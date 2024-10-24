import groovy.json.JsonSlurperClassic

pipeline {
    agent {
        docker {
            image 'gradle:6.8.3-jdk11' // Specify the Gradle Docker image
            args '-v /root/.gradle:/root/.gradle' // Optional: Mount the local Gradle repository to cache dependencies
        }
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
                    sh './gradlew mvnCleanCompilePackage'
                    echo "Compilation finished"
                }
            }
        }

        // stage('Static Code Analysis') {
        //     steps {
        //         script {
        //             sh './gradlew mvnSonar'
        //         }
        //     }
        // }

        // stage('Unit Testing') {
        //     steps {
        //         script {
        //             sh './gradlew mvnTest'
        //         }
        //     }
        // }

        // stage('Test Coverage') {
        //     steps {
        //         script {
        //             sh './gradlew mvnJacocoReport'
        //         }
        //     }
        // }

        // stage('Mutation Testing') {
        //     steps {
        //         script {
        //             sh './gradlew mvnPitest'
        //         }
        //     }
        // }

        // stage('Integration and Service Testing') {
        //     steps {
        //         script {
        //             sh './gradlew mvnVerify'
        //         }
        //     }
        // }

        // stage('Database Testing') {
        //     steps {
        //         script {
        //             sh './gradlew mvnDatabaseTest'
        //         }
        //     }
        // }

        // stage('Local Deployment') {
        //     steps {
        //         script {
        //             echo 'Deploying locally...'
        //             sh 'docker-compose up -d'
        //         }
        //     }
        // }

        // stage('Remote Deployment') {
        //     steps {
        //         script {
        //             echo 'Deploying to remote server...'
        //             sshagent(['remote-server-credentials']) {
        //                 sh 'scp build/libs/your-artifact.jar user@vs-ctl.dei.isep.ipp.pt:/path/to/deploy'
        //                 sh 'ssh user@vs-ctl.dei.isep.ipp.pt "bash /path/to/deploy-script.sh"'
        //             }
        //         }
        //     }
        // }
    }

    post {
        always {
            echo "Post build action reached"
            junit 'build/test-results/test/*.xml' // Collect test reports
            archiveArtifacts artifacts: 'build/libs/**/*.jar', fingerprint: true // Archive the built artifacts
        }
    }
}