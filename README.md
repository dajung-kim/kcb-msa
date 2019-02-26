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
./gradlew --parallel --max-workers=4 bootRun
```
 
이후 아래 URL에 각각 접근 시 웹 페이지(HAL Browser)가 나타난다면 정상적으로 개발환경 구성이 완료 된 것입니다.

> http://localhost:11000/user  
> http://localhost:12000/card  
> http://localhost:13000/loan
