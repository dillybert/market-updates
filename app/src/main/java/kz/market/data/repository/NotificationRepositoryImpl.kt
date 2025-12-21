package kz.market.data.repository

import android.app.PendingIntent
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import kz.market.R
import kz.market.domain.model.AppNotification
import kz.market.domain.repository.NotificationRepository
import kz.market.presentation.notification.NotificationIntentFactory
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : NotificationRepository {
    override fun show(appNotification: AppNotification) {
        Log.d("FCMService", "Notification shown: $appNotification")

        val intent = NotificationIntentFactory.create(
            context = context,
            appNotification = appNotification
        )

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val systemNotification =
            NotificationCompat.Builder(context, appNotification.channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground_monochrome)
                .setContentTitle(appNotification.title)
                .setContentText(appNotification.body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        NotificationManagerCompat
            .from(context)
            .notify(System.currentTimeMillis().toInt(), systemNotification)
    }
}