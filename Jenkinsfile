pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo '📥 Obteniendo código fuente...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '🔨 Compilando aplicación...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo '🧪 Ejecutando pruebas unitarias...'
                sh 'mvn test -Djacoco.skip=true -Dsurefire.excludes=**/SeleniumExampleTest.java'
            }
        }

        stage('Docker Build') {
            steps {
                echo '🐳 Construyendo imagen Docker...'
                sh 'docker build -t mi-app:latest .'
            }
        }

    }

    post {
        success {
            echo '✅ Pipeline ejecutado exitosamente!'
        }
        failure {
            echo '❌ El pipeline falló. Revisa los logs arriba.'
        }
        always {
            cleanWs()
        }
    }
}