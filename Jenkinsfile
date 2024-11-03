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
        POSTMAN_COLLECTION_PATH = "Docs/Psoft-G1.postman_collection.json"
        POSTMAN_ENVIRONMENT_PATH = "Docs/Psoft-G1.postman_environment.json"
        LOCAL_DEPLOYMENT_PATH = "${WORKSPACE}/deployment"
        APP_JAR_NAME = "psoft-g1-0.0.1-SNAPSHOT.jar"
        APP_WAR_NAME = "psoft-g1-0.0.1-SNAPSHOT.war"
        REMOTE_CREDENTIALS_ID = "SSH_DEIVM_CREDS"
        REMOTE_PORT = "11179"
        REMOTE_HOST = "vsgate-ssh.dei.isep.ipp.pt"

    }

    tools {
        gradle "gradle"
        //maven "mvn"
        nodejs "nodejs"
    }


    options {
        // Configure workspace caching
        /* Workspace caching allows Jenkins to cache the contents of a workspace so that when a new build is triggered,
        it can reuse the cached workspace if the source code has not changed significantly. This is particularly useful
        for large projects with dependencies and libraries that do not change frequently.*/
        skipDefaultCheckout()
        cache(maxCacheSize: 250, defaultBranch: 'main', caches: [
                arbitraryFileCache(path: 'cache', cacheValidityDecidingFile: 'pom.xml', cacheName: 'maven-cache')])
    }

    stages {

        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        //sh 'mvn clean install'
                        sh 'mvn clean compile package -DskipTests'
                        sh 'ls -la target/'
                    } else {
                        //bat 'mvn clean install'
                        bat 'mvn clean compile package -DskipTests'
                        bat 'dir target'
                    }
                    /* Artifact caching involves caching build artifacts (e.g., compiled binaries, build outputs) so that subsequent builds
                    can reuse them rather than recompiling or regenerating them. This can save a significant amount of time and resources. */
                    stash name: 'build-artifact', includes: 'target/*.jar'
                    echo "JAR Artifact stashed"

                    stash name: 'war-artifact', includes: 'target/*.war'
                    echo "WAR file stashed"

                    echo "Compilation finished"
                }
            }
        }

        stage('Static Code Analysis') {
            steps {
                unstash 'build-artifact'
                script {
                    if (isUnix()) {
                        withSonarQubeEnv() {
                            //sh "mvn clean verify sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.projectName=${SONAR_PROJECT_NAME} -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.token=${env.SONAR_TOKEN}"
                            sh "mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.token=${env.SONAR_TOKEN} -Dsonar.host.url=${SONARCLOUD_URL} -Dsonar.organization=${SONARCLOUD_ORGANIZATION}"
                        }

                    } else {
                        withSonarQubeEnv() {
                            //bat "mvn clean verify sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.projectName=${SONAR_PROJECT_NAME} -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.token=${env.SONAR_TOKEN}"
                            bat "mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.token=${env.SONAR_TOKEN} -Dsonar.host.url=${SONARCLOUD_URL} -Dsonar.organization=${SONARCLOUD_ORGANIZATION}"
                        }
                    }
                }
            }
        }

        stage('Install Newman HTML Reporter') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'npm install -g newman-reporter-htmlextra'
                    } else {
                        bat 'npm install -g newman-reporter-htmlextra'
                    }
                }
            }
        }

        stage('Start Spring Boot Application') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'nohup mvn spring-boot:run &'
                    } else {
                        bat 'start "" /B mvn spring-boot:run'
                    }
                }
            }
        }


        stage('Testing') {
            parallel {
                stage('Unit Testing') {
                    steps {
                        unstash 'build-artifact'
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
                    steps {
                        unstash 'build-artifact'
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
                        unstash 'build-artifact'
                        script {
                            if (isUnix()) {
                                sh 'mvn -DwithHistory test-compile org.pitest:pitest-maven:mutationCoverage'
                            } else {
                                bat 'mvn -DwithHistory test-compile org.pitest:pitest-maven:mutationCoverage'
                            }
                        }
                    }
                }

                stage('Service Testing') {
                    steps {
                        unstash 'build-artifact'
                        script {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                if (isUnix()) {
                                    sh "newman run ${POSTMAN_COLLECTION_PATH} --environment ${POSTMAN_ENVIRONMENT_PATH} -r htmlextra --reporter-htmlextra-export target/newman/PostmanResults.html"
                                } else {
                                    bat "newman run ${POSTMAN_COLLECTION_PATH} --environment ${POSTMAN_ENVIRONMENT_PATH} -r htmlextra --reporter-htmlextra-export target/newman/PostmanResults.html"
                                }
                            }
                        }
                    }
                }
            }
        }

        stage('Stop Spring Boot Application') {
            steps {
                script {
                    if (isUnix()) {
                        // Using Maven to stop the Spring Boot application
                        sh 'mvn spring-boot:stop -DpidFile=${WORKSPACE}/target/spring.pid'
                    } else {
                        // For Windows, you may still use taskkill as a fallback
                        bat 'mvn spring-boot:stop -DpidFile=${WORKSPACE}\\target\\spring.pid'
                    }
                }
            }
        }

        stage('Local Deployment') {
            steps {
                script {
                    echo "Deploying to local environment..."
                    unstash 'build-artifact' // Retrieve the latest build artifact
                    if (isUnix()) {
                        sh '''
                        mkdir -p ${LOCAL_DEPLOYMENT_PATH}
                        cp target/*.jar ${LOCAL_DEPLOYMENT_PATH}/
                        java -jar ${LOCAL_DEPLOYMENT_PATH}/${APP_JAR_NAME} &
                        '''
                    } else {
                        bat '''
                        mkdir "%LOCAL_DEPLOYMENT_PATH%"
                        copy target\\*.jar "%LOCAL_DEPLOYMENT_PATH%\\"
                        start "" /B java -jar "%LOCAL_DEPLOYMENT_PATH%\\%APP_JAR_NAME%"
                        '''
                    }
                }
            }
        }

        stage('Remote Deployment') {
            steps {
                script {
                    unstash 'build-artifact' // Retrieve the JAR file for deployment

                    withCredentials([sshUserPrivateKey(credentialsId: "${REMOTE_CREDENTIALS_ID}", keyFileVariable: 'SSH_KEY', usernameVariable: 'REMOTE_USERNAME')]) {
                        if (isUnix()) {
                            sh """
                                ssh-keyscan -H ${REMOTE_HOST} >> ~/.ssh/known_hosts
                                cat ~/.ssh/known_hosts // Check known_hosts content
                                scp -i ${SSH_KEY} -P ${REMOTE_PORT} target/${APP_JAR_NAME} ${REMOTE_USERNAME}@${REMOTE_HOST}:/opt/app/
                                ssh -i ${SSH_KEY} -p ${REMOTE_PORT} ${REMOTE_USERNAME}@${REMOTE_HOST} << EOF
                                    pkill -f 'java -jar ${APP_JAR_NAME}' || true
                                    nohup java -jar /opt/app/${APP_JAR_NAME} > /opt/app/app.log 2>&1 &
                                EOF
                            """
                        } else {
                            bat """
                                ssh-keyscan -H ${REMOTE_HOST} >> %USERPROFILE%\\.ssh\\known_hosts
                                type %USERPROFILE%\\.ssh\\known_hosts // Check known_hosts content
                                plink -P ${REMOTE_PORT} -i ${SSH_KEY} -batch ${REMOTE_USERNAME}@${REMOTE_HOST} "echo connected"
                                pscp -P ${REMOTE_PORT} -i ${SSH_KEY} -batch target/${APP_JAR_NAME} ${REMOTE_USERNAME}@${REMOTE_HOST}:/opt/app/
                                plink -P ${REMOTE_PORT} -i ${SSH_KEY} -batch ${REMOTE_USERNAME}@${REMOTE_HOST} "pkill -f 'java -jar ${APP_JAR_NAME}' || true && nohup java -jar /opt/app/${APP_JAR_NAME} > /opt/app/app.log 2>&1 &"
                            """
                        }
                    }
                    echo "Deployment complete. Spring Boot application is running on the remote server."
                }
            }
        }


        stage('Check Remote Application Status') {
            steps {
                script {
                    echo "Checking if the application is running..."
                    def retryInterval = 10
                    def maxRetries = 10
                    def attempts = 0

                    withCredentials([sshUserPrivateKey(credentialsId: "${REMOTE_CREDENTIALS_ID}", keyFileVariable: 'SSH_KEY', usernameVariable: 'REMOTE_USERNAME')]) {
                        waitUntil {
                            attempts++
                            def isRunning = false
                            if (isUnix()) {
                                isRunning = sh(script: "ssh -i ${SSH_KEY} -p ${REMOTE_PORT} ${REMOTE_USERNAME}@${REMOTE_HOST} 'ps aux | grep -v grep | grep java -jar ${APP_JAR_NAME}'", returnStatus: true) == 0
                            } else {
                                isRunning = bat(script: "plink -P ${REMOTE_PORT} -i ${SSH_KEY} ${REMOTE_USERNAME}@${REMOTE_HOST} \"tasklist | findstr /C:'java.exe'\"", returnStatus: true) == 0
                            }

                            if (isRunning) {
                                echo "Application is running! - REMOTE"
                            } else {
                                echo "Application is not running yet - REMOTE. Retrying in ${retryInterval} seconds..."
                            }

                            sleep retryInterval
                            return isRunning || attempts >= maxRetries
                        }

                        if (attempts >= maxRetries) {
                            error "Application did not start within the expected time. - REMOTE"
                        }
                    }
                }
            }
        }



        stage('Check Local Application Status') {
            steps {
                script {
                    echo "Checking if the application is running..."
                    def retryInterval = 10

                    waitUntil {
                        def isRunning = false
                        if (isUnix()) {
                            isRunning = sh(script: "ps -ef | grep -v grep | grep 'java -jar ${APP_JAR_NAME}'", returnStatus: true) == 0
                        } else {
                            isRunning = bat(script: "tasklist | findstr /C:\"java.exe\"", returnStatus: true) == 0
                        }

                        if (isRunning) {
                            echo "Application is running successfully."
                            return true
                        } else {
                            echo "Application not running yet. Retrying in ${retryInterval} seconds..."
                            sleep retryInterval
                            return false
                        }
                    }
                }
            }
        }

        stage('Stop Local Application') {
            steps {
                script {
                    echo "Stopping the application..."
                    if (isUnix()) {
                        sh 'mvn spring-boot:stop -DpidFile=${WORKSPACE}/target/spring.pid'
                    } else {
                        bat 'mvn spring-boot:stop -DpidFile=${WORKSPACE}\\target\\spring.pid'
                    }
                    echo "Application stopped."
                }
            }
        }


        stage('Report Results') {
            steps {
                script {
                    // Collect test reports
                    junit 'target/surefire-reports/**/*.xml'
                    // Archive the built artifacts
                    archiveArtifacts artifacts: 'target/**/*.jar', fingerprint: true
                    // Publish JaCoCo coverage report
                    jacoco execPattern: 'target/jacoco.exec', classPattern: 'target/classes', sourcePattern: 'src/main/java', htmlDir: 'target/site/jacoco'
                    // Publish PIT mutation testing report
                    publishHTML(target: [
                            reportName           : 'PIT Mutation Report',
                            reportDir            : 'target/pit-reports',
                            reportFiles          : 'index.html',
                            alwaysLinkToLastBuild: true,
                            keepAll              : true
                    ])
                    // Publish Newman test results
                    publishHTML(target: [
                            reportName           : 'Postman Test Results',
                            reportDir            : 'target/newman',
                            reportFiles          : 'PostmanResults.html',
                            alwaysLinkToLastBuild: true,
                            keepAll              : true
                    ])
                }
            }
        }
    }
}
