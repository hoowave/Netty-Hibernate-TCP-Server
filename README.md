# Netty & Hibernate TCP Server

패킷 기반의 고성능 TCP 서버입니다.<br>
Netty의 비동기 이벤트 처리와 Hibernate의 데이터 영속성을 결합하여, 헥사고날 아키텍처 패턴을 적용한 확장 가능한 서버 아키텍처를 구현중입니다.

## 주요 특징
- **유연한 통신 프로토콜**: 요청 패킷에 따라 패킷 기반 또는 JSON 기반 응답 타입 지원
- **비동기 이벤트 처리**: Netty의 이벤트 루프를 활용한 고성능 네트워크 처리
- **도메인 중심 설계**: 헥사고날 아키텍처를 통한 비즈니스 로직의 독립성 확보
- **데이터 영속성**: Hibernate와 HikariCP를 활용한 효율적인 데이터베이스 연동
- **보안**: JWT 기반의 사용자 인증 시스템
- **실시간 통신**: UDP 기반의 게임 서버로 다중 클라이언트 동시 처리

## 포트 정보
- 14101 : 로그인 서버(TCP)
- 14102 : 캐릭터 서버(TCP)
- 14103 : 게임 서버(UDP)
- 14104 : 메시지 서버(TCP) / 예정

## 구현 현황

### 공통
- [O] Hibernate 연동
- [O] HikariCP 연동
- [O] 패킷 구조 정의
- [O] JWT 인증 시스템
- [O] 로그인 세션 및 영속성 관리

### Login Server
- [O] 프로토콜 정의
- [O] JWT 패킷 암호화, 복호화
- [O] 인증
- [O] 데이터베이스 연동

### Character Server
- [O] 캐릭터 조회
- [O] 캐릭터 생성
- [O] 캐릭터 삭제
- [O] 캐릭터 선택 
- [O] 데이터베이스 연동

### Game Server
- [O] UDP 서버 구현
- [O] Broadcast 구조 설계
- [O] 패킷 구조 정의
- [-] Player 이동 처리 (TBD)
- [-] Player 경험치 획득 처리 (TBD)
- [-] 실시간 위치 동기화 (TBD)

### 개발 예정 기능
- [-] 패킷 암호화
- [-] 모니터링 시스템
- [-] 성능 최적화
- [-] 로그인 세션 관리 개선
- [-] 사용자 권한 관리 시스템
- [-] 게임 서버 부하 분산
- [-] 게임 서버 상태 모니터링

## Flow

### 1. JWT 토큰 발급
- 사용자가 Toolbox 백엔드 서버(`https://dev.hoowave.org`)에 로그인
- 서버에서 JWT 토큰 발급
- 토큰에는 사용자 ID, 권한 등의 정보가 포함됨

### 2. JWT 토큰 전송
- 클라이언트는 발급받은 JWT 토큰을 패킷 구조에 맞게 인코딩
- 패킷 구조:
  ```
  Opcode: 02 (LOGIN)
  Header: 01 (LOGIN_REQUEST)
  ResponseType: 01(Packet) / 02(Json)
  Body: JWT 토큰(Hex Encode)
  ```
- 인코딩된 토큰을 Login Server(포트 14001)로 전송

### 3. JWT 토큰 인증 및 세션 발급
- Login Server는 수신된 JWT 토큰을 검증
- 토큰이 유효한 경우:
  - 사용자 ID 추출
  - 데이터베이스에서 사용자 정보 조회
  - UUID 기반의 게임 세션 생성
  - `ClientSession`에 세션 정보 저장
- 응답 패킷:
  ```
  Opcode: F1 (SUCCESS)
  Header: 02 (LOGIN_RESPONSE)
  ResponseType: 0x3031 (PACKET)
  Body: UUID 세션 정보
  ```

### 4. 캐릭터 관리
- 클라이언트는 발급받은 UUID 세션을 사용하여 Character Server(포트 14002)와 통신
- 캐릭터 조회:
  ```
  Opcode: 03 (CHARACTER)
  Header: 31 (CHARACTER_LIST_REQUEST)
  ResponseType: 01(Packet) / 02(Json)
  Body: UUID 세션(Hex Encode)
  ```
- 캐릭터 생성:
  ```
  Opcode: 03 (CHARACTER)
  Header: 35 (CHARACTER_ADD_REQUEST)
  ResponseType: 01(Packet) / 02(Json)
  Body: UUID 세션 + "/" + 닉네임(Hex Encode)
  ```
- 캐릭터 선택:
  ```
  Opcode: 03 (CHARACTER)
  Header: 33 (CHARACTER_INFO_REQUEST)
  ResponseType: 01(Packet) / 02(Json)
  Body: UUID 세션 + "/" + 캐릭터 ID(Hex Encode)
  ```
- 캐릭터 삭제:
  ```
  Opcode: 03 (CHARACTER)
  Header: 37 (CHARACTER_REMOVE_REQUEST)
  ResponseType: 01(Packet) / 02(Json)
  Body: UUID 세션 + "/" + 캐릭터 ID(Hex Encode)
  ```

### 5. 게임 서버 연결
- 클라이언트는 발급받은 UUID 세션을 사용하여 Game Server(포트 14003)와 UDP 통신
- 게임 서버는 UDP를 통해 실시간으로 다중 클라이언트와 통신
- 주요 기능 (TBD):
  - Player 이동 처리
  - Player 경험치 획득
  - 실시간 위치 동기화
  - 다중 클라이언트 브로드캐스트

## 패킷 구조
자세한 패킷 구조는 [PACKET_STRUCTURE.md](docs/PACKET_STRUCTURE.md) 문서를 참고해주세요.

## 프로젝트 구조
자세한 프로젝트 구조는 [PROJECT_STRUCTURE.md](docs/PROJECT_STRUCTURE.md) 문서를 참고해주세요.

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
6. **브로드캐스트** (게임 서버): UDP를 통한 다중 클라이언트 동시 전송

## 기술 스택

- **Java 17**
- **Netty**: 비동기 이벤트 기반 네트워크 프레임워크
- **Hibernate**: JPA 구현체
- **HikariCP**: 고성능 JDBC 커넥션 풀
- **JWT**: 사용자 인증 및 권한 관리
- **Gradle**: 빌드 도구
- **UDP**: 실시간 게임 통신

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
- [UDP 통신 가이드](https://docs.oracle.com/javase/tutorial/networking/datagrams/)

## 라이선스

이 프로젝트는 MIT 라이선스를 따릅니다.
