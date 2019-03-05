pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '/var/jenkins_home/workspace/kcb-msa/gradlew --parallel --max-workers=5 clean assemble'
      }
    }
    stage('Run') {
      steps {
        sh 'nohup /var/jenkins_home/workspace/kcb-msa/gradlew --parallel --max-workers=5 bootRun &'
      }
    }
  }
}