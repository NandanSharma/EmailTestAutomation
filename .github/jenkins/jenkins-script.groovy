pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/NandanSharma/EmailTestAutomation.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Start Selenium Grid') {
            steps {
                sh 'docker-compose -f /home/ec2-user/selenium-grid/docker-compose.yml up -d'
            }
        }
        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Stop Selenium Grid') {
            steps {
                sh 'docker-compose -f /home/ec2-user/selenium-grid down'
            }
        }
    }
    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}