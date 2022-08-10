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

