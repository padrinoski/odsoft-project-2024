import groovy.json.JsonSlurperClassic

pipeline {
    agent any

    environment {
        SONAR_HOST_URL = "http://localhost:9000"
        SONARCLOUD_URL = "https://sonarcloud.io"
        SONARCLOUD_ORGANIZATION = "padrinoski"
        SONAR_TOKEN = credentials('SONAR_TOKEN')
        SONAR_PROJECT_KEY = 'odsoft-sonarqube'
        SONAR_PROJECT_NAME = 'odsoft-sonarqube'
    }

    tools {
        gradle "gradle"
        //maven "mvn"
    }

    stages {

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
                        //sh "mvn clean verify sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.projectName=${SONAR_PROJECT_NAME} -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.token=${env.SONAR_TOKEN}"
                        sh "mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.token=${env.SONAR_TOKEN} -Dsonar.host.url=${SONARCLOUD_HOST_URL} -Dsonar.organization=${SONARCLOUD_ORGANIZATION}"
                        }

                    }else{
                        withSonarQubeEnv() {
                        //bat "mvn clean verify sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.projectName=${SONAR_PROJECT_NAME} -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.token=${env.SONAR_TOKEN}"
                        bat "mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.token=${env.SONAR_TOKEN} -Dsonar.host.url=${SONARCLOUD_HOST_URL} -Dsonar.organization=${SONARCLOUD_ORGANIZATION}"
                        }
                    }
                }
            }
        }


        stage('Unit Testing') {
            steps{
                script {
                    if (isUnix()) {
                        sh "mvn test"
                    } else {
                        bat "mvn test"
                    }            
                }
            }
        }

        stage('Test Coverage') {
            steps{
                script {
                    if (isUnix()) {
                        sh "mvn jacoco:report"
                    } else {
                        bat "mvn jacoco:report"
                    }            
                }
            }
        }

        stage('Mutation Testing') {
            steps {
                script{
                    if(isUnix()){
                        sh 'mvn -DwithHistory test-compile org.pitest:pitest-maven:mutationCoverage'
                    }else{
                        bat 'mvn -DwithHistory test-compile org.pitest:pitest-maven:mutationCoverage'
                    }
                }
            }
        }
/*
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

                stage('Report Results') {
            steps {
                script {
                    // Collect test reports
                    //junit 'target/surefire-reports/**/*.xml'
                    // Archive the built artifacts
                    //archiveArtifacts artifacts: 'target/**/*.jar', fingerprint: true
                    // Publish JaCoCo coverage report
                    jacoco execPattern: 'target/jacoco.exec', classPattern: 'target/classes', sourcePattern: 'src/main/java', htmlDir: 'target/site/jacoco'
                    // Publish PIT mutation testing report
                    publishHTML(target: [
                        reportName: 'PIT Mutation Report',
                        reportDir: 'target/pit-reports',
                        reportFiles: 'index.html',
                        alwaysLinkToLastBuild: true,
                        keepAll: true
                    ])
                }
            }
        }
    }

}