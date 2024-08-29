package com.example.quartzexample.simple_job

import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import java.util.concurrent.ExecutorService

@Configuration
class SimpleJobConfig {

    @Bean
    fun simpleJobDetail(): JobDetail {
        return JobBuilder.newJob(SimpleLogJob::class.java)
            .withIdentity(SimpleLogJob::class.simpleName)
            .storeDurably()
            .build()
    }

    /**
     * Job을 실행시키기 위한 정보를 담고 있는 객체이다. Job의 이름, 그룹, JobDataMap 속성 등을 지정할 수 있다. Trigger가 Job을 수행할 때 이 정보를 기반으로 스케줄링을 한다
     */
    @Bean
    fun simpleLogTrigger(): Trigger {
        val jobDetail = JobBuilder.newJob(SimpleLogJob::class.java)
            .withIdentity(SimpleLogJob::class.simpleName)  // Job의 이름과 그룹 설정
            .usingJobData("myKey", "myValue")  // Job에 데이터 전달
            .build()

        return TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity(SimpleLogJob::class.simpleName)
            .withSchedule(CronScheduleBuilder.cronSchedule("0 29 18 * * ?"))
            .build()
    }

    @Bean
    fun simpleSchedulerFactoryBean(
        executorService: ExecutorService,
    ): SchedulerFactoryBean {
        return SchedulerFactoryBean().apply {
            setTaskExecutor(executorService)
        }
    }

    @Bean
    fun simpleLogScheduler(simpleSchedulerFactoryBean: SchedulerFactoryBean): Scheduler {
        val scheduler = simpleSchedulerFactoryBean.scheduler

        // JobDetail과 Trigger를 Scheduler에 등록
        val jobDetail = simpleJobDetail()
        val trigger = simpleLogTrigger()

        scheduler.scheduleJob(jobDetail, trigger)
        scheduler.listenerManager.addJobListener(CustomJobListener())

        return scheduler
    }
}