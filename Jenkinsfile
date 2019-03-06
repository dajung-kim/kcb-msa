pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh './gradlew --parallel --max-workers=5 clean assemble'
      }
    }
    stage('Run') {
      steps {
        sh 'nohup ./gradlew --parallel --max-workers=5 bootRun &'
      }
    }
  }
}