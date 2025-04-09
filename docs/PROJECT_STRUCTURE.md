# 프로젝트 구조

## 디렉토리 구조

```
TcpServer/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── tools/
│   │   │           ├── Common/
│   │   │           │   ├── db/
│   │   │           │   │   ├── entity/
│   │   │           │   │   │   ├── Account.java        # 계정 엔티티
│   │   │           │   │   │   └── Game.java          # 게임 엔티티
│   │   │           │   │   └── HibernateUtil.java     # Hibernate 설정
│   │   │           │   ├── exception/
│   │   │           │   │   └── PacketException.java   # 패킷 예외 처리
│   │   │           │   ├── packet/
│   │   │           │   │   ├── enums/
│   │   │           │   │   │   ├── PacketHeader.java  # 패킷 헤더 정의
│   │   │           │   │   │   ├── PacketOpcode.java  # 패킷 Opcode 정의
│   │   │           │   │   │   └── ResponseCode.java  # 응답 코드 정의
│   │   │           │   │   ├── InPacket.java          # 수신 패킷 처리
│   │   │           │   │   └── OutPacket.java         # 송신 패킷 처리
│   │   │           │   └── utils/
│   │   │           │       └── HexUtil.java           # 16진수 변환 유틸리티
│   │   │           ├── CharacterServer/
│   │   │           │   ├── application/
│   │   │           │   │   ├── dto/
│   │   │           │   │   │   ├── CharacterInsertPacket.java  # 캐릭터 생성 패킷
│   │   │           │   │   │   ├── CharacterListPacket.java    # 캐릭터 목록 패킷
│   │   │           │   │   │   ├── CharacterPacket.java        # 캐릭터 기본 패킷
│   │   │           │   │   │   └── CharacterPingPacket.java    # PING 패킷
│   │   │           │   │   ├── port/
│   │   │           │   │   │   ├── in/
│   │   │           │   │   │   │   └── CharacterPort.java      # 캐릭터 포트 인터페이스
│   │   │           │   │   │   └── out/
│   │   │           │   │   │       └── CharacterRepositoryPort.java # 캐릭터 저장소 포트
│   │   │           │   │   └── service/
│   │   │           │   │       └── CharacterService.java        # 캐릭터 서비스 구현
│   │   │           │   ├── infrastructure/
│   │   │           │   │   ├── adapter/
│   │   │           │   │   │   ├── in/
│   │   │           │   │   │   │   └── CharacterServerHandler.java # 캐릭터 서버 핸들러
│   │   │           │   │   │   └── out/
│   │   │           │   │   │       └── CharacterRepositoryAdapter.java # 캐릭터 저장소 어댑터
│   │   │           │   └── CharacterServer.java                 # 캐릭터 서버 진입점
│   │   │           ├── GameServer/
│   │   │           │   ├── application/
│   │   │           │   │   ├── dto/
│   │   │           │   │   │   ├── GameMovePacket.java        # 플레이어 이동 패킷
│   │   │           │   │   │   ├── GameExpPacket.java         # 경험치 획득 패킷
│   │   │           │   │   │   ├── GameLevelPacket.java       # 레벨업 패킷
│   │   │           │   │   │   └── GamePingPacket.java        # PING 패킷
│   │   │           │   │   ├── port/
│   │   │           │   │   │   ├── in/
│   │   │           │   │   │   │   └── GamePort.java          # 게임 포트 인터페이스
│   │   │           │   │   │   └── out/
│   │   │           │   │   │       └── GameRepositoryPort.java # 게임 저장소 포트
│   │   │           │   │   └── service/
│   │   │           │   │       └── GameService.java           # 게임 서비스 구현
│   │   │           │   ├── infrastructure/
│   │   │           │   │   ├── adapter/
│   │   │           │   │   │   ├── in/
│   │   │           │   │   │   │   └── GameServerHandler.java # 게임 서버 핸들러
│   │   │           │   │   │   └── out/
│   │   │           │   │   │       └── GameRepositoryAdapter.java # 게임 저장소 어댑터
│   │   │           │   └── GameServer.java                     # 게임 서버 진입점
│   │   │           ├── LoginServer/
│   │   │           │   ├── application/
│   │   │           │   │   ├── dto/
│   │   │           │   │   │   ├── LoginPacket.java           # 로그인 패킷
│   │   │           │   │   │   └── LoginPingPacket.java       # PING 패킷
│   │   │           │   │   ├── port/
│   │   │           │   │   │   ├── in/
│   │   │           │   │   │   │   └── LoginPort.java         # 로그인 포트 인터페이스
│   │   │           │   │   │   └── out/
│   │   │           │   │   │       └── LoginRepositoryPort.java # 로그인 저장소 포트
│   │   │           │   │   └── service/
│   │   │           │   │       └── LoginService.java          # 로그인 서비스 구현
│   │   │           │   ├── infrastructure/
│   │   │           │   │   ├── adapter/
│   │   │           │   │   │   ├── in/
│   │   │           │   │   │   │   └── LoginServerHandler.java # 로그인 서버 핸들러
│   │   │           │   │   │   └── out/
│   │   │           │   │   │       └── LoginRepositoryAdapter.java # 로그인 저장소 어댑터
│   │   │           │   └── LoginServer.java                    # 로그인 서버 진입점
│   │   │           └── Main.java                               # 애플리케이션 진입점
│   │   └── resources/
│   │       └── application.yml                                # 애플리케이션 설정
│   └── test/
│       └── java/
│           └── com/
│               └── tools/
│                   └── Common/
│                       └── utils/
│                           └── HexUtilTest.java               # 16진수 변환 테스트
├── docs/
│   ├── PACKET_STRUCTURE.md                                   # 패킷 구조 문서
│   └── PROJECT_STRUCTURE.md                                  # 프로젝트 구조 문서
└── README.md                                                 # 프로젝트 설명 문서
```

