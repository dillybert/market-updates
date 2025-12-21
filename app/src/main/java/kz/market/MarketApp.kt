package kz.market

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import kz.market.presentation.notification.NotificationChannelFactory

@HiltAndroidApp
class MarketApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationChannelFactory.createAll(this)
        FirebaseMessaging.getInstance().subscribeToTopic("updates")
            .addOnCompleteListener { task ->
                Log.d("FCMService", "Subscribed to updates")
            }
    }
}