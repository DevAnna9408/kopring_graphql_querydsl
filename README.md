# Kotlin Spring Boot 클린 프로젝트

- Kotlin, Gradle, Spring Boot MVC, JPA 기반의 웹 애플리케이션

## 버전
> Java 11, Spring Boot 2.5.4, gradle 6.9.1 기반

## 최상위 폴더 구조

- `/api` :  api를 모아둔 모듈
- `/buildSrc` : gradle 멀티 프로젝트 생성 시 자동으로 생성되는 폴더
  - 공통 의존관계를 관리할 수 있을 것으로 보이지만 그렇게 사용하지는 않고 ,gradle 표준 구성으로 보임
- `/common` : 공통 비즈니스 모델(domain)과 라이브러리(lib)를 모아둔 모듈

## 레이어 구성

### 컨트롤러
- 클라이언트의 요청을 받아서 서비스를 호출해서 처리 후 결과를 클라이언트에 반환해주는 역할

### 서비스
- 컨트롤러로부터의 요청을 받아서 핵심 비즈니스 로직을 가지고 있는 도메인 엔티티에 위임해서 요청을 처리한 후 컨트롤러에게 반환
- 서비스 레이어는 주로 트랜잭션 관리와 외부 서비스 호출 등 응용 로직(application logic)을 담당, 핵심 비즈니스 로직 처리는 도메인 엔티티에서 담당
- 컨트롤러로부터 받은 XxxIn 객체와 DB를 조회해서 가져온 도메인 엔티티를 활용해서 요청 처리 후 XxxOut.fromXxx() 메서드를 활용해서 XxxOut 객체 생성 후 컨트롤러에 전달

### 도메인
- 핵심 비즈니스 로직(business logic)을 갖고 있으며 엔티티, 리포지토리 모두 도메인 계층에 존재

## 구현 주요 패러다임

### 객체 지향
> 객체에게 데이터를 요구하지 말고 작업을 요청하라

###  Rich Domain Model
- 핵심 비즈니스 로직을 서비스 계층에 두고 도메인 엔티티에는 getter/setter 만 두고 DB 컬럼과 매핑하는 역할로만 사용하는 Anemic Domain Model은 지양
- Anemic Domain Model에는 데이터만 있기 때문에 핵심 비즈니스 로직이 여러 서비스에 분산/중복 구현되는 것을 막기 어려워 유지보수가 어려워짐
- 핵심 비즈니스 로직을 도메인 엔티티에 두는 Rich Domain Model 을 사용하면 데이터가 있는 곳에서 데이터 처리를 수행할 수 있어 응집도가 높아지고 결합도가 낮아져 유지보수성이 좋아짐
- 서비스 레이어에는 다른 서비스나 Repository 등의 의존 관계가 존재하고 있고 단위 테스트를 하려면 의존하는 것들을 모두 Mock으로 만들어야 하므로 핵심 비즈니스 로직을 서비스 레이어에 두면 테스트 작성 비용 증가
- 도메인 엔티티 레이어는 다른 계층에 대한 의존 관계가 없어 단위 테스트 작성이 상대적으로 쉽고 실행하기도 가벼움

###  CQRS
- Command Query Responsibility Segregation: 명령/조회 책임 분리
- 데이터를 변경하는 CUD(Create/Update/Delete)를 담당하는 Command와 데이터를 조회하는 R(Retrieve)을 담당하는 Query로 서비스 계층을 분리

* **Command**
  > Rich Domain Model과 융합되어 데이터 변경은 도메인 엔티티에서 담당

  * 특정 비즈니스 로직의 대표 객체인 A에 B가 포함돼 있고, B에 C가 포함돼 있다고 할 때 데이터은 다음과 같은 위임을 통해 처리됨
  - xxxCommandService.changeXxx() -> aRepository.getByAaa() -> a.changeXxx() -> b.changeXxx() -> c.changeXxx()-여기에서 실제 데이터 변경
  - 서비스에 BRepository, CRepository 등의 의존 관계가 필요 없어지므로 결합도가 낮아짐

* **Query**
  > 조회는 데이터를 변경하지 않으므로 편하게 구현 가능
  * xxxQueryService.getXxx() -> b.getXxx() -> c.getXxx() 와 같이 비즈니스 로직에 무게를 두고 위임을 통해 구현할 수도 있고
  - cRepository.getXxx() 와 같이 성능에 무게를 두고 구현할 수도 있음


