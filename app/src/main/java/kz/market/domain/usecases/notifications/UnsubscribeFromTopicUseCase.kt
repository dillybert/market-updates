package kz.market.domain.usecases.notifications

import kz.market.domain.repository.NotificationTopicRepository
import javax.inject.Inject

class UnsubscribeFromTopicUseCase @Inject constructor(
    private val notificationTopicRepository: NotificationTopicRepository
) {
    suspend operator fun invoke(topic: String) =
        notificationTopicRepository.unsubscribeFromTopic(topic)
}