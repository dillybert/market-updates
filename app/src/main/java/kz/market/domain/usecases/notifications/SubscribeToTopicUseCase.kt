package kz.market.domain.usecases.notifications

import kz.market.domain.repository.NotificationTopicRepository
import javax.inject.Inject

class SubscribeToTopicUseCase @Inject constructor(
    private val notificationTopicRepository: NotificationTopicRepository
) {
    suspend operator fun invoke(topic: String) =
        notificationTopicRepository.subscribeToTopic(topic)
}