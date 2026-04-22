pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-21'
    }

    environment {
        // ⚠️ IMPORTANT: Replace YOUR_DOCKERHUB_USERNAME with your actual Docker Hub username
        DOCKER_IMAGE = "mamathadhudakonda/customer-service"
        DOCKER_TAG = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                echo '========== Stage 1: Pulling code from GitHub =========='
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '========== Stage 2: Building with Maven =========='
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo '========== Stage 3: Running Unit Tests =========='
                sh 'mvn test'
            }
        }

        stage('Docker Build') {
            steps {
                echo '========== Stage 4: Building Docker Image =========='
                sh 'docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .'
                sh 'docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest'
            }
        }

        stage('Docker Push') {
            steps {
                echo '========== Stage 5: Pushing Image to Docker Hub =========='
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials',
                                                  usernameVariable: 'DOCKER_USER',
                                                  passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh 'docker push ${DOCKER_IMAGE}:${DOCKER_TAG}'
                    sh 'docker push ${DOCKER_IMAGE}:latest'
                }
            }
        }

        stage('Deploy') {
            steps {
                echo '========== Stage 6: Deploying Application =========='
                sh '''
                    docker stop customer-service-app || true
                    docker rm customer-service-app || true
                    docker run -d --name customer-service-app --network cicd-network -p 8080:8080 ${DOCKER_IMAGE}:latest
                '''
                echo 'Application deployed at http://localhost:8080'
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline completed successfully!'
            echo 'App running at http://localhost:8080'
            echo 'Swagger UI at http://localhost:8080/swagger-ui.html'
        }
        failure {
            echo '❌ Pipeline failed. Check logs above for details.'
        }
        always {
            echo 'Cleaning up...'
            sh 'docker logout || true'
        }
    }
}