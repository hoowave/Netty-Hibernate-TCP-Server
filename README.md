# Netty & Hibernate TCP Server

패킷 기반의 고성능 TCP 서버입니다.<br>
Netty의 비동기 이벤트 처리와 Hibernate의 데이터 영속성을 결합하여, 헥사고날 아키텍처 패턴을 적용한 확장 가능한 서버 아키텍처를 구현중입니다.

## 주요 특징
- **유연한 통신 프로토콜**: 요청 패킷에 따라 패킷 기반 또는 JSON 기반 응답 타입 지원
- **비동기 이벤트 처리**: Netty의 이벤트 루프를 활용한 고성능 네트워크 처리
- **도메인 중심 설계**: 헥사고날 아키텍처를 통한 비즈니스 로직의 독립성 확보
- **데이터 영속성**: Hibernate와 HikariCP를 활용한 효율적인 데이터베이스 연동
- **보안**: JWT 기반의 사용자 인증 시스템

## 구현 현황

### 공통
- [O] Hibernate 연동
- [O] HikariCP 연동
- [O] 패킷 구조 정의
- [O] JWT 인증 시스템
- [-] 로그인 세션 및 영속성 관리

### Login Server
- [O] 프로토콜 정의
- [O] JWT 패킷 암호화, 복호화
- [O] 인증
- [O] 데이터베이스 연동

### Character Server
- [-] 캐릭터 조회
- [-] 캐릭터 생성
- [-] 캐릭터 삭제

### Game Server
- [-] 설계 단계

## 개발 예정 기능
- [-] 패킷 암호화
- [-] 모니터링 시스템
- [-] 성능 최적화
- [-] 로그인 세션 관리 개선
- [-] 사용자 권한 관리 시스템

## 패킷 구조

```
XX XX XX XX ..
index0 : Packet Opcode (2 bytes)
index1 : Packet Header (2 bytes)
index2 : Packet ResponseType (2 bytes)
index3 : Separator (1 byte, 0x20)
index4 ~ : Body
```

### 패킷 타입 정의

#### Opcode (요청 타입)
- `0x4631` : SUCCESS
- `0x4632` : FAIL
- `0x3031` : PING
- `0x3032` : LOGIN
- `0x3033` : CHARACTER

#### Header (액션 타입)
- `0x3031` : PING_REQUEST
- `0x3032` : PING_RESPONSE
- `0x3031` : LOGIN_REQUEST
- `0x3032` : LOGIN_RESPONSE
- `0x3031` : CHARACTER_LIST_REQUEST
- `0x3032` : CHARACTER_LIST_RESPONSE
- `0x3033` : CHARACTER_ADD_REQUEST
- `0x3034` : CHARACTER_ADD_RESPONSE
- `0x3035` : CHARACTER_REMOVE_REQUEST
- `0x3036` : CHARACTER_REMOVE_RESPONSE

#### ResponseType (응답 타입)
- `0x3031` : PACKET (패킷 기반 응답)
- `0x3032` : JSON (JSON 기반 응답)

## 프로젝트 구조

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── tools/
│   │           ├── LoginServer/     # 로그인 서버 구현
│   │           │   ├── application/ # 애플리케이션 서비스
│   │           │   │   ├── dto/     # 데이터 전송 객체
│   │           │   │   ├── port/    # 포트 인터페이스
│   │           │   │   └── service/ # 서비스 구현체
│   │           │   ├── core/        # 도메인 로직
│   │           │   ├── infrastructure/ # 인프라스트럭처
│   │           │   │   ├── adapter/ # 어댑터 구현
│   │           │   │   └── in/      # 인바운드 어댑터
│   │           │   └── LoginServer.java # 서버 진입점
│   │           ├── CharacterServer/ # 캐릭터 서버 구현
│   │           │   ├── application/ # 애플리케이션 서비스
│   │           │   │   ├── dto/     # 데이터 전송 객체
│   │           │   │   ├── port/    # 포트 인터페이스
│   │           │   │   └── service/ # 서비스 구현체
│   │           │   ├── infrastructure/ # 인프라스트럭처
│   │           │   │   └── adapter/ # 어댑터 구현
│   │           │   └── CharacterServer.java # 서버 진입점
│   │           ├── Common/          # 공통 모듈
│   │           │   ├── packet/      # 패킷 관련 공통 코드
│   │           │   │   ├── enums/   # 패킷 관련 열거형
│   │           │   │   ├── InPacket.java # 패킷 처리
│   │           │   │   └── OutPacket.java # 패킷 처리
│   │           │   ├── utils/       # 유틸리티
│   │           │   ├── exception/   # 예외 처리
│   │           │   ├── db/          # 데이터베이스 관련
│   │           │   │   ├── entity/  # 엔티티 클래스
│   │           │   │   │   ├── Account.java # 계정 엔티티
│   │           │   │   │   ├── Game.java # 게임 엔티티
│   │           │   │   │   └── enums/ # 엔티티 관련 열거형
│   │           │   │   └── HibernateUtil.java # DB 설정
│   │           │   ├── session/     # 세션 관리
│   │           │   │   └── ClientSession.java # 클라이언트 세션
│   │           │   └── dao/         # 데이터 접근 객체
│   │           └── Main.java        # 애플리케이션 진입점
│   └── resources/                   # 설정 파일
└── test/                           # 테스트 코드
```

## 아키텍처 설명

### 헥사고날 아키텍처

- **도메인 계층 (core)**: 핵심 비즈니스 로직과 규칙을 포함
- **애플리케이션 계층 (application)**: 유스케이스 구현 및 조정
- **인프라스트럭처 계층 (infrastructure)**: 외부 시스템과의 통신 담당
- **공통 모듈 (Common)**: 모든 서버에서 공통으로 사용되는 기능

### 패킷 처리 흐름

1. **수신**: Netty 채널을 통한 패킷 수신
2. **디코딩**: 커스텀 프로토콜에 따른 패킷 디코딩
3. **처리**: 도메인 로직을 통한 비즈니스 처리
4. **영속화**: Hibernate와 HikariCP를 통한 데이터 저장
5. **응답**: 처리 결과를 클라이언트에게 전송 (ResponseType에 따라 PACKET 또는 JSON 형식)

## 기술 스택

- **Java 17**
- **Netty**: 비동기 이벤트 기반 네트워크 프레임워크
- **Hibernate**: JPA 구현체
- **HikariCP**: 고성능 JDBC 커넥션 풀
- **JWT**: 사용자 인증 및 권한 관리
- **Gradle**: 빌드 도구

## 시작하기

### 개발 환경 설정

1. JDK 17 설치
2. Gradle 8.0 이상 설치
3. MariaDB 10.0 이상 설치 및 설정

### 데이터베이스 설정
`src/main/java/com/tools/Common/db/HibernateUtil.java` 파일에서 데이터베이스 연결 정보 설정

### 빌드 및 실행

```bash
# 프로젝트 빌드
./gradlew build

# 서버 실행
./gradlew bootRun
```

## 참고 자료

- [Netty 공식 문서](https://netty.io/wiki/user-guide-for-4.x.html)
- [Hibernate 공식 문서](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html)
- [HikariCP 공식 문서](https://github.com/brettwooldridge/HikariCP)
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)

## 라이선스

이 프로젝트는 MIT 라이선스를 따릅니다.
