import groovy.json.JsonSlurperClassic

node {
    // Set global environment variables
    //env.REMOTE_JENKINS_URL = "https://vs-gate.dei.isep.ipp.pt:10518/"
    //env.LOCAL_JENKINS_URL = "http://localhost:8080/"
    env.SONAR_HOST_URL = "http://localhost:9000"

    echo "Running on Jenkins URL: ${env.JENKINS_URL}"
    echo "Checking environment..."

    stage('Check Environment') {

        withCredentials([usernamePassword(credentialsId: 'jenkins-creds', usernameVariable: 'JENKINS_USER', passwordVariable: 'JENKINS_PASS')]) {
                try {
                    // Retrieve CSRF crumb
                    def crumbResponse = httpRequest(
                            url: "${env.JENKINS_URL}crumbIssuer/api/json",
                            authentication: credentialsId
                    )

                    // Parse JSON response
                    def crumbJson = new JsonSlurperClassic().parseText(crumbResponse.content)
                    def crumb = crumbJson.crumb
                    def crumbField = crumbJson.crumbRequestField

                    // Trigger Jenkins job with CSRF crumb
                    /*  def response = httpRequest(
                            url: "${env.JENKINS_URL}job/your_job_name/build", // Specify the job name
                            httpMode: 'POST',
                            authentication: credentialsId,
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

    // Build stage (Compilation, Dependency resolution, Packaging)
    stage('Build') {
        // Assuming a Maven project for example, adjust for Gradle or other build systems
        mvn 'clean compile package'
        echo "Compilation finished"
    }

    /* // Static Code Analysis (e.g., SonarQube)
    stage('Static Code Analysis') {
        withSonarQubeEnv() {
          sh "mvn sonar:sonar -Dsonar.projectKey=sonarqube-project -Dsonar.projectName='odsoft-sonarqube-project' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_9e852095a9a8f3b5dade08e8c48c03b8d10e4c36"
        }
    }

    // Unit Testing
    stage('Unit Testing') {
        // Run unit tests
        sh 'mvn test'
    }

    // Test Coverage
    stage('Test Coverage') {
        // Example for JaCoCo Coverage
        sh 'mvn jacoco:report'
    }

    // Mutation Tests (e.g., using PIT)
    stage('Mutation Testing') {
        // Example of mutation testing with PIT
        sh 'mvn org.pitest:pitest-maven:mutationCoverage'
    }

    // Integration and Service Testing
    stage('Integration and Service Testing') {
        // Run integration tests, for example, with Maven Surefire
        sh 'mvn verify'
    }

    // Database Testing
    stage('Database Testing') {
        // Example of running database tests, adjust to your DB setup
        sh 'mvn -Dtest=YourDatabaseTest test'
    }

    // Local Deployment
    stage('Local Deployment') {
        // Local deployment logic
        echo 'Deploying locally...'
        // Example using Docker for local deployment
        sh 'docker-compose up -d'
    }

    // Remote Deployment
    stage('Remote Deployment') {
        // Deployment to a remote server like the one you mentioned (e.g., vs-ctl.dei.isep.ipp.pt)
        echo 'Deploying to remote server...'
        sshagent(['remote-server-credentials']) {
            // Example using SCP to copy files
            sh 'scp target/your-artifact.jar user@vs-ctl.dei.isep.ipp.pt:/path/to/deploy'
            // Example of SSH command execution for deployment
            sh 'ssh user@vs-ctl.dei.isep.ipp.pt "bash /path/to/deploy-script.sh"'
        }
    } */
}

    // Post build actions: reporting results
    post {
    echo("Post build action reached")
/*         always {
            junit 'target/surefire-reports *//*.xml' // Collect test reports
            archiveArtifacts artifacts: 'target *//*.jar', fingerprint: true // Archive the built artifacts
        } */
    }
