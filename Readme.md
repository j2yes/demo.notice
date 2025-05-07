## Notice Application

간단한 공지사항 관리 Application 을 구현하였습니다.

시스템은 공지사항에 대한 등록, 수정, 삭제, 상세조회, 목록조회 API 를 제공합니다. 

## 사용 기술

- [X] Java 17
- [X] Spring Boot 3.4.5
- [X] Spring Data JPA
- [X] QueryDSL
- [X] MySQL
- [X] Redis
- [X] Docker, Docker Compose 
- [X] Swagger
- [X] Lombok 

## API 

API 에 대한 자세한 명세와 호출 테스트는 프로젝트 실행 후 [Swagger](http://localhost:8080/notice/swagger.html) 를 통해 확인할 수 있습니다. 

### 공지사항 등록 

```http request
POST /api/v1/notices
```
### 공지사항 수정

```http request
PUT /api/v1/notices/{id}
```

### 공지사항 삭제 

```http request
DELETE /api/v1/notices/{id}
```

### 공지사항 상세조회 

```http request
GET /api/v1/notices/{id}
```

### 공지사항 목록 조회 

```http request
GET /api/v1/notices
```

## 프로젝트 설명  

### 패키지 구조 

```text
- main.java.com.demo.notice
  - application 
    - port : 애플리케이션 외부 시스템과 연동에 사용하는 추상화 레이어  
    - command : 서비스 레이어로 데이터 생성 또는 수정을 요청하는 DTO  
    - query : 서비스 레이어로 조회 요청을 위한 DTO 
    - usecase : 비즈니스 업무의 기능별로 분리한 추상화 레이어   
    - service : usecase 를 구현한 서비스 
    - schedule : client 요청이 아닌 애플리케이션 스스로 트리거가 필요한 작업 수행 
  - domain : 외부환경에 영향받지 않고, 애플리케이션의 비즈니스를 표현하는 도메인 객체 관리 레이어 
  - global : 프로젝트 전체에서 공통적으로 사용되는 설정, 유틸, 예외 등 정의   
  - infrastructure : DB 또는 외부 API 등 외부 연동을 위한 레이어 설정 
    - adapter : port 인터페이스의 구현체 
    - mapper : domain 모델과 db 모델 사이의 객체 변환 매퍼 
    - persistence : db 영속성 관리     
  - presentation : client (front-end) 와 인터페이스를 위한 레이어
    - advice
    - controller  
```

### 구현 관련 

- 시스템 구현에서 가장 큰 병목이 될 수 있다고 판단한 조회수 관리에 중점을 두고 구현하였습니다.
- 몇 가지 추가로 개선이 필요하다고 생각한 내용입니다. 
  - 유효성 처리 고도화 (필드별 제한 추가적용, 조건부 유효성 검사 설정)
  - 첨부 삭제 및 첨부 다운로드 API 구현 
  - 데이터 양이 많다면 검색 위한 검색 (인덱스) DB 서버를 별도로 구성하여 사용
  - 데이터 상세 조회에 대한 캐시 적용 
  - 첨부 삭제 시 첨부된 물리 파일에 대한 비동기 삭제 (Eventually Consistency) 처리

## 개발환경 설정

### Code Style 설정

Java 코드 스타일을 맞추기 위해 `intellij-java-google-style.xml` 적용

IntelliJ 적용 방법 : Settings -> Editor -> Code Style -> Java -> Import Scheme (`./intellij-java-google-style.xml` 경로의 xml import)

### Docker 설정

로컬환경에 Docker 가 설치되어 있지 않으면 [Docker](https://www.docker.com/) 공식 사이트에서 다운로드받아 설치할 수 있습니다.

`spring-boot-docker-compose` 를 사용하기 때문에 특별한 작업없이 로컬환경에서 자동으로 docker compose 가 실행됩니다. 

Manual 실행이 필요한 경우 아래 Command 를 이용하시면 됩니다.

#### 서비스 시작:

```bash
docker-compose -f ./compose.yml up -d
```

#### 서비스 중지:

```bash
docker-compose -f ./compose.yml down
```

#### 서비스 상태 확인:

```bash
docker-compose -f ./compose.yml ps
```

#### 로그 확인:

```bash
docker-compose -f ./compose.yml logs [서비스이름]
```

### 프로젝트 실행 방법

```shell
# 실행 방법 1
./gradlew bootJar
# jar 필드 후 실행 
java -jar ./build/libs/notice-0.0.1-SNAPSHOT.jar
```

```shell
# 실행 방법 2
./gradlew bootRun
```
