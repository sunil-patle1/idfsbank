pipeline {
    agent any
    stages {
        stage("QA"){
            parallel {
                stage("QA-1"){
                    agent any
                    steps {
                        echo "QA 1 PASSED"
                    }
                }

                stage("QA-2"){
                    agent any
                    steps {
                        echo "QA 2 PASSED"
                    }
                }
            }
        }
        stage("Sonar-Scan") {
            steps {
                sh '''
                export PATH=$PATH:/opt/sonar-scanner/bin
                sonar-scanner -Dsonar.login=b5d12d2402ebf7b27b08b7fc1e9c74326b9fd1b8 -Dsonar.projectKey=idfsbank -Dsonar.organization=atulyw
                '''
             }
          }  
        stage("Build"){
            steps {
                sh '''
                mvn clean package
                tar -cvf $JOB_BASE_NAME-$BUILD_ID.tar **/**.war
                '''    
            }
        }

        stage("push-dev"){
            when {
                branch "develop"
            }

            steps {
                echo "pushing artifact to s3"
            }
        }

        stage("deploy-dev"){
            when {
                branch "develop"
            }

            steps {
                echo "Deploying artifact to s3"
            }
        }

        stage("push-test"){
            when {
                branch "release/*"
            }

            steps {
                echo "pushing artifact to s3"
            }
        }

        stage("deploy-test"){
            when {
                branch "release/*"
            }

            steps {
                echo "Deploying artifact to s3"
            }
        }

        stage("push-uat"){
            when {
                branch "release/*"
            }

            steps {
                echo "pushing artifact to s3"
            }
        }

        stage("deploy-uat"){
            when {
                branch "release/*"
            }

            steps {
                echo "Deploying artifact to s3"
            }
        }

        stage("push-prod"){
            when {
                branch "main"
            }

            steps {
                echo "pushing artifact to s3"
            }
        }

        stage("deploy-prod"){
            when {
                branch "main"
            }

            steps {
                echo "Deploying artifact to s3"
            }
        }
    }
}