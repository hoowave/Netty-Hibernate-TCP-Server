# 패킷 구조

## 기본 구조

```
XX XX XX XX XX XX XX XX XX ..
index0 : Packet Opcode (2 bytes)
index1 : Padding (2 bytes)
index2 : Separator (1 byte, 0x20)
index3 : Packet Header (2 bytes)
index4 : Padding (2 bytes)
index5 : Separator (1 byte, 0x20)
index6 : ResponseType (2 bytes)
index7 : Separator (1 byte, 0x20)
index8 ~ : Body
```

## 패킷 타입 정의

### Opcode (요청 타입)
- `0x4631` : SUCCESS
- `0x4632` : FAIL
- `0x3031` : PING
- `0x3032` : LOGIN
- `0x3033` : CHARACTER

### Header (액션 타입)
- `0x3131` : PING_REQUEST
- `0x3132` : PING_RESPONSE
- `0x3231` : LOGIN_REQUEST
- `0x3232` : LOGIN_RESPONSE
- `0x3331` : CHARACTER_LIST_REQUEST
- `0x3332` : CHARACTER_LIST_RESPONSE
- `0x3333` : CHARACTER_INFO_REQUEST
- `0x3334` : CHARACTER_INFO_RESPONSE
- `0x3335` : CHARACTER_ADD_REQUEST
- `0x3336` : CHARACTER_ADD_RESPONSE
- `0x3337` : CHARACTER_REMOVE_REQUEST
- `0x3338` : CHARACTER_REMOVE_RESPONSE

### ResponseType (응답 타입)
- `0x3031` : PACKET (패킷 기반 응답)
- `0x3032` : JSON (JSON 기반 응답)

## 패킷 예시

### PING 요청
```
Opcode: 0x3031 (PING)
Header: 0x3131 (PING_REQUEST)
ResponseType: 0x3031 (PACKET)
Body: 없음
```

### PING 응답
```
Opcode: 0x4631 (SUCCESS)
Header: 0x3132 (PING_RESPONSE)
ResponseType: 0x3031 (PACKET)
Body: 없음
```

### 로그인 요청
```
Opcode: 0x3032 (LOGIN)
Header: 0x3231 (LOGIN_REQUEST)
ResponseType: 0x3031 (PACKET)
Body: JWT 토큰 (16진수로 인코딩)
```

### 로그인 응답
```
Opcode: 0x4631 (SUCCESS)
Header: 0x3232 (LOGIN_RESPONSE)
ResponseType: 0x3031 (PACKET)
Body: UUID 세션 정보 (16진수로 인코딩)
```

### 캐릭터 조회 요청
```
Opcode: 0x3033 (CHARACTER)
Header: 0x3331 (CHARACTER_LIST_REQUEST)
ResponseType: 0x3031 (PACKET)
Body: UUID 세션 (16진수로 인코딩)
```

### 캐릭터 조회 응답
```
Opcode: 0x4631 (SUCCESS)
Header: 0x3332 (CHARACTER_LIST_RESPONSE)
ResponseType: 0x3031 (PACKET)
Body: 캐릭터 목록 정보
```

### 캐릭터 생성 요청
```
Opcode: 0x3033 (CHARACTER)
Header: 0x3335 (CHARACTER_ADD_REQUEST)
ResponseType: 0x3031 (PACKET)
Body: UUID 세션 + "/" + 닉네임 (16진수로 인코딩)
```

### 캐릭터 생성 응답
```
Opcode: 0x4631 (SUCCESS)
Header: 0x3336 (CHARACTER_ADD_RESPONSE)
ResponseType: 0x3031 (PACKET)
Body: 생성된 캐릭터 정보
```

## 패킷 처리 흐름

1. **수신**: Netty 채널을 통한 패킷 수신
2. **디코딩**: 
   - Opcode, Header, ResponseType 추출
   - Body를 16진수에서 문자열로 디코딩
3. **처리**: 도메인 로직을 통한 비즈니스 처리
4. **응답**: 
   - ResponseType에 따라 PACKET 또는 JSON 형식으로 응답
   - PACKET 형식: 바이너리 데이터로 응답
   - JSON 형식: JSON 문자열로 응답 