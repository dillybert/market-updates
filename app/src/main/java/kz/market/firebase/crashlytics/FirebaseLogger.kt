package kz.market.firebase.crashlytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kz.market.logger.ApplicationEvent
import kz.market.logger.ApplicationLogger
import javax.inject.Inject

class FirebaseLogger @Inject constructor(
    private val crashlytics: FirebaseCrashlytics,
    private val analytics: FirebaseAnalytics
) : ApplicationLogger {
    override fun log(event: ApplicationEvent) {
        val message = buildString {
            event.params.forEach { (key, value) ->
                append(" $key: $value")
            }
        }
        crashlytics.log(message)

        val bundle = Bundle().apply {
            event.params.forEach { param ->
                putString(param.key, param.value)
            }
        }
        analytics.logEvent(event.name, bundle)
    }
}