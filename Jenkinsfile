def services = ["eureka", "hystrix-dashboard", "turbine", "gateway", "user", "card", "loan"]
def ports = ["10001", "10002", "10003", "10004", "11000", "12000", "13000"]

def startAll = services.collectEntries {
  ["${it}" : startup(it)]
}

def shutdownAll = ports.collectEntries {
  ["${it}" : shutdown(it)]
}

def startup(service) {
  return {
    stage("start ${service}") {
      withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
//        sh "./gradlew ${service}:bootRun & > ./${service}/nohup.out"
        sh "java -jar ./${service}/build/libs/${service}-0.0.1-SNAPSHOT.jar & > ./${service}/nohup.out"
      }
    }
  }
}

def shutdown(port){
  return {
    stage("shutdown ${port}") {
      sh "curl -i -X POST http://localhost:${port}/actuator/shutdown &"
    }
  }
}

pipeline {
  agent any
  stages {
    // 소스코드 컴파일 및 jar 패키징
    stage('Build') {
      steps {
        script {
          sh './gradlew --parallel clean assemble'
        }
      }
    }
//    // 모든 프로세스 종료
//    stage('Stop All Running Tasks') {
//      steps {
//        script {
//          sh './gradlew --stop'
//        }
//      }
//    }

    stage('Shutdown'){
      steps {
        script {
          parallel shutdownAll
        }
      }
    }
    stage('Run') {
      steps {
        script {
          parallel startAll
        }
      }
    }
//    stage('Run Services') {
//      parallel {
//      // 유레카 서버 시작
//        stage('Run Eureka') {
//          steps {
//            script {
//              withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
//                sh './gradlew eureka:bootRun &'
//              }
//            }
//          }
//        }
//        // 나머지 서버
//        stage('Run Micro Service') {
//          steps {
//            withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
//              sh './gradlew --parallel --max-workers=4 user:bootRun card:bootRun loan:bootRun gateway:bootRun &'
//            }
//          }
//        }
//      }
//    }
  }
}
