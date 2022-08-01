pipeline {
    agent any
    stages {
        stage('Pull') { 
            steps {
                git credentialsId: 'idfsbank_github', url: 'git@github.com:atulyw/idfsbank.git' 
            }
        }
        stage('QA') { 
            steps {
                echo "$BUILD_NUMBER Pass for QA"
            }
        }
        stage('Build') { 
            steps {
               sh '''
               mvn clean package
               tar -cvf $JOB_BASE_NAME-$BUILD_ID.tar **/**.war
               ls -a
               ''' 
            }
        }
        stage('Push-Build') { 
            steps {
                echo 'pushing artifact to s3'
            }
        }
        stage('Deploy') { 
            steps {
                echo 'Deploying artifact to s3'
            }
        }
    }
}