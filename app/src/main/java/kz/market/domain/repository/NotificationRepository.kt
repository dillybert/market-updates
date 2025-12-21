package kz.market.domain.repository

import kz.market.domain.model.AppNotification

interface NotificationRepository {
    fun show(appNotification: AppNotification)
}