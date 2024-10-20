import java.util.Base64

node {
    // Set global environment variables
    env.REMOTE_JENKINS_URL = "https://vs-gate.dei.isep.ipp.pt:10518/"
    env.LOCAL_JENKINS_URL = "http://localhost:8080/"

    echo "Running on Jenkins URL: ${env.JENKINS_URL}"
    echo "Checking environment..."

    stage('Check Environment') {
        // Determine if this is a local or remote Jenkins run using environment variables or custom logic
        if (env.JENKINS_URL == "http://localhost:8080/") {
            echo "Running on Local Jenkins. Triggering local job."

            withCredentials([usernamePassword(credentialsId: 'local-jenkins-creds', usernameVariable: 'LOCAL_JENKINS_USER', passwordVariable: 'LOCAL_JENKINS_PASS')]) {
                // Encode credentials for local Jenkins
                def authString = "${LOCAL_JENKINS_USER}:${LOCAL_JENKINS_PASS}".toString()
                def encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes('UTF-8'))

                def response = httpRequest(
                        url: env.LOCAL_JENKINS_URL,
                        customHeaders: [[name: 'Authorization', value: "Basic ${encodedAuthString}"]],
                        httpMode: 'POST'
                )
                echo "Using local Jenkins user: ${LOCAL_JENKINS_USER}"
                echo "Triggered local Jenkins job successfully. Response: ${response}"
            }
        } else {
            echo "Running on Remote Jenkins. Triggering remote job."

            withCredentials([usernamePassword(credentialsId: 'remote-jenkins-creds', usernameVariable: 'REMOTE_JENKINS_USER', passwordVariable: 'REMOTE_JENKINS_PASS')]) {
                // Encode credentials for remote Jenkins
                def authString = "${REMOTE_JENKINS_USER}:${REMOTE_JENKINS_PASS}".toString()
                def encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes('UTF-8'))

                def response = httpRequest(
                        url: env.REMOTE_JENKINS_URL,
                        customHeaders: [[name: 'Authorization', value: "Basic ${encodedAuthString}"]],
                        httpMode: 'POST'
                )
                echo "Using remote Jenkins user: ${REMOTE_JENKINS_USER}"
                echo "Triggered remote Jenkins job successfully. Response: ${response}"
            }
        }
    }
}
