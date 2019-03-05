# KCB Micro Service Tutorial

본 프로젝트는 Spring Boot 기반의 Micro Service Architecture를 구성하기 위한 간단한 프로젝트 입니다. Gradle 기반의 Multi Project 구조로 구성되어 있습니다.

## Requirements

개발환경을 구성하고자 하는 컴퓨터에는 다음과 같은 프로그램이 설치되어있어야 합니다.

- [Java 8 이상](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- Java IDE: IntelliJ Idea를 권장하며, Eclipse도 사용가능합니다.
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
 
이후 아래 URL에 각각 접근 시 웹 페이지(HAL Browser)가 나타난다면 정상적으로 개발환경 구성이 완료 된 것입니다.

> USER: http://localhost:11000  
> CARD: http://localhost:12000  
> LOAN: http://localhost:13000

## Service Discovery

본 서비스에서는 Spring Cloud Eureka를 사용하여 내부 도메인을 동적으로 구성합니다. 
이를 위해 Eureka 서버를 별도로 띄워야 하는데, 위 Installation 섹션에서 `bootRun` 태스크 실행을 통해 Eureka 서버가 자동 실행됩니다.

Eureka는 다음과 같이 접속 가능합니다.

> http://localhost:8761

접속 후 화면 중간의 Instance 목록에 4개 서비스가 표시되면 정상적으로 환경 구성이 완료 된 것입니다. 이제 모든 Micro Service 들은 Eureka 서버를 통해서 자기 자신의 서비스를 관리받게 됩니다.

## Edge Service

클라이언트 관점에서 API 서비스를 이용할 때는 각각의 마이크로 서비스에 직접 접속할 수도 있지만(예. GET http://localhost:11000/users/1), 
사실 굳이 각 서비스별 각각의 URL을 알기란 쉽지도 않고 서비스 제공자 입장에서도 모든 URL을 굳이 노출할 필요가 없습니다.
이를 위해 API Gateway에서 모든 요청을 받아서 처리할 수 있는 디자인 패턴이 존재 하는데, 이렇게 맨 앞단에서 클라이언트의 요청을 받아서 처리해 주는 서비스를 `Edge Service`라고 합니다.

현재 Eureka 서버의 인스턴스 목록을 보면 `EDGE-SERVICE`라는 항목이 있습니다. 해당 서비스가 Gateway 역할을 통해서 나머지 Micro Service에 직접 요청을 날려주는 일종의 프록시 서버 역할을 하게 되고, 이로 인해 클라이언트에서는 다음과 같이 API를 호출하면 됩니다.

> http://localhost:14000/api/hello

여기서 14000 포트는 EDGE-SERVICE의 서비스 포트입니다. 

## API 개발 가이드

클라이언트에서 사용할 수 있는 API 개발을 위해 크게 아래 두가지 작업이 필요합니다.

- 업무레벨 서비스 개발(비즈니스 로직)
- Feign API(Edge Service) 추가

### 업무레벨 서비스 개발

예를들어 특정 사용자의 신용카드 목록을 조회하는 API가 있다고 하면 /card 모듈에서 적절한 컨트롤러와 서비스를 연결하여 json 데이터를 리턴하도록 합니다.
개발이 완료되면 아래와 같이 API 사용이 가능할 것입니다.

> http://localhost:12000/user/1/cards

하지만 클라이언트에서는 해당 URL을 바로 사용하지 않을 것입니다. 클라이언트에게는 Edge Service만 노출될 것이기 때문입니다. 
해당 API가 정상적으로 완료된다면 이제 API Gateway에 해당 서비스를 노출합니다.

### Feign API 추가

아래와 같이 인터페이스를 추가합니다. 인터페이스 갯수는 유레카에서 관리되는 서비스 갯수만큼 존재하면 됩니다. 즉 Micro Service가 3개 라면 각 3개 서비스에 대한 인터페이스가 따로 필요합니다.
```java
@FeignClient(name = "user")  // eureka에 등록된 인스턴스 명
public interface UserClient {
    @GetMapping(value = "/users/{id}")  // user 서비스에서 제공하는 API URI
    String getUserInfo(@PathVariable("id") String id);  // 해당 API의 포맷
}
```

