# API 서버

사용자 API 서버

# 계층

## controller

클라이언트의 요청을 받는 최전방 계층

### 패키지 구분 기준


## dto

클라이언트로부터 받은 데이터나, 클라이언트에게 반환해줄 데이터를 담아서 전달하는 Data Transfer Object

- XXXIn  
  - 클라이언트로부터 입수되는(==  API 서버에 들어온 == in) 데이터 객체
  - 다른 서버로부터 반환되는(==  API 서버에 들어온 == in) 데이터 객체
- XXXOut
  - 클라이언트에게 반환해주는(== 서버로부터 나가는 == out) 데이터 객체
  - 다른 서버에게 전송되는(== 서버로부터 나가는 == out) 데이터 객체



### dto 사용 구간

컨트롤러 계층과 서비스 계층에서만 사용된다

```
컨트롤러 <---> 서비스 <---> 도메인

 dto -------- dto
 
              entity --- entity
```

## service

최전방에서 클라이언트의 요청을 받는 컨트롤러 계층과, 비즈니스 로직 및 데이터 CRUD를 담당하는 도메인 계층 사이에서 응용 로직(dto-entity 변환, 트랜잭션 관리, 타 시스템 호출 담당)을 담당

### 패키지 구분 기준

크게 command 와 query 로 구분
- command: 데이터의 변경(CUD, Create, Update, Delete)을 수반하는 서비스
- query: 데이터 변경을 후반하지 않는 조회(Query) 서비스


# 설정

## CacheConfig

- 인메모리 캐시 ehcache 사용을 위한 설정
- 실제 캐시 사용 설정은 resources/ehcache/ehcache.xml 에 있음

## CommonLibConfig

- common 서브모듈에 있는 공통 라이브러리를 참조할 수 있게 해주는 설정

## JpaConfig

- common 서브모듈에 있는 entity, repository 를 참조할 수 있게 해주는 설정

## OpenApiConfig

- OpenApi(Swagger) 관련 설정

## SecurityConfig

- 스프링 시큐리티 설정
