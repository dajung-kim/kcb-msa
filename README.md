# KCB Micro Service Tutorial

본 프로젝트는 Spring Boot 기반의 Micro Service Architecture 구성을 위한 간단한 프로젝트 입니다. 
Gradle 기반의 Multi Project 구조로 구성되어 있습니다.

## TO DO 

추가적으로 아래 항목들을 진행 예정입니다.

- [ ] 각 Micro Service 별 간단한 API 개발
- [ ] Profile 커스터마이징 테스트
- [ ] kafka 등의 Message Queue 연결

## Requirements

개발환경을 구성하고자 하는 컴퓨터에는 다음과 같은 환경/프로그램이 설치되어있어야 합니다.

- 인터넷 가능
- [Java 8 이상](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- Java IDE: IntelliJ Idea를 권장하며, Eclipse(STS)도 사용가능합니다.
- [git](https://git-scm.com/downloads)

Windows의 경우 [Chocolatey](https://chocolatey.org), macos의 경우 [homebrew](https://brew.sh/index_ko)를 설치하시면 더 간단히 설치를 진행할 수 있습니다.

## Installation

로컬 개발환경에서 다음과 같이 Git 저장소를 내려받습니다.

```bash
git clone https://github.com/korea-credit-bureau/kcb-msa
```

내려받은 저장소 디렉토리로 이동해서 어플리케이션을 시작합니다. Gradle 기반의 Multi Project 구조로 이루어져 있기 때문에 여러개의 서비스 어플리케이션을 동시에 실행해주어야 합니다.

```bash
cd kcb-msa
./gradlew --parallel bootRun
```

또는 IntelliJ 사용시 Run Dashboard에서 전체 선택 후 왼쪽의 재생버튼을 눌러도 됩니다.
 
이후 아래 URL에 각각 접근 시 웹 페이지(HAL Browser)가 나타난다면 정상적으로 개발환경 구성이 완료 된 것입니다.

> USER: http://localhost:11000  
> CARD: http://localhost:12000  
> LOAN: http://localhost:13000

## Lineup

각 서비스별 용도 및 사용 포트는 아래와 같습니다.

### 공통 영역

본 샘플에서 공통영역은 100xx 번대 포트를 사용합니다.

| 서비스명 | 포트 | 용도 | 비고 | 
|--------|----|----|----|
| Eureka | 10001 | Service Discovery Register | 각각의 Micro Service를 중앙 관리하는 기능 |
| Hystrix Dashborad | 10002 | Circuit Breaker 모니터링 | 각 Micro Service르 트래픽을 모니터링 하는 기능 |
| Turbine | 10003 | Hystrix Cluster | Hystrix 모니터링시 Micro Service를 클러스터링하기 위한 기능 |
| Gateway | 10004 | Edge Service | 각각의 Micro Service로 라우팅을 위한 클라이언트 관점의 End Point |  

### 업무 영역

본 샘플에서 업무영역(업무 도메인 관련 각각의 Micro Service)은 1xxxx 번대 포트를 사용합니다. 

| 서비스명 | 포트 | 용도 | 비고 | 
|--------|----|----|----|
| User | 11000 | User Service | 사용자 계정 관련 Micro Service |
| Card | 12000 | Card Service | 신용카드 관련 Micro Service |
| Loan | 13000 | Loan Service | 대출 관련 Micro Service |

## Service Discovery(Eureka)

본 서비스에서는 Spring Cloud Eureka를 사용하여 내부 도메인을 동적으로 구성합니다. 
이를 위해 Eureka 서버를 별도로 띄워야 하는데, 위 Installation 섹션에서 `./gradlew --parallel bootRun` 명령이 정상적으로 실행되었으면 Eureka 서버가 자동 실행됩니다.

Eureka는 다음과 같이 접속 가능합니다.

> http://localhost:10001

접속 후 화면 중간의 Instance 목록에 4개 서비스가 표시되면 정상적으로 환경 구성이 완료 된 것입니다. 이제 모든 Micro Service 들은 Eureka 서버를 통해서 자기 자신의 서비스를 관리받게 됩니다.

## API Gateway(Zuul)

클라이언트 관점에서 API 서비스를 이용할 때는 각각의 마이크로 서비스에 직접 접속할 수도 있지만(예. GET http://localhost:11000/users/1), 
사실 굳이 각 서비스별 각각의 URL을 알기란 쉽지도 않고 서비스 제공자 입장에서도 모든 URL을 굳이 노출할 필요가 없습니다.
이를 위해 API Gateway에서 모든 요청을 받아서 처리할 수 있는 디자인 패턴이 존재 하는데, 이렇게 맨 앞단에서 클라이언트의 요청을 받아서 처리해 주는 서비스를 `Edge Service`라고 합니다.

현재 Eureka 서버의 인스턴스 목록을 보면 `GATEWAY`라는 항목이 있습니다. 
클라이언트가 Gateway 서비스 포트를 이용해서 요청을 보내면, Gateway는 Zuul Proxy 및 Eureka 정보를 바탕으로 해당 요청이 어느 Micro Service로 전달될지를 판단해서 라우팅을 수행합니다.    
이를 통해서 클라이언트 또는 웹서버단에는 10004 포트만 노출하면 되고 나머지 포트 정보는 숨길 수 있습니다.

| 클라이언트가 요청하는 주소         | 실제 서비스 되는 주소              |
|--------------------------------|--------------------------------| 
| http://localhost:10004/users/1 | http://localhost:11000/users/1 |
| http://localhost:10004/cards/1 | http://localhost:12000/cards/1 |
| http://localhost:10004/loans/1 | http://localhost:13000/loans/1 | 

그 외 기타 설정을 통해서 여러가지 API 조합도 가능하게 됩니다(TBD).   

## API 개발 가이드

클라이언트에서 사용할 수 있는 API 개발을 위해 크게 아래 두가지 작업이 필요합니다.

- 업무레벨 서비스 개발(비즈니스 로직)
- Gateway 라우팅 설정

### 업무레벨 서비스 개발

예를들어 특정 사용자의 신용카드 목록을 조회하는 API가 있다고 하면 /card 모듈에서 적절한 컨트롤러와 서비스를 연결하여 json 데이터를 리턴하도록 합니다.
개발이 완료되면 아래와 같이 API 사용이 가능할 것입니다.

> http://localhost:12000/user/1/cards

하지만 클라이언트에서는 해당 URL을 바로 사용하지 않을 것입니다. 클라이언트에게는 Edge Service만 노출될 것이기 때문입니다. 
해당 API가 정상적으로 완료된다면 이제 API Gateway에 해당 서비스를 노출합니다.

### Gateway(Zuul proxy) 라우팅 설정

Gateway > application.yaml 파일에 다음과 같이 라우팅 설정이 되어있으면 됩니다. 차후에 라우팅 규칙이 복잡해질 수록 여기에 설정이 추가되거나 Zuul Filter를 통해서 URL을 Rewrite 하는 방식으로 대응할 수 있습니다.

```yaml
zuul:
  routes:     
    user: # user 서비스로 보낼 uri
      path: /users/**
      stripPrefix: false      
    loan: # loan 서비스로 보낼 uri
      path: /loans/**
      stripPrefix: false    
    card: # card 서비스로 보낼 uri  
      path: /cards/**
      stripPrefix: false
```

## Circuit Breaker

각각의 Micro Service 내에서 오류가 발생하는 경우, 그리고 이러한 오류가 지속되는 경우 해당 인스턴스를 차단하여 서비스 가용성 저하를 방지할 수 있습니다. 
이렇게 오류가 발생하는 인스턴스로의 트래픽을 차단해 주는 기능을 Circuit Breadker라고 하는데 본 프로젝트에서는 Netflix Hystrix를 활용하여 이를 구현하였습니다.

Hystrix는 현재 트래픽에 대한 모니터링 대시보드를 제공하는데 다음 URL에 접속해서 확인할 수 있습니다.

> http://localhost:10002/hystrix

화면에 URL을 입력하는 input에 `http://localhost:10003/turbine.stream`를 입력하면 모니터링을 위한 대시보드 준비가 완료된 것입니다. 
최초에는 빈 화면이 나오는데 트래픽이 발생하게 되면 그 때부터 모니터링 항목이 조회됩니다. 모니터링을 위한 샘플 API(http://localhost:10004/loans/hello?name=world)를 호출해 보시기 바랍니다.   

## 빌드 설정

본 프로젝트의 빌드는 Jenkins를 기반으로 구성되어 있습니다. 빌드는 Declarative pipeline 으로 작성 되어 있습니다. 빌드 환경은 아래에서 접속 가능합니다(AWS Workspace에서 확인 가능).

> http://10.0.2.15:10000/blue/organizations/jenkins/kcb-msa-pipeline/activity  또는  
> http://jenkins.koreacb.kr:10000/blue/organizations/jenkins/kcb-msa-pipeline/activity

각 단계에 대해서는 아래 스크립트 및 주석을 참고 해 주시기 바랍니다.
 
```jenkinsfile
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
        // Gateway 및 Micro Service 시작
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
```