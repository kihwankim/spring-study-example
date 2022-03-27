
# Index

1. Redis 캐시로 사용하기
2. Redis에서 데이터를 영구적으로 저장방법(RDB, AOP)
3. Redis 아키텍쳐
4. Redis 운영과 장애 포인트


# 1. Redis 캐시로 사용하기

## 1.1 Redis 사용처
- 캐싱 솔루션
- 캐시 : 사용자의 데이터를 더 빠르게 조회할 수 있는 데이터 저장소

## 1.2 Redis 특징
- 단순한 Key-value 구조
- in memory 데이터 저장소 -> 속도가 빠름
- 빠른 성능을 가짐

## 1.3 캐싱 전략
### 읽기 전략(Look-Aside Lazying Loading)
1. 구조
![lookaside](img/lookaside.png)
- 처음에 application이 redis부터 검색
- redis에 해당 데이터가 없을 경우(miss) DB로 접근해서 데이터 fetch
- redis에 다시 저장 후 반

2. 장점
- redis가 다운되더라도 DB로 부터 데이터를 가져올 수 있기 때문에 장애 요소를 없앨 수 있음

3. 단점
- 모든 connection이 DB로 붙을 수 있기때문에 성능 문제가 발생할 수 있음
- 처음에 캐시 미스 많이 발생할 수 있음

4. 해결법
- cache warming: 미리 캐시에 데이터를 밀어 넣어주는 작업을 진행

### 쓰기 전략(Write Around)
1. 구조
![wirte-around](img/write-around.png)
- DB에 모든 데이터 저장
- 캐시 미스가 발생 -> DB 에서 데이터를 가져와서 캐시에 저장

2. 단점
- 캐시랑 DB랑 데이터가 다른 이슈 발생할 수 있음


### 쓰기 전략(Write-Through)

1. 구조
![wirte-through](img/write-through.png)
- write할 때 무조건 캐시에 저장 후 DB에 저장


3. 장점
- 캐시는 무조건 최신정보를 가짐

4. 단점
- 저장 단계가 2단계이기 때문에 성능 저하 발생
- 재사용하지 않는 데이터도 캐시에 저장하므로 성능 저하 및 메모리 leak 발생 가능성 존재 -> 해결 법 expire time 설정이 필요

# 2. Redis 영구 저장(AOF, RDB)

## 2.1 Redis Persistence

- Redis는 in-memory 데이터 스토어
- 서버 재시작 시 모든 데이터 유실
- 복제 기능을 사용해도 사람의 실수로 발생 시 데이터 복원 불가
- Redis를 캐시 이외의 용도로 사용한다면 적절한 데이터 백업이 필요

## 2.2 Redis Persistence Option

### AOF
- Append Only File의 약자로서 데이터 변경하는 command가 들어오면 command를 모두 저장
- 데이터가 많이 커지게됨 -> 주기적으로 압축이 필요함
- redis.conf파일에서 auto-aof-rewrite-percentage옵션(크기 기준)을 기준으로 자동으로 AOF로 저장된 데이터를 압축할 수 있음
- BGREWRITEAOF command를 이용해서 CLI창에서 수동으로 AOF 파일 재작성 가능

### RDB
- 데이터를 snapshot을 사용하듯이 redis상태를 저장함
- redis.conf 파일에서 SAVE 옵션을 사용해서 자동으로 저잘하게할 수 있음 -> 시간단위로 저장 가능
- BGSAVE command를 이용해서 cli 창에서 수동으로 RDB 파일 저장
  - save command 절대 사용 하면 안됨

### 선택
- 캐시로만 redis 사용: 사용할 필요없음
- 어느정도 데이터 손실 감소 가능: RDB만 사용 -> redis.conf에서 save옵션을 잘 사용해야함
- 장애 시점까지 모든 데이터를 복구해야하는 경우 : AOF 사용하기 -> everysec를 사용해서 1초단위로 저장해서 성능 개선을 해야함 -> 최대 1초 정도의 분실 될 수 있음

# 3. Redis 아키 텍쳐

## 3.1 Replication 구성
### 정의
- 단순이 복제된 redis만 존재
- 비동기식으로 복제 -> 복제본된 데이터를 전달하고 기다리거나 확인하지 않음
- HA 기능 없음
  - 장애 상황시 수동으로 복구 해야함 
  - replicaof no one -> 복제를 먼저 끊어야함
  - 애플리케이션에서 연결 정보 변경이 필요

## 3.2 Sentinel 구성

