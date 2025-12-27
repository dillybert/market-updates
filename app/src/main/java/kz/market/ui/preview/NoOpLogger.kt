package kz.market.ui.preview

import kz.market.logger.ApplicationEvent
import kz.market.logger.ApplicationLogger

class NoOpLogger : ApplicationLogger {
    override fun log(event: ApplicationEvent) = Unit
}