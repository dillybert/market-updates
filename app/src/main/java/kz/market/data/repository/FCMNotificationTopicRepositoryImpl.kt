package kz.market.data.repository

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import kz.market.domain.repository.NotificationTopicRepository
import javax.inject.Inject

class FCMNotificationTopicRepositoryImpl @Inject constructor(
    private val firebaseMessaging: FirebaseMessaging
) : NotificationTopicRepository {
    override suspend fun subscribeToTopic(topic: String) {
        firebaseMessaging
            .subscribeToTopic(topic)
            .await()
    }

    override suspend fun unsubscribeFromTopic(topic: String) {
        firebaseMessaging
            .unsubscribeFromTopic(topic)
            .await()
    }
}