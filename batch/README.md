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

## Transition

### BatchStatus

- 정의: JobExecution과 StepExecution의 속성으로 Job과 Step의 종료 후 최종 결과 상태가 무엇인지 정의
- 특정
    - SimpleJob
        - 마지막 Step의 BatchStatus값을 Job의 최종 BatchStatus값으로 반영
        - Step이 실패할 경우 실패한 Step이 마지막 Step이 된다
    - FlowJob
        - Flow내 Step의 ExitStatus값을 FlowExecutionStatus 값으로 저장
        - 마지막 Flow의 FlowExecutionStatus값을 Job의 최종 BatchStatus값으로 반영
    - cf) ABANDONED: 처리를 완료했지만, 성공하지 못한 상태, 재시작시 건너 띄어야 하는 단계

### ExitStatus

- 정의
    - JobExecution과 StepExecution의 속성으로 Job과 Step의 실행 후 어떤 상태로 종료되었는지 정의
    - 기본적으로 ExitStatus는 BatchStatus와 동일한 값으로 설정된다
- SimpleJob
    - 마지막 Step의 ExitStatus값을 Job의 최종 ExistStatus값으로 반영
- FlowJob
    - Flow내 Step의 ExitStatus값을 FlowExecutionStatus 값으로 저장
    - 마지막 Flow의 FlowExecutionStatus값을 job의 최종 ExitStatus 값으로 반영
- 사용자 정의 ExitStatus설정 가능

cf) JobExecution과 StepExecution는 BatchStatus, ExitStatus모두 가지고 있음

# Spring Batch Chunk

## TaskletStep

### 정의

- transactional 관리하는 객체와 실제 개발자가 작성한 item reader, processor, writer를 호출 하는 `ChunkOrientedTasklet`를 관리하는 객체
- AbstractStep을 상속받은 구현체(전체 flow가 들어있는 Template Callback 패턴 abstract class)

