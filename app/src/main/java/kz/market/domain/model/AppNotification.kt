package kz.market.domain.model

data class AppNotification(
    val title: String,
    val body: String,
    val channelId: String,
    val deepLink: String? = null
)