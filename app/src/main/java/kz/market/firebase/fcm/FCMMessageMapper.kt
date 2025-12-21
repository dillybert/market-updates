package kz.market.firebase.fcm

import com.google.firebase.messaging.RemoteMessage
import kz.market.domain.model.AppNotification
import javax.inject.Inject

class FCMMessageMapper @Inject constructor() {
    fun map(remoteMessage: RemoteMessage): AppNotification =
        AppNotification(
            title = remoteMessage.data["title"] ?: "",
            body = remoteMessage.data["body"] ?: "",
            channelId = remoteMessage.data["channelId"] ?: "common_channel",
            deepLink = remoteMessage.data["deepLink"]
        )
}