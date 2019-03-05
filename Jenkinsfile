pipeline {
  agent any
  stages {
    stage('Git pull master') {
      steps {
        git(url: 'https://github.com/korea-credit-bureau/kcb-msa', branch: 'master')
      }
    }
    stage('Build') {
      steps {
        sh '/var/jenkins_home/workspace/kcb-msa/gradlew clean assemble'
      }
    }
    stage('Run') {
      steps {
        sh 'nohup /var/jenkins_home/workspace/kcb-msa/gradlew --parallel --max-workers=5 bootRun &'
      }
    }
  }
}