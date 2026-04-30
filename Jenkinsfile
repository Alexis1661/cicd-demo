pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('SONAR_TOKEN')
    }

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
                sh 'mvn test -Djacoco.skip=true'
            }
        }

        stage('Static Analysis (SonarQube)') {
            steps {
                echo '🔍 Analizando calidad de código...'
                sh """
                    mvn sonar:sonar \
                        -Dsonar.projectKey=cicd-demo \
                        -Dsonar.projectName=cicd-demo \
                        -Dsonar.host.url=http://sonarqube:9000 \
                        -Dsonar.token=${SONAR_TOKEN}
                """
            }
        }

        stage('Container Security Scan (Trivy)') {
            steps {
                echo '🛡️ Escaneando imagen Docker con Trivy...'
                sh 'docker build -t mi-app:latest .'
                sh 'trivy image --timeout 10m --scanners vuln --exit-code 0 --severity HIGH,CRITICAL mi-app:latest'
            }
        }

        stage('Quality Gate') {
            steps {
                echo '🚦 Verificando Quality Gate...'
                script {
                    def qg = sh(
                        script: """
                            curl -s -u admin:admin123 \
                            "http://sonarqube:9000/api/qualitygates/project_status?projectKey=cicd-demo" \
                            | grep -o '"status":"[^"]*"' | head -1
                        """,
                        returnStdout: true
                    ).trim()
                    echo "Quality Gate status: ${qg}"
                    if (qg.contains('ERROR')) {
                        error '❌ Quality Gate falló!'
                    }
                }
            }
        }

        stage('Deploy') {
            when { branch 'main' }
            steps {
                echo '🚀 Desplegando aplicación...'
                sh 'docker stop mi-app-container || true'
                sh 'docker rm mi-app-container || true'
                sh 'docker run -d --name mi-app-container -p 8081:8080 mi-app:latest'
                echo '✅ Aplicación desplegada en http://localhost:8081'
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