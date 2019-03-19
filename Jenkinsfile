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
        sh "java -jar ./${service}/build/libs/${service}-0.0.1-SNAPSHOT.jar &"
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

    stage('Checkout') {
      steps {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'github', url: 'https://github.com/korea-credit-bureau/kcb-msa/']]])
      }
    }

    // 소스코드 컴파일 및 jar 패키징
    stage('Build') {
      steps {
        script {
          sh './gradlew --parallel clean assemble'
        }
      }
    }

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
  }
}
