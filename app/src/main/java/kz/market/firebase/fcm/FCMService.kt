package kz.market.firebase.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kz.market.domain.usecases.notifications.HandleNotificationUseCase
import javax.inject.Inject

@AndroidEntryPoint
class FCMService : FirebaseMessagingService() {
    @Inject lateinit var mapper: FCMMessageMapper
    @Inject lateinit var handleNotificationUseCase: HandleNotificationUseCase

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.data.let {
            val appNotification = mapper.map(message)
            handleNotificationUseCase(appNotification)
        }
    }
}