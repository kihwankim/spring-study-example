# SAGA 패턴

## 1. 비즈니스 로직

### 상품 주문

1. 상품 목록 정보 입력
2. Order Service Transaction 설정
    1. 상품 Order entity 생성
    2. Item 정보 수정(상품 잔고량 수정)
    3. pay service kafka로 전송
    4. 고객에게 상품 구제 완료 alert 창 전송
3. pay system에 결제 요청(Kafka)
    1. 결제 데이터 저장(결제 API 호출 대용)
    2. 성공/실패 전송
4. Order Service
    1. 고객에게 status값에 따라 성공 실패 여부 보여주기

## Message

### 공통 데이터

- Id
- key: hash 값 -> RDB의 key값 이용

### 하위 type

- Command
    - 정의: 변경, 추가. 삭제(CUD) 작업을 요청 하는 것
    - convention: ~~Command
- Event
    - 정의: CUD 작업에 대해서 결과를 반환 하는 것
    - convention: ~~Event

### Payload 컨벤션

- ~~CommandPayload
- ~~EventPayload

### Payload 필수 데이터

- Id: aggreate의 id값
- hashKey: 중복 수신 방지

### Package 위치 설계

- Message, Payload interface: common module(공통)
- Event, Command, CommandPayload, EventPayload: domain module에 위치
    - 이유: 주로 변경이 같이 이뤄짐

## Aggregate/DomainModel

- common module model


## EventListner

### 성공
- 새로운 Event 생성
  - hashkey 생성
  - 새로운 Id 생성
    - 기존 Command의 ID를 통해서 notify event 전송

### 실패
- Exception 전용 Event table에 적제
  - 새로운 haskey 생성
  - 기존 Id와 새로운 haskey로 notify event 전송