### Flow

 ```java
 public class TaskletStep extends AbstractStep {

    private Tasklet tasklet; // ChunkOrientedTasklet 를 가지게 됩니다

    @Override
    protected void doExecute(StepExecution stepExecution) throws Exception {
        stepExecution.getExecutionContext().put(TASKLET_TYPE_KEY, tasklet.getClass().getName());
        stepExecution.getExecutionContext().put(STEP_TYPE_KEY, this.getClass().getName());

        stream.update(stepExecution.getExecutionContext());
        getJobRepository().updateExecutionContext(stepExecution);

        // Shared semaphore per step execution, so other step executions can run
        // in parallel without needing the lock
        final Semaphore semaphore = createSemaphore();

        stepOperations.iterate(new StepContextRepeatCallback(stepExecution) { // iterator 내부에서 doInChunkContext method를 호출 하게 됩니다
            @Override
            public RepeatStatus doInChunkContext(RepeatContext repeatContext, ChunkContext chunkContext)
                    throws Exception {

                StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();

                // Before starting a new transaction, check for
                // interruption.
                interruptionPolicy.checkInterrupted(stepExecution);

                RepeatStatus result;
                try {
                    result = new TransactionTemplate(transactionManager, transactionAttribute) // transactional Template 호출 template call back 패턴으로 처리
                            .execute(new ChunkTransactionCallback(chunkContext, semaphore));
                } catch (UncheckedTransactionException e) {
                    // Allow checked exceptions to be thrown inside callback
                    throw (Exception) e.getCause();
                }

                chunkListener.afterChunk(chunkContext);

                // Check for interruption after transaction as well, so that
                // the interrupted exception is correctly propagated up to
                // caller
                interruptionPolicy.checkInterrupted(stepExecution);

                return result == null ? RepeatStatus.FINISHED : result;
            }

        });

    }

    private class ChunkTransactionCallback extends TransactionSynchronizationAdapter implements TransactionCallback<RepeatStatus> {

        @Override
        public RepeatStatus doInTransaction(TransactionStatus status) { // transactional 내부의 biz 로직 호출
            // codes...

            try {

                try {
                    try {
                        result = tasklet.execute(contribution, chunkContext); // 한개의 chunk에 대해서 read process write 로직 호출
                        // TaskletStep(outer class)의 tasklet를 사용하게 됩니다
                        if (result == null) {
                            result = RepeatStatus.FINISHED;
                        }
                    } catch (Exception e) {
                        if (transactionAttribute.rollbackOn(e)) {
                            chunkContext.setAttribute(ChunkListener.ROLLBACK_EXCEPTION_KEY, e); // 현재 transaction에 대해서 rollback 처리 할수 있도록 변경
                            throw e;
                        }
                    }
                } finally {

                    // If the step operations are asynchronous then we need
                    // to synchronize changes to the step execution (at a
                    // minimum). Take the lock *before* changing the step
                    // execution.
                    try {
                        semaphore.acquire();
                        locked = true;
                    } catch (InterruptedException e) {
                        logger.error("Thread interrupted while locking for repository update");
                        stepExecution.setStatus(BatchStatus.STOPPED);
                        stepExecution.setTerminateOnly();
                        Thread.currentThread().interrupt();
                    }

                    // Apply the contribution to the step
                    // even if unsuccessful
                    if (logger.isDebugEnabled()) {
                        logger.debug("Applying contribution: " + contribution);
                    }
                    stepExecution.apply(contribution);
                }

                stepExecutionUpdated = true;

                stream.update(stepExecution.getExecutionContext());

                try { // job repository에 관한 추가 처리 진행(성공 실패에 대한 정보를 DB에 적제 하는 로직
                    getJobRepository().updateExecutionContext(stepExecution); // step정보 저장
                    stepExecution.incrementCommitCount();
                    if (logger.isDebugEnabled()) {
                        logger.debug("Saving step execution before commit: " + stepExecution);
                    }
                    getJobRepository().update(stepExecution);
                } catch (Exception e) { // job repository 데이터 저장 실패
                    String msg = "JobRepository failure forcing rollback";
                    logger.error(msg, e);
                    throw new FatalStepExecutionException(msg, e);
                }

            } catch (Error e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Rollback for Error: " + e.getClass().getName() + ": " + e.getMessage());
                }
                rollback(stepExecution);
                throw e;
            } catch (RuntimeException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Rollback for RuntimeException: " + e.getClass().getName() + ": " + e.getMessage());
                }
                rollback(stepExecution);
                throw e;
            } catch (Exception e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Rollback for Exception: " + e.getClass().getName() + ": " + e.getMessage());
                }
                rollback(stepExecution);
                // Allow checked exceptions
                throw new UncheckedTransactionException(e);
            }

            return result;

        }
    }
}
 ```

 ```java
 public class TransactionTemplate extends DefaultTransactionDefinition
        implements TransactionOperations, InitializingBean {

    @Override
    @Nullable
    public <T> T execute(TransactionCallback<T> action) throws TransactionException {
        Assert.state(this.transactionManager != null, "No PlatformTransactionManager set");

        if (this.transactionManager instanceof CallbackPreferringPlatformTransactionManager) {
            return ((CallbackPreferringPlatformTransactionManager) this.transactionManager).execute(this, action);
        } else {
            TransactionStatus status = this.transactionManager.getTransaction(this);
            T result;
            try {
                result = action.doInTransaction(status); // transactional 생성 시점
            } catch (RuntimeException | Error ex) {
                // Transactional code threw application exception -> rollback
                rollbackOnException(status, ex);
                throw ex;
            } catch (Throwable ex) {
                // Transactional code threw 예상치 못한 exception 발생 -> rollback 처리
                rollbackOnException(status, ex);
                throw new UndeclaredThrowableException(ex, "TransactionCallback threw undeclared checked exception");
            }
            this.transactionManager.commit(status); // 정상 동작 commit 동작
            return result;
        }
    }
}
 ```

- `TaskletStep`이 `RepeateTemplate`의 `executeInternal` method를 호출
- `RepeateTemplate`의 `executeInternal` method는 chunk 단위의 로직을 반복적으로 호출 하는 전체 flow logic이 들어 있습니다
    - `RepeateTemplate`는 오직 반복적으로 chunk 단위로 나눈 횟수 만큼 `StepContextRepeatCallback`의 `doInChunkContext` method만 호출해준다고 보면 됩니다
    - `RepeateTemplate`의 `executeInternal` method는 내부적으로 transactional이 있지만 해당 부분은 외부에서 주입 받은 `StepContextRepeatCallback` class를 통해서 처리가 이뤄 집니다
    - `StepContextRepeatCallback`의 `doInChunkContext` 를 호출하게 되면, `TransactionTemplate` 객체를 생성하게 되는데 해당 객체가 transactional을 처리하는 class 입니다
