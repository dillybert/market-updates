############################
# 🔰 WORKMANAGER
############################
# Не удалять классы воркеров
-keep class androidx.work.** { *; }
-keep class * extends androidx.work.Worker { *; }
-keep class * extends androidx.work.CoroutineWorker { *; }

############################
# 🔰 UPDATER / INSTALLER / USE CASES
############################
# Все из updater-пакета
-keep class kz.market.updater.** { *; }