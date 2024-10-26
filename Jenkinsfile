import groovy.json.JsonSlurperClassic

pipeline {
    agent any

    environment {
        SONAR_HOST_URL = "http://localhost:9000"
    }

    tools {
        gradle "gradle"
    }

    stages {
        stage('Check Environment') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'jenkins-creds', usernameVariable: 'JENKINS_USER', passwordVariable: 'JENKINS_PASS')]) {
                    script {
                        try {
                            // Retrieve CSRF crumb
                            def crumbResponse = httpRequest(
                                url: "${env.JENKINS_URL}crumbIssuer/api/json",
                                authentication: 'jenkins-creds'
                            )

                            // Parse JSON response
                            def crumbJson = new JsonSlurperClassic().parseText(crumbResponse.content)
                            def crumb = crumbJson.crumb
                            def crumbField = crumbJson.crumbRequestField

                            // Trigger Jenkins job with CSRF crumb
                            /* def response = httpRequest(
                                url: "${env.JENKINS_URL}job/your_job_name/build", // Specify the job name
                                httpMode: 'POST',
                                authentication: 'jenkins-creds',
                                customHeaders: [[name: crumbField, value: crumb]]
                            ) */

                            // Log Jenkins user
                            echo "Using Jenkins user: ${JENKINS_USER}"
                            echo "Triggered Jenkins job successfully. Response: ${response}"
                        } catch (Exception e) {
                            echo "Error occurred while triggering Jenkins job: ${e.message}"
                        }
                    }
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    if(isUnix()){
                        sh 'mvn clean compile package'
                    }else{
                        bat 'mvn clean compile package'
                    } 
                    echo "Compilation finished"
                }
            }
        }

        stage('Start SonarQube') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'docker-compose up -d'
                    } else {
                        bat 'docker-compose up -d'
                    }
                    // Wait for SonarQube to be ready
                    waitUntil {
                        script {
                            def response
                            if (isUnix()) {
                                response = sh(script: "curl -s -o /dev/null -w '%{http_code}' ${SONAR_HOST_URL}/api/system/status", returnStdout: true).trim()
                            } else {
                                response = bat(script: """
                                    powershell -Command "\$response = Invoke-WebRequest -Uri ${SONAR_HOST_URL}/api/system/status -UseBasicParsing; echo \$response.StatusCode"
                                """, returnStdout: true).trim()
                            }
                            return response == '200'
                        }
                    }
                }
            }
        }

         stage('Static Code Analysis') {
            steps {
                script{
                    if(isUnix()){
                        withSonarQubeEnv() {
                            sh "gradle SonarUnix"
                        }
                    }else{
                        withSonarQubeEnv() {
                            bat "gradle SonarWindows"
                        }
                    }
                }
            }
        }

/*
        stage('Unit Testing') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Test Coverage') {
            steps {
                sh 'mvn jacoco:report'
            }
        }

        stage('Mutation Testing') {
            steps {
                sh 'mvn org.pitest:pitest-maven:mutationCoverage'
            }
        }

        stage('Integration and Service Testing') {
            steps {
                sh 'mvn verify'
            }
        }

        stage('Database Testing') {
            steps {
                sh 'mvn -Dtest=YourDatabaseTest test'
            }
        }

        stage('Local Deployment') {
            steps {
                echo 'Deploying locally...'
                sh 'docker-compose up -d'
            }
        }

        stage('Remote Deployment') {
            steps {
                echo 'Deploying to remote server...'
                sshagent(['remote-server-credentials']) {
                    sh 'scp target/your-artifact.jar user@vs-ctl.dei.isep.ipp.pt:/path/to/deploy'
                    sh 'ssh user@vs-ctl.dei.isep.ipp.pt "bash /path/to/deploy-script.sh"'
                }
            }
        } */
    }

    post {
        always {
            echo "Post build action reached"
            //junit 'target/surefire-reports/**/*.xml' // Collect test reports
            //archiveArtifacts artifacts: 'target/**/*.jar', fingerprint: true // Archive the built artifacts
        }
    }
}