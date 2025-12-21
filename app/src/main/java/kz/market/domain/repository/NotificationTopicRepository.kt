package kz.market.domain.repository

interface NotificationTopicRepository {
    suspend fun subscribeToTopic(topic: String)
    suspend fun unsubscribeFromTopic(topic: String)
}