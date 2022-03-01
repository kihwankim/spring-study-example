
# Test Code

## 설정
### SpringBootTest
- 통합 테스트 실행시 사용할 java 설정을 초기화 할때 사용합니다
- (classes={...}) 내부에 test를 진행할 batch config 정보를 입력하고, 배체 테스트 환경 정보가 들어있는 파일을 import 해줍니다
  - 전체 Test Code실행시 특정 Job Bean정보만 Container에 올리기 때문에 전체 Test에서 Applciation Context가 재실행 되는건 동일합니다
- @ContextConfiguration방이기도 합니다
  - @SpringBootTes내부에 @ContextConfiguration을 사용하기 때문에 동일하다고 생각 할 수 있습니다

### JobLauncherTestUtils
- Batch Job을 테스트 환경에서 실행할 Utils 클래스 입니다
- CLI등으로 실행하는 Job을 테스트 코드에서 Job을 실행할 수 있도록 지원 합니다
- 통합 테스트를 위한 기능

### jobLauncherTestUtils.launchJob(jobParameters)
- JobParameter와 함깨 Job을 실행할때 사용
- 해당 Job결과는 JobExecution 에 담겨 반환 됩니다
- 성공적으로 Batch가 수행되었지는 jobExecution.getStatus()로 검증할 수 있음
- @Autowierd setJob()으로 현재 Bean에 올라간 Job을 주입 받아서 사용합니다 그렇기 때문에 @SprinBootTest에 작성된 Job만 실행이 가능합니다
  - 만약 하나의 Config file에 여러개의 job이 있을 경우 어떤것을 선택해야할지 알 수 없기 때문에 에러가 납니

### 과서 Batch 설정 방법

- @ConditionalOnProperty와 @TestPropertySource을 사용해서 특정 Batch Job만 불러와서 사용
- 단점
  - 많은 test 설정 코드가 필요
  - 전체 테스트 수행시 매번 Spring Context가 재실행
    - @TestPropertySource로 인해서 전체 테스트 수행시 매번 SpringContext가 다시 생성됨
- 장점
  - @Bean충돌이 안 일어남
  - @ConditionalOnProperty 덕분에 Job/Step/Reader 등의 Bean 생성시 다른 Job에서 사용된 Bean 이름에 대해서 크게 신경쓰지 않아도 됩니다
  - 매번 단일 Batch Job설정명 Spring Context에 추가 되기 때문에 단위 테스트에서는 빠르게 실행 가능

### @EnableBatchProcessing
- Batch 환경을 자동으로 설정
- Test환경에서도 필요하기 때문에 별도의 설정에서 선언되어 상요합니다
  - 모든 Test class에서 선언하는 불편함을 없애기 위해서 Test Code에서 공유하기 위한 Config file에 정의 합니다

### JobRepositoryTestUtils
- DB에 생성된 JobExecution을 쉽게 생성/삭제 가능하게 지원 합니다
- 통합 테스트를 위해 사용

### StepScopeTestExecutionListener
- Batch 단위 테스트시 StepScope 컨텍스트를 생성
- 해당 Context를 통해서 JobParameter등을 단위 테스트에서 DI 받을 수 있음

### JobScopeTestExecutionListener
- Batch 단위 테스트시 JobScope 컨텍스트를 생성
- JobParameter등을 단위 테스트에 DI받을 수 있음

### @SpringBatchTest
- Spring Batch 4.1버전에 새롭게 추가된 annotation
- JobLauncherTestUtils를 지원 받기 위해서 사용합니다
- ApplicationContext에 테스트에 필요한 여러 Utils Bean을 자동으로 등록해줍니다