### DTO <-> DomainEntity 변환은 DTO에서 담당
- DTO는 도메인 엔티티 외에는 다른 의존 관계가 없으므로 테스트 용이
- In DTO -> Domain Entity, Domain Entity -> Out DTO 변환 로직을 DTO에서 담당하게 하면 테스트 작성/실행 비용 절감
```  
data class UserOut( 
 val userId: String, 
 val name: String
 ) { 
	companion object { 
		fun fromEntity(e: User): UserOut { 
		return UserOut( 
			userId = e.userId,
			 name = e.name()
			 ) } }}  
```  



### 가벼운 Controller, Service
- Rich Domain Model, CQRS, DTO역할 패러다임을 잘 지키면,
  - 컨트롤러에는 서비스를 호출하고 결과를 반환하는 부분만 필요하므로 구현할 내용이 거의 없음
  - 특정 API가 호출되는 라우팅 기능, 클라이언트가 보낸 정보를 역직렬화하고 `@RequestBody`에 담는 기능은 개발자가 작성한 게 아니라 스프링 프레임워크에서 담당하는 기능이므로 단위 테스트 대상이 아님
  - 컨트롤러에는 서비스를 호출하는 당연한 로직만 남으므로 단위 테스트 거의 불필요
  - 서비스에는 트랜잭션 관리, 외부 서비스 호출 등의 책임만 남으므로 구현할 내용과 테스트 할 내용이 많이 줄어듬

## 주요 공통 로직

### 데이터 저장/조회

#### JPA
- Java 표준 데이터 저장 API
- JPA는 표준 인터페이스, Hibernate는 JPA 구현체

#### QueryDsl
- JPA보다 쿼리 작성, 페이징 처리 등을 쉽게 해주는 라이브러리
- 개발자가 작성한 도메인 엔티티로부터 QClass를 자동 생성해서 쿼리에 사용

### 인증(Authentication) : 계정 확인
- 인증 수단으로 JWT(Java Web Token) 사용
  - HS512 알고리즘을 사용할 것이기 때문에 512bit, 즉 64byte 이상의 secret key를 사용해야 한다.
  -  Secret 값은 특정 문자열을 Base64 로 인코딩한 값 사용
- 로그인 전체 흐름은 스프링 시큐리티를 따름
  - HTTP Authorization 헤더에 들어 있는 JWT를 파싱해서 필요한 값을 SecurityContextHolder 에 저장
  - `JwtAuthFilter` `JwtProcessor /parse`등에서 발생하는 익셉션을 구분하지않고 , `JwtAuthenticationEntryPoint` 에서 401인증 실패로 모아서 처리한다

#### 인증 흐름
- ID/PWD 가 확인되면 서버가 JWT를 발급하고 클라이언트에게 반환
- 클라이언트는 JWT를 저장하고 이후 요청 보낼때 마다 HTTP의 Authorization 헤더에 `Bearer 발급받은JWT`를 포함
- 서버는 들어오는 요청마다 HTTP Authorization 헤더에서 JWT 값을 읽고 `JwtProcessor` 통해서 db에서 해당정보를 확인 - 사용자 정보를  스프링 SecurityContextHolder에 저장
- 로그인 상태가 서버에 저장되지 않고, 인증에 필요한 정보가 클라이언트의 HTTP 요청 헤더에 들어있음
  - 서버 인스턴스가 여러개일때도 로그인 상태 공유를 위한 세션 클러스터링이 필요하지 않음

### 인가(Authorization)   : 계정의 권한

#### 인가 흐름
- 서버는 JWT를 파싱해서 획득한 정보가 저장된 SecurityContextHolder에서 권한 정보를 읽음
  - JwtAuthFilter가 통과되면 ,SecurityContextHolder에 `AuthUser` 객체저장함
- 스프링 시큐리티 `CustomAccessDeniedHandler` 인가에러 핸들러 등록
- 계정 자체의 권한뿐 아니라 사용자의 요청 정보에 따라 인가 여부가 달라질 수 있는 경우 서비스 레이어에서 인가 처리 수행
  - 인가 처리 로직은 common/lib/security/SecurityUtil.kt 에 담아 공통화하고 서비스 레이어는 SecurityUtil 에 인가 처리 위임

### 예외 처리

