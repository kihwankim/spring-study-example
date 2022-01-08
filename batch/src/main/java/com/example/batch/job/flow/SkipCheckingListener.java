package com.example.batch.job.flow;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

public class SkipCheckingListener extends StepExecutionListenerSupport {
    public ExitStatus afterStep(StepExecution stepExecution) {
        String exitCode = stepExecution.getExitStatus().getExitCode();
        if (!exitCode.equals(ExitStatus.FAILED.getExitCode()) &&
                stepExecution.getSkipCount() > 0) {
            return new ExitStatus("COMPLETED WITH SKIPS");
        }

        return null;
    }

    /**
     * .start(step1())
     *     .on("FAILED") step 1이 failed 이면 바로 종료 -> job 실패
     *     .end()
     * .from(step1())
     *     .on("COMPLETED WITH SKIPS")
     *     .to(errorPrint1())
     *     .end()
     * .from(step1()) // step1 성공시 step 2실행
     *     .on("*")
     *     .to(step2())
     *     .end()
     */
}
