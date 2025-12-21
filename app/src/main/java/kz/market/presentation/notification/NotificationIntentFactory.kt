package kz.market.presentation.notification

import android.content.Context
import android.content.Intent
import android.net.Uri
import kz.market.MainActivity
import kz.market.domain.model.AppNotification
import kotlin.jvm.java

object NotificationIntentFactory {
    fun create(context: Context, appNotification: AppNotification): Intent =
        if (appNotification.deepLink != null) {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(appNotification.deepLink)
            ).apply {
                setPackage(context.packageName)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        } else {
            Intent(
                context,
                MainActivity::class.java
            )
        }
}