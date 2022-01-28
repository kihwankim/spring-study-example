## 1. 정의
- key-value 형태로 데이터를 관리하는 오픈소스
- Redis는 빠른 속도와 간편한 사용법을제공 -> 캐시, 인증토큰, 섹션 관리 용도로 사용

## 2. 특징
- in-memory data structure store
  - 메모리에 데이터 저장 및 조회 -> 빠른 속도
- key - value 형태로 데이터 저장
- single thread 기반
- data 만료시간 지정 가능
- 여러가지 value type 설정가능
  - string, set, hash, list, bit field, sorted set ... etc
    ![redis pic](./images/redis_data_structure.png)

## 3. 자료구조 제공에 따른 편의성
### 개발의 편의성과 난이도 조절 가능
- ex1: ranking 기능 구현
  - 일반 RDB를 score값을 저장하고 데이터를 읽어 올때 sort를 해야함
  - sort 성능을 고려해야 함
  - redis는 sorted set을 제공: 기본적으로 sorting된 저장 공간 제공

### 트랜젝션 문제해결
- single thread이기 때문에 race condition을 피해 데이터 정합성 보장
- 모든 자료구조는 atomic

## Redis의 자료 구조
### string

- key와 연결할 수 있는 가장 간단한 유형의 값
- key는 문자열
- value도 문자열
- 모든 종류의 문자열(이진 데이터 포함)을 젖아할 수 있음
- 응용 사례: 이미지 저장, HTML fragment를 캐시해서 사용 가능
- 최대 사이즈 : 512MB
```
> set hello world
OK
> get hello
"world"
```

- string 정수로 파싱 후 atomic 하게 증감하는 command
```
> set counter 100
OK
> incr counter # string to int parsing 후 1 증가 후 다시 String 변환
(integer) 101
> incr counter
(integer) 102
> incrby counter 50
(integer) 152
```

- 기존에 있는 key에 새로운 값으로 변경

```
> INCR mycounter
(integer) 1
> GETSET mycounter "0" # mycounter 의 value 1 -> 0으로 변경
"1"
redis> GET mycounter
"0"
```

- key가 이미 존재하거나, 존재하지 않을 때만 데이터 저장
```
> set mykey newval nx # mykey가 nil 일 경우에만 할당 가능
(nil)
> set mykey newval xx # mykey가 nil일경우 할당 불가
OK
```

### List
- double linked list 의 특징을 가지고 있음
  - head/tail 에 push 할때 동일한 시간 소요
- 특정 index의 값을 삭제, 조회 가능

```
> LPUSH mylist A   # now the list is "A"
> LPUSH mylist B   # now the list is "B","A"
> RPUSH mylist A   # now the list is "A","B","A" (RPUSH was used this time)
```
- pub - sub(생산자-소비자) 패턴으로 가장 유용하게 사용 중인 요소
- `RPUSHX`를 사용하면 key가 이미 있을 때만ㅇ 데이터를 저장합니다. 이걸 사용하면 이미 존재하는 pub - sub 구조에서만 data 를 추가할 수 있음
- list에서 데이터가 없을 경우 null/nil 값이 반환됩니다 이때 `BRPOP`, `BLPOP` key time 을하게 되면 time 초 동안 data가 publish 될때 까지 기다렸다가 처리 합니다
- 여러번 polling 하지 않아도 되기 때문에 polling 프로세스를 줄일 수 있습니다

### Hash

- filed-value 쌍을 가지는 hashmap으로 이해하면 편합니다
- key에 대한 filed의 개수에는 제한이 없음
- RDB와 가장 비슷한 구조 key pk로 보고, filed를 하나의 column name, value를 실제 값으로 인식하면 하나의 table로 사용할 수 있습니다.
- value에 대해서 개별로 조작할수 있는 atomic한 함수도 제공

```
> hincrby user-1 birthyear 10 # user-1 이라는 key의 hash(filed: birthday - value: 1977)에서 value 1977에서 10을 증가 시키는 뜻
(integer) 1987
> hincrby user-1 birthyear 10
(integer) 1997
```

- 기본 문법

```
# 저장
> HSET hkey name kkh # 1개저장
> HMSET hkey age 25 workplace nhn # 여러개 저장(multiple)

# 읽기
> HGET hkey name # name 읽기
> HMGET hkey name age workplace # 여러 filed 읽기
> HGETALL hkey # hkey 내부 값 전체 읽기 filed - value 순으로 출력

# 제거
> HDEL hkey workplace name # filed 삭제
> DEL hkey # key 자체를 삭제
```

### Set
- 정렬되지 않은 문자열의 모음 입니다
- 중복 불가
- 교집합, 합집합, 차집합 연산 가능
- 객체 간의 간계를 표현할 때 좋음
  - 현재 project중 음식(post)와 재료(tag)로 쌍을 이루는 정보가 있습니다
  - 여기에 음식 내부에 tag정보를 mapping 시켜주는 것을 작성하게되면 아래와 같이 사용할 수 있습니다
  - ID가 10인 food 내부에 tag정보가 americano shot coffeee 3개 들어가 있습니다.
  - 만약 아래 예시에서 5개의 food 가 동일하게 가지고 있는 tag를 확인하고 싶을 경우 `sinter`를 사용할 수 있습니다.
  - 반대로 tag별로 분류해 놓은 tag:ID:foods로 설정할 경우 tag 1, 2, 5, 77이 가지고 있는 food를 손싶게 조회할 수 있습니다

```
> sadd food:10:tags americano shot coffeee

> smembers food:10:tags
1. americano
2. shot
3. coffeee
```

```
> sadd tag:1:foods 1000 1
(integer) 1
> sadd tag:2:foods 1000 2
(integer) 1
> sadd tag:5:foods 1000 3
(integer) 1
> sadd tag:77:foods 1000 4
(integer) 1
> sinter tag:1:projects tag:2:projects tag:5:projects tag:77:projects
1) 1000
```


### sorted set

- set과 마찬가지로 중복이 없는 집합 이지만 각 value 내부에는 score라는 값이 존재합니다.
- score(floating point value)에 맞춰서 sort 된 구조를 가집니다
- 주로 랭킹을 매기는 application에서 사용을 많이 합니다
- index 접근시 sorted set이 더 빠르게 접근이 됩니다(index 접근시 list보다 sorted set을 사용 권장)

```
# 추가
> zadd birthyear (NX) 0 jason # NX를 붙이면 jason이 존재하면 추가하지 않음, XX인 경우 존재하면 update하라는 뜻입니다
> zrange birthyear 0 -1 # 전체 값 조회
```

### Expire 기능

- redis는 일정 시간이 지나면 Expire되어서 사라지는 기능 입니다
- 메모리가 한정적이기 때문에 삭제 시간을 미리 정의하는게 편리합니다
- 만약 동일한 키가 다시 들어오면 expire date가 다시 설정이 됩니다
- 처음: 30s, 현재 20s 인 data -> upate 및 생성 -> 30s로 다시 초기화