// Place your Spring DSL code here
beans = {
    taskExecutor(org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor) {
        corePoolSize = 10
        maxPoolSize = 30
        queueCapacity = 6
        keepAliveSeconds = 2000
    }
    mail(SendMailService) {
        taskExecutor = ref('taskExecutor')
    }
}