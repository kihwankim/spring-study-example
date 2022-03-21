## SpringBoot Run method 호출 과정

### 과정

1. BootStrapContext 생성
2. Java AWT Headless Property 설정
3. 스프링 애플리케이션 리스너 조회 및 starting 처리
4. Arguments 래핑 및 Environment 준비
5. IgnoreBeanInfo 설정
6. 배너 출력
7. 애플리케이션 컨텍스트 생성
8. Context 준비 단계
9. Context Refresh 단계
10. Context Refresh 후처리 단계
11. 실행 시간 출력 및 리스너 started 처리
12. Runners 실행

### BootStrapContext
- BootStrapContext: ApplicationContext가 준비될 때 까지 환경 변수들을 관리하는 스프링의 Environment 객체를 후처리하기 위한 임시 컨텍스트
- bootstrapRegistryInitializer는 BootStrapContext에 저장해야하는 설정 정보들을 가지고 있습니다
- 