# filepath: /c:/Users/guicota/Desktop/MEI/odsoft-project-2024/P2/docker-swarm/Dockerfile
FROM jenkins/jenkins:lts

# Install plugins
RUN jenkins-plugin-cli --plugins configuration-as-code

# Copy JCasC configuration file
COPY casc.yml /var/jenkins_conf/casc.yml

# Set environment variable for JCasC
ENV CASC_JENKINS_CONFIG=/var/jenkins_conf/casc.yml