## 주요 컴포넌트 설명

### Common 패키지
- **db**: 데이터베이스 관련
  - `HibernateUtil`: Hibernate 설정 및 세션 관리
  - `entity`: 데이터베이스 엔티티 클래스
- **exception**: 예외 처리
  - `PacketException`: 패킷 관련 예외 처리
- **packet**: 패킷 처리
  - `enums`: 패킷 관련 열거형 정의
  - `InPacket`: 수신 패킷 처리
  - `OutPacket`: 송신 패킷 처리
- **utils**: 유틸리티
  - `HexUtil`: 16진수 변환 및 패킷 데이터 처리

### CharacterServer 패키지
- **application**: 비즈니스 로직
  - `dto`: 데이터 전송 객체
  - `port`: 포트 인터페이스
  - `service`: 서비스 구현체
- **infrastructure**: 인프라스트럭처
  - `adapter`: 어댑터 구현
- **CharacterServer**: 서버 진입점
  - Netty 서버 설정 및 초기화
  - 포트 14102 사용

### GameServer 패키지
- **application**: 비즈니스 로직
  - `dto`: 데이터 전송 객체
  - `port`: 포트 인터페이스
  - `service`: 서비스 구현체
- **infrastructure**: 인프라스트럭처
  - `adapter`: 어댑터 구현
- **GameServer**: 서버 진입점
  - Netty UDP 서버 설정 및 초기화
  - 포트 14103 사용
  - 브로드캐스트 기능 구현

### LoginServer 패키지
- **application**: 비즈니스 로직
  - `dto`: 데이터 전송 객체
  - `port`: 포트 인터페이스
  - `service`: 서비스 구현체
- **infrastructure**: 인프라스트럭처
  - `adapter`: 어댑터 구현
- **LoginServer**: 서버 진입점
  - Netty 서버 설정 및 초기화
  - 포트 14101 사용

## 의존성 관리
- **Spring Boot 3.2.3**: 웹 애플리케이션 프레임워크
- **Netty 4.1.107**: 비동기 이벤트 기반 네트워크 프레임워크
- **Hibernate 6.3.1**: JPA 구현체
- **HikariCP 6.2.1**: 고성능 JDBC 커넥션 풀
- **JWT 0.11.5**: JSON Web Token 인증
- **Lombok 1.18.30**: 코드 간소화
- **JUnit 5.10.2**: 단위 테스트

## 포트 정보
- **14101**: 로그인 서버 (TCP)
- **14102**: 캐릭터 서버 (TCP)
- **14103**: 게임 서버 (UDP)
- **14104**: 메시지 서버 (TCP) / 예정