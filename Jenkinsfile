import groovy.json.JsonSlurperClassic

pipeline {
    agent any
    
    options {
        // Change workspace directory inside the container
        // Make sure the path `/workspace/odsoft_main` is mapped correctly in Docker Desktop
        skipDefaultCheckout() // Avoids Jenkins using the default (Windows) path
    }

    environment {
        SONAR_HOST_URL = "http://localhost:9000"
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
                    // Assuming a Maven project for example, adjust for Gradle or other build systems
                    bat 'mvn clean compile package'
                    echo "Compilation finished"
                }
            }
        }

        /* stage('Static Code Analysis') {
            steps {
                withSonarQubeEnv() {
                    sh "mvn sonar:sonar -Dsonar.projectKey=sonarqube-project -Dsonar.projectName='odsoft-sonarqube-project' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_9e852095a9a8f3b5dade08e8c48c03b8d10e4c36"
                }
            }
        }

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