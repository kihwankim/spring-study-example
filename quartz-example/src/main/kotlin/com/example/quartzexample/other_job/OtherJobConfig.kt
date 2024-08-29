package com.example.quartzexample.other_job

import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean

@Configuration
class OtherJobConfig {
    @Bean
    fun otherJobDetail(): JobDetail {
        return JobBuilder.newJob(OtherLogJob::class.java)
            .withIdentity(OtherLogJob::class.simpleName)
            .storeDurably()
            .build()
    }

    /**
     * Job을 실행시키기 위한 정보를 담고 있는 객체이다. Job의 이름, 그룹, JobDataMap 속성 등을 지정할 수 있다. Trigger가 Job을 수행할 때 이 정보를 기반으로 스케줄링을 한다
     */
    @Bean
    fun otherLogTrigger(): Trigger {
        val jobDetail = JobBuilder.newJob(OtherLogJob::class.java)
            .withIdentity(OtherLogJob::class.simpleName)  // Job의 이름과 그룹 설정
            .usingJobData("myKey", "myValue")  // Job에 데이터 전달
            .build()

        return TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity(OtherLogJob::class.simpleName)
            .withSchedule(CronScheduleBuilder.cronSchedule("0 41 19 * * ?"))
            .build()
    }

    @Bean
    fun otherLogScheduler(schedulerFactoryBean: SchedulerFactoryBean): Scheduler {
        val scheduler = schedulerFactoryBean.scheduler

        // JobDetail과 Trigger를 Scheduler에 등록
        val jobDetail = otherJobDetail()
        val trigger = otherLogTrigger()

        scheduler.scheduleJob(jobDetail, trigger)
        scheduler.listenerManager.addJobListener(OhterLogJobListener())

        return scheduler
    }
}