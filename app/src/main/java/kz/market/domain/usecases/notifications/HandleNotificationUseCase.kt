package kz.market.domain.usecases.notifications

import kz.market.domain.model.AppNotification
import kz.market.domain.repository.NotificationRepository
import javax.inject.Inject

class HandleNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke(appNotification: AppNotification) =
        notificationRepository.show(appNotification)
}