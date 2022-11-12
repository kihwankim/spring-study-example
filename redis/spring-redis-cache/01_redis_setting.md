# Redis 설정

## 1. application.yml

```
spring:
  redis:
    lettuce:
      pool:
        min-idle: 0
        max-idle: 8
        max-active: 8
    port: 6379
    host: 127.0.0.1
```

- host: redis server host
- password: redis 서버 login password
- max-active: pool에 할당될 수 있는 커넥션 최대수 (음수로 사용시 무제한)
- max-idle: pool의 idle connection 최대 수 (양수일 때만 유효)
- min-idle: pool에서 관리하는 idle connetion 최소 수
- sentinel.master: redis server name
- sentinel.nodes: host:port 쌍 목록(콤마로 구분)
- timeout: connection timeout 시간(단위: ms)