import groovy.json.JsonSlurperClassic

pipeline {
    agent any

    environment {
        SONAR_HOST_URL = "http://localhost:9000"
        SONAR_TOKEN = credentials('sonar-token')
        SONAR_PROJECT_KEY = 'odsoft-sonarqube'
        SONAR_PROJECT_NAME = 'odsoft-sonarqube'
    }

    tools {
        gradle "gradle"
        //maven "mvn"
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
                        //sh 'mvn clean install'
                        sh 'mvn clean compile package'
                    }else{
                        //bat 'mvn clean install'
                        bat 'mvn clean compile package'
                    } 
                    echo "Compilation finished"
                }
            }
        }

        stage('Static Code Analysis') {
            steps {
                script{
                    if(isUnix()){
                        withSonarQubeEnv() {
                        sh "mvn clean verify sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.projectName=${SONAR_PROJECT_NAME} -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.token=${env.SONAR_TOKEN}"
                        }

                    }else{
                        withSonarQubeEnv() {
                        bat "mvn clean verify sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.projectName=${SONAR_PROJECT_NAME} -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.token=${env.SONAR_TOKEN}"
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

}