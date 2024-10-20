node {
    // Set global environment variables
    env.REMOTE_JENKINS_URL = "https://vs-gate.dei.isep.ipp.pt:10518/"
    env.LOCAL_JENKINS_URL = "http://localhost:8081/"

    stage('Check Environment and Trigger Job') {
        // Check if this is a local or remote Jenkins run
        if (env.JENKINS_URL == "http://localhost:8081/") {
            echo "Running on Local Jenkins. Triggering local job."

            withCredentials([usernamePassword(credentialsId: 'local-jenkins-creds', usernameVariable: 'LOCAL_USER', passwordVariable: 'LOCAL_PASS')]) {
                // Trigger a local Jenkins job
                def response = httpRequest(
                        url: env.LOCAL_JENKINS_URL,
                        customHeaders: [[name: 'Authorization', value: "Basic ${"${LOCAL_USER}:${LOCAL_PASS}".bytes.encodeBase64().toString()}"]],
                        httpMode: 'POST'
                )
                echo "Using local Jenkins user: ${LOCAL_USER}"
                echo "Triggered local Jenkins job successfully. Response: ${response}"
            }
        } else {
            echo "Running on Remote Jenkins. Triggering remote job."

            withCredentials([usernamePassword(credentialsId: 'remote-jenkins-creds', usernameVariable: 'REMOTE_USER', passwordVariable: 'REMOTE_PASS')]) {
                // Trigger a remote Jenkins job
                def response = httpRequest(
                        url: env.REMOTE_JENKINS_URL,
                        customHeaders: [[name: 'Authorization', value: "Basic ${"${REMOTE_USER}:${REMOTE_PASS}".bytes.encodeBase64().toString()}"]],
                        httpMode: 'POST'
                )
                echo "Using remote Jenkins user: ${REMOTE_USER}"
                echo "Triggered remote Jenkins job successfully. Response: ${response}"
            }
        }
    }
}
