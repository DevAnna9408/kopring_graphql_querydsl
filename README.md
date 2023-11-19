# Kopring + GrapqhQl + QueryDsl ver.1 by anna ver 1.0

[Swagger](http://localhost:8080/swagger-ui/index.html)

[PlayGround](http://localhost:8080/playground)

### Version
- Spring boot ver 2.5.4 -> 2.7.7 
- Gradle ver 6.9.1 -> 7.4.1
- JAVA ver 11.0.15.1
- Kotlin ver 1.5.21 -> 1.6.21
- JPA ver 1.5.21 -> 1.6.21
- QueryDSL ver 1.0.10 -> 5.0.0

## 주요 구현 패러다임

> Rich Domain Model

Anemic Domain Model에서는 핵심 비즈니스 로직이 서비스 계층에 집중되어 있고

도메인 엔터티에는 Getter / Setter 역할만 하는, DB와 매핑하기 위한 역할로서의 VO만 남겨두었는데

이 경우 핵심 비즈니스 로직이 여러 서비스에 분산되어 있거나 중복되어 있기 때문에 유지보수가 어려워 진다.

이에 반해 Rich Domain Model은 핵심 비즈니스 로직을 Domain 엔터티에 둠으로서 데이터가 있는 곳에서 데이터 처리를 수행할 수 있고,

응집도가 높아지면서 결합도는 낮아져 유지보수가 용이해 진다.

> CQRS

Command Query Responsibility Segregation의 약자로 명령과 조회의 책임을 분리하는 패러다임이다.

데이터를 변경하는, CUD를 담당하는 Command와 데이터 조회만 담당하는 Query로 서비스 계층을 분리한다.

CQRS는 Rich Domain Model과 결합되어 Command의 관점에서 데이터 변경을 도메인 엔터티에서 담당하게 된다.

관심사가 분리되기 때문에 시스템의 유지보수를 더 쉽고, 유연하게 관리할 수 있다.

## API 호출방식

> QueryDsl

Java 언어를 위한 SQL 쿼리 생성 라이브러리.

데이터베이스와 상호작용할 때, 문자열 기반의 SQL 쿼리보다 더욱 안전하고 유연한 방식으로 쿼리를 작성할 수 있게 해준다.

코드 기반으로 쿼리를 작성하며 컴파일 시점에서 오타나 잘못된 접근을 방지한다.

또한 QueryDSL은 다양한 데이터베이스에 대한 지원을 제공하고, 여러 종류의 프로젝트에서 유연하게 활용할 수 있습니다.

JPA나 Hibernate와 같은 ORM(Object-Relational Mapping) 프레임워크와 함께 사용되며, 객체 지향적인 방식으로 쿼리를 작성할 수 있도록 도와준다.

> GraphQl

GraphQL은 페이스북에서 개발한 쿼리 언어로서, 데이터를 관리하는 유연하고 효율적인 방법을 제공하는 쿼리 언어와 런타임 환경이다.

RESTful API와는 다르게 클라이언트가 필요한 데이터를 직접 명시할 수 있고, 이로써 필요한 정보만 정확히 가져올 수 있으며, 하나의 엔드포인트 만을 가지기 때문에 여러 요청을 하나로 통합할 수 있다.

> 한계점

GraphQl은 @SchemaMapping 방식과 Resolver 방식으로 나뉘는데, 멀티모듈 환경의 코틀린 스프링부트에서 SchemaMapping은 정상 작동하지 않음을 확인(23.11.19), 따라서 Resolver 방식으로 개발을 진행했다.

SchemaMapping은 기본적으로 SpringBoot 2.7.x 이상 버전부터 지원하고 Resolver가 내장되어 있으나
Mutation Query는 잘 작동하지만 Query 요청 시 return 값이 blank가 전달되는 것을 확인했다.

## 디렉토리 구조

```bash
├── api
│   ├── config
│   ├── controller
│   │   ├── restapi * QueryDsl과 ResponseEntity를 사용하는 기존의 RESTAPI 호출방식
│   │   ├── graphql * GraphQl을 사용하는 호출방식
│   ├── dto
│   ├── filter
│   ├── intercepter
│   ├── service
│   │   ├── command
│   │   ├── query
│   │   ├── firebase
├── *.graphqls
├── buildSrc
├── common
│   ├── domain
│   │   ├── _common
│   │   ├── config
│   │   ├── converter
│   │   ├── exception
│   │   ├── model
│   │   ├── repository
│───├── lib
│   │   ├── config
│   │   ├── error
│   │   ├── security
│   │   │   ├── jwt
│───│───├── utils
```
