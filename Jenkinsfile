import groovy.json.JsonSlurperClassic

node {
    // Set global environment variables
    //env.REMOTE_JENKINS_URL = "https://vs-gate.dei.isep.ipp.pt:10518/"
    //env.LOCAL_JENKINS_URL = "http://localhost:8080/"

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
                    def response = httpRequest(
                            url: "${env.JENKINS_URL}job/your_job_name/build", // Specify the job name
                            httpMode: 'POST',
                            authentication: credentialsId,
                            customHeaders: [[name: crumbField, value: crumb]]
                    )
                    echo "Using Jenkins user: ${JENKINS_USER}"
                    echo "Triggered Jenkins job successfully. Response: ${response}"
                } catch (Exception e) {
                    echo "Error occurred while triggering Jenkins job: ${e.message}"
                }
            }
    }
}

def triggerJenkinsJob(jenkinsUrl, credentialsId) {
    withCredentials([usernamePassword(credentialsId: 'jenkins-creds', usernameVariable: 'JENKINS_USER', passwordVariable: 'JENKINS_PASS')]) {
        try {
            // Retrieve CSRF crumb
            def crumbResponse = httpRequest(
                    url: "${jenkinsUrl}crumbIssuer/api/json",
                    authentication: credentialsId
            )

            // Parse JSON response
            def crumbJson = new JsonSlurperClassic().parseText(crumbResponse.content)
            def crumb = crumbJson.crumb
            def crumbField = crumbJson.crumbRequestField

            // Trigger Jenkins job with CSRF crumb
            def response = httpRequest(
                    url: "${jenkinsUrl}job/your_job_name/build", // Specify the job name
                    httpMode: 'POST',
                    authentication: credentialsId,
                    customHeaders: [[name: crumbField, value: crumb]]
            )
            echo "Using Jenkins user: ${JENKINS_USER}"
            echo "Triggered Jenkins job successfully. Response: ${response}"
        } catch (Exception e) {
            echo "Error occurred while triggering Jenkins job: ${e.message}"
        }
    }
}