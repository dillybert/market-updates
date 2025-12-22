package kz.market.logger

interface ApplicationLogger {
    fun log(event: ApplicationEvent)
}