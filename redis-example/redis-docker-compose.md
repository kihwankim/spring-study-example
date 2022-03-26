## 이슈
### 환경
- mac book pro m1

### 이슈 원인 
- mac에서는 docker conatiner의 ip address를 routing table에 등록하지 않아서 docker compose로 띄운 sentinel redis에서 timeout 에러가 납니다
- flow: sentinel 에서 master redis container ip/port 번호 조회 -> 조회된 redis ip/port로 query 실행 -> routing 불가로 timeout 발생

참고자료: https://docs.docker.com/desktop/mac/networking/#there-is-no-docker0-bridge-on-macos