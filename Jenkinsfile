import groovy.json.JsonSlurperClassic

node {
    // Set global environment variables
    env.REMOTE_JENKINS_URL = "https://vs-gate.dei.isep.ipp.pt:10518/"
    env.LOCAL_JENKINS_URL = "http://localhost:8080/"

    echo "Running on Jenkins URL: ${env.JENKINS_URL}"
    echo "Checking environment..."

    stage('Check Environment') {
        // Determine if this is a local or remote Jenkins run
        if (env.JENKINS_URL == "http://localhost:8080/") {
            echo "Running on Local Jenkins. Triggering local job."

            withCredentials([usernamePassword(credentialsId: 'local-jenkins-creds', usernameVariable: 'LOCAL_JENKINS_USER', passwordVariable: 'LOCAL_JENKINS_PASS')]) {
                try {
                    // Retrieve CSRF crumb
                    def crumbResponse = httpRequest(
                            url: "${env.LOCAL_JENKINS_URL}crumbIssuer/api/json",
                            authentication: 'local-jenkins-creds'
                    )

                    // Parse JSON response
                    def crumbJson = new JsonSlurperClassic().parseText(crumbResponse.content)
                    def crumb = crumbJson.crumb
                    def crumbField = crumbJson.crumbRequestField

                    // Trigger local Jenkins job with CSRF crumb
                    def response = httpRequest(
                            url: "${env.LOCAL_JENKINS_URL}job/your_job_name/build", // Specify the job name
                            httpMode: 'POST',
                            authentication: 'local-jenkins-creds',
                            customHeaders: [[name: crumbField, value: crumb]]
                    )
                    echo "Using local Jenkins user: ${LOCAL_JENKINS_USER}"
                    echo "Triggered local Jenkins job successfully. Response: ${response}"
                } catch (Exception e) {
                    echo "Error occurred while triggering local Jenkins job: ${e.message}"
                }
            }
        } else {
            echo "Running on Remote Jenkins. Triggering remote job."

            withCredentials([usernamePassword(credentialsId: 'remote-jenkins-creds', usernameVariable: 'REMOTE_JENKINS_USER', passwordVariable: 'REMOTE_JENKINS_PASS')]) {
                try {
                    // Retrieve CSRF crumb
                    def crumbResponse = httpRequest(
                            url: "${env.REMOTE_JENKINS_URL}crumbIssuer/api/json",
                            authentication: 'remote-jenkins-creds'
                    )

                    // Parse JSON response
                    def crumbJson = new JsonSlurperClassic().parseText(crumbResponse.content)
                    def crumb = crumbJson.crumb
                    def crumbField = crumbJson.crumbRequestField

                    // Trigger remote Jenkins job with CSRF crumb
                    def response = httpRequest(
                            url: "${env.REMOTE_JENKINS_URL}job/your_job_name/build", // Specify the job name
                            httpMode: 'POST',
                            authentication: 'remote-jenkins-creds',
                            customHeaders: [[name: crumbField, value: crumb]]
                    )
                    echo "Using remote Jenkins user: ${REMOTE_JENKINS_USER}"
                    echo "Triggered remote Jenkins job successfully. Response: ${response}"
                } catch (Exception e) {
                    echo "Error occurred while triggering remote Jenkins job: ${e.message}"
                }
            }
        }
    }
}