### 정의
- sentinel node까지 추가 및 sentinel node가 redis를 감시
- 마스터가 비정상 상태일 경우 자동으로 fail over를 시켜줌
- applciation/redis에서 연결 정보 변경 필요 없기 때문에 편리함
- sentinel node는 항상 3대 이상 홀수로 필요
  - 과반수 이상 failover를 해야한다고 동의해야지 failover를 진행하기 때문

## 3.3 Cluster 구성

### 정의
- 키를 여러 노드에 자동으로 분할해서 저장하는 샤딩 기능 제공
- 모든 노드가 서로를 감시하며, 마스터가 비정상 상태일 때 자동으로 failover를 시켜줌
- 최소 3대의 mater node가 필요

# 4. Redis 운영 장애 요소

## 4.1 상요사면 안되는 command
- redis 는 single thread이기 때문에 수행하는데 오래결리는 command입력시 모든 요청이 block되는 이슈 발생

### Command
- keys * (모든 키 조회) -> scan 0 으로 대체해야함: 재귀적으로 key들을 하나씩 호출
- Hash나 sorted set 자료구조 -> 내부에 데이터가 많아질수록 성능이 저하가되는 이슈 발생
  - 최대 100만개 이상 넘어가지 않도록 조절해야함 -> 키를 나누는 방식을 고려해야함
  - hgetall -> hsacn
  - del -> unlink: 많은데이터가 존재할 경우 key를 지울동알 모든 것이 block -> unlink는 background에서 지워주기 때문에 성능 개선

## 4.2 기본 설정값으로 성능 개선

### STOP-WRITE-ON-BGSAVE-ERROR = NO
- yes(default)
- yes일 경우 RDB 파일 저장실패시 redis로 들어오는 모든 write을 차단해주는 것

### MAXMEMORY-POLICY = ALLKEYS-LRU
- redis 캐시를 사용할때 expire time설정을 권장하지만, 이럼에도 불구하고 꽉찬 경우가 있음
- memory에 모든 데이터가 꽉찬경우 MAXMEMORY-POLICY 정책에 의해서 key 관리
  - noeviction(defulat): 삭제 하지 않음 -> 새로운키 저장 거부 -> 장애가 될 수 있음
  - volatile-lru: lru 정책을 사용하지만 expire time이 설정된 key만 삭제하는 뜻으로 모든 key가 expire time이 없을 경우 장애가 될 수 있음
  - allkeys-lru: 모든 key에 대해서 lru 가능

### Cache Stampede 이슈
1. 정의
- TTL이 너무작을 경우 모든 Application 서버들이 DB에 동시에 데이터를 읽어서 동일한 데이터를 redis에 저장하는 이슈 발생
- Duplicated read/duplicated write 이슈 발셍

2. 해결 방법
- TTL 시간을 넉넉하게 늘리는 방식을 문제를 해결

### MaxMemory 값 설정
- persistence / 복제 사용시 MaxMemory 설정 주의
- RDB나 AOF를 사용할 때 fork로 자식 프로세스를 생성해서 backgrond에서 데이터를 파일로 저장하게 됩니다
- 그리고 원래 process는 redis 요청 데이터를 처리함
- 이유: copy on write 를 사용해서 memory를 복사해서 사용함
  - COW: 처음에 두 프로세스가 동일한 메모리 영역을 공유해서 사용합니다. 그리고 redis의 main process가 수정을 할경우 새로운 메모리 영역에 카피를 해서 데이터를 저장하고 참조하는 방식을 의미 합니다.
  - 서버의 메모리 사용률이 2배로 증가하는 이슈가 발생함
  - 복제를 처음 시도하거나, 연결이 끊겼다가 재시도를 할 경우 새로 RDB파일을 저장하는 과정을 거치게 되어서 메모리 사용률 2배까지 증가할 수 있음
- max memory를 실제 memory의 1/2로 설정이 필요

### used_memory_rss 값을 보기
- used_memeory: 논리적으로 redis 가사용하는 메모리
- used_memroy_rss: OS가 redis에 할당하기 위해 사용한 물리적 메모리 양을 보여줌
- used_memory_rss와 used_memory와의 차이가 클 경우 fragmentation이 크다고 말을 합니다
- 삭제되는 키가 많으면 fragmentataion 증가
  - 특정 시점에 피크를 찍고 다시 삭제되는 경우
  - TTL로 인해 삭제가 과도하게 많이 발생하는 경우
- CONFIG SET activedefrag yes 를 잠시 켜두어서 fragmentation을 해결하는게 좋음 -> 공식문서에서는 항상켜두지 말고 잠시 켜뒀다가 끄는게 좋다고 나와있음