- 예외가 발생하는 곳에서 즉시 Exception을 던지는 것이 기본
- 던져지 예외를 클라이언트에게 반환하는 최종 처리 로직은 common/lib/error/GlobalExceptionHandler에 집중

## common 서브 모듈  _domain
> 핵심 로직 처리 담당

### _common
- `SqlUtil` : 쿼리dsl에서 자주 사용하는 함수
- EnumModel을 구현한 enum으로 코드 구현, EnumMapper를 통해 등록된 enum조회가능

### config
- domain에서 사용하는 다국어설정
- 쿼리dsl에서 sql함수사용 설정
- JPA 가 실행하는 쿼리와 파라미터를 볼 수 있게 해주는 P6Spy 설정

### converter
- 데이터베이스의 데이터 타입과 애플리케이션 소스 코드의 데이터 타입 변환 담당
  - Y/N
  - EnumToList
  - StringToList
  - ListToPage

### exception
- 도메인 엔티티에서 비즈니스 로직 처리시 발생할 수 있는 예외 클래스

### model
- 도메인 엔티티 클래스

### projection
- 쿼리 최적화를 위해 도메인 엔티티의 일부 속성만 조회할 때 사용

### repository
- DB CRUD 담당

## common 서브 모듈  _lib
> 여러 서브모듈에서 참조하는 공통 라이브러리

### config
- 다국어 설정

### error
- 공통 예외 처리 로직

## api 서브 모듈

### config
- 각종 설정 파일
- 개별 파일별 주석 참고

### controller
- 클라이언트에게 제공하는 api

### dto
-  클라이언트로부터 받거나 클라이언트에게 반환되는 객체를 DTO라고 부름
- 클라이언트로부터 받는 DTO와 클라이언트에게 반환하는 DTO를 각각 XxxIn, XxxOut 으로 구분해서 책임 분리

### filter
- api 서브모듈에서만 사용하는 서블릿 필터
  - 여러 서브모듈에서 사용된다면 common/lib 으로 이동하는 것이 적절

### interceptor
- 스프링 인터셉터
  - 여러 서브모듈에서 사용된다면 common/lib 으로 이동하는 것이 적절
- WebMvcConfig 에서 스프링 인터셉터 설정 - 특정 url에 대한 로깅처리(개인정보)

### service
- 데이터 변경을 수반하는 command와 데이터 변경 없이 조회만 수행하는 query로 책임 분리(CQRS)

### util
- 특정 레이어에 두기 애매한 유틸 성 로직 모음

## 일반적인 작업 순서

1. 요구사항에 맞는 api 설계
- 어떤 클라이언트에게 제공할 api 인지 결정
- 클라이언트에게 어떤 데이터를 받아서 어떤 데이터를 반환해주면 되는지 파악 및 결정
- 클라이언트에게 받는 데이터가 특정 리소스의 식별자인 경우 `@PathVariable`를, 필드별 조회 조건인 경우 `@RequestParam`를 사용
- 데이터 변경에 필요한 데이터는 `@PathVariable`나 `@RequestParam`가 아니라 `@RequestBody`로 받는다
- REST API 설계 시 https://tv.naver.com/v/2292653 참고
- 다만 REST API는 말 그대로 자원 기준의 API 이고, 실제 현업에서는 자원 기준만으로 API를 표현하지는 않으므로 반드시 엄격하게 지킬 필요까지는 없음
2. 요구사항 처리를 담당해야할 도메인 엔티티 걸정 및 비즈니스 로직 구현
- Rich Domain Model 패러다임에 따라 데이터 변경은 도메인 엔티티에서 수행
- 요구사항 처리에 필요한 데이터 변경이 어느 도메인 엔티티에서 수행돼야 하는지 결정 후 비즈니스 로직도 해당 도메인 엔티티에 추가
3. 도메인 엔티티에 요구사항 처리를 위임할 서비스 응용 로직 구현
- CQRS 패러다임에 따라 요구사항이 command인지 query인지 구별하고 적합한 곳에 응용 로직(트랜잭션 처리, 외부 서비스 호출 등) 구현
4. DTO 구현
- In DTO인 경우 도메인 엔티티로 변환하는 로직 구현
- Out DTO인 경우 도메인 엔티티로부터 Out DTO를 변환하는 로직 구현
5. Controller 구현
- 1에서 설계한 내용에 따라 컨트롤러 구현  
  

