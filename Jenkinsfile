pipeline {
  agent any
  stages {
    // 소스코드 컴파일 및 jar 패키징
    stage('Build') {
      steps {
        script {
          sh './gradlew --parallel --max-workers=5 clean assemble'
        }
      }
    }
    // 모든 프로세스 종료
    stage('Stop All Running Tasks') {
      steps {
        script {
          sh './gradlew --stop'
        }
      }
    }
    stage('Run Services') {
      parallel {
        // 유레카 서버 시작
        stage('Run Eureka') {
          steps {
            script {
              withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
                sh './gradlew eureka:bootRun &'
              }
            }
          }
        }
        // 나머지 서버
        stage('Run Micro Service') {
          steps {
            withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
              sh './gradlew --parallel --max-workers=4 user:bootRun card:bootRun loan:bootRun gateway:bootRun &'
            }
          }
        }
      }
    }
  }
}