- `StepContextRepeatCallback`의 `doInChunkContext` method는 transactional 처리와 `ChunkTransactionCallback` 객체를 통해서 `ChunkOrientedTasklet`의 chunk 단위 로직을 호출하는 부분 입니다
    - `ChunkTransactionCallback`는 `TaskletStep`의 내부 클래스 이기 때문에 `TaskletStep`이 가지고 있는 `ChunkOrientedTasklet` 객체에 접근을 할 수 있습니다
- `ChunkOrientedTasklet`의 비즈니스 로직을 호출 후 처리 transaction 처리 완료 후 종료를 하게 됩니다

## ChunkOrientedTasklet

### 정의

- 스프링 배치에서 제공하는 tasklet의 구현체이고, Chunk 지향 프로세싱을 담당
- read -> process -> write 작업을 진행
- spring batch에서는 `TaskletStep`내부에 있는 `ChunkTransactionCallback`라는 객체가 `TaskletStep` class가 가지고 있는 `ChunkOrientedTasklet`의 execute를 호출해서 비즈니스 로직을 실행합니다

 ```java
 public class ChunkOrientedTasklet<I> implements Tasklet {

    @Nullable
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        @SuppressWarnings("unchecked")
        Chunk<I> inputs = (Chunk<I>) chunkContext.getAttribute(INPUTS_KEY); // reader chunk size만큼 데이터 읽어들임
        if (inputs == null) {
            inputs = chunkProvider.provide(contribution);
            if (buffering) {
                chunkContext.setAttribute(INPUTS_KEY, inputs);
            }
        }

        chunkProcessor.process(contribution, inputs); // processor를 통해서 중간 연산 처리 후 writer를 통해 write logic 호출 하는 기능을 제공합니다
        chunkProvider.postProcess(contribution, inputs);

        // Allow a message coming back from the processor to say that we
        // are not done yet
        if (inputs.isBusy()) {
            logger.debug("Inputs still busy");
            return RepeatStatus.CONTINUABLE;
        }

        chunkContext.removeAttribute(INPUTS_KEY);
        chunkContext.setComplete();

        if (logger.isDebugEnabled()) {
            logger.debug("Inputs not busy, ended: " + inputs.isEnd());
        }
        return RepeatStatus.continueIf(!inputs.isEnd());

    }
}
 ```

### 특징

- Reader, Processor, Writer를 사용해서 chunk 기반의 데이터 입출력 처리를 담당
- TaskletStep에 의해서 반복적으로 실행, ChunkOrientedTasklet이 실행 될 때 마다 새로운 Transaction이 생성
- exception 발생시 chunk 를 rollback, 이전 commit은 완료됩니다
- 데이터 읽기 처리는(ItemReader) ChunkProvider(구현체: SimpleChunkProvider)가 담당
    - chunk size만큼 read 진행할때 repeatOperations는 chunk size만큼 iterate를 진행합니다
    - repeatOperations doRead라는 API를 호출해서 데이터 1 개씩 읽어 들이게 되고, input이라는 변수에 저장 후 반환
- 데이터 가공(ItemProcessor) 및 데이터 변경 및 추가는(ItemWriter)는 `ChunkProcessor`가 담당 합니다
    - `ChunkProcessor`는 `transform` method를 통해서 processor 로직을 호출하게 됩니다
    - `ChunkProcessor`는 `write` method를 통해서 writer 로직을 호출하게 됩니다

 ```java
 public class SimpleChunkProcessor<I, O> implements ChunkProcessor<I>, InitializingBean {

    @Override
    public final void process(StepContribution contribution, Chunk<I> inputs) throws Exception {
        // Allow temporary state to be stored in the user data field
        initializeUserData(inputs);

        if (isComplete(inputs)) { // input이 없을 경우 아무 것도 하지 않고 종료
            return;
        }

        Chunk<O> outputs = transform(contribution, inputs); // processor에 정의한 로직 실행, remove로 iterator의 데이터가 삭제되면 exception 발생

        // Adjust the filter count based on available data
        contribution.incrementFilterCount(getFilterCount(inputs, outputs));

        // Adjust the outputs if necessary for housekeeping purposes, and then
        // write them out...
        write(contribution, inputs, getAdjustedOutputs(inputs, outputs)); // process에서 처리한 결과를 바탕으로 저장 하는 기능을 제공

    }
}
 ```
