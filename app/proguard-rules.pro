############################
# 🔰 HILT / DAGGER / VIEWMODEL
############################
# Не трогать Hilt и сгенерированные компоненты
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class kz.market.**_HiltModules { *; }
-keep class kz.market.**_HiltComponents { *; }
-keep class * extends androidx.lifecycle.ViewModel { *; }

############################
# 🔰 KOTLIN SERIALIZATION (если используешь)
############################
-keep class kotlinx.serialization.** { *; }
-keep @kotlinx.serialization.Serializable class * { *; }

############################
# 🔰 R8 / REFLECTION FIX
############################
# Сохраняем аннотации
-keepattributes *Annotation*
-keepattributes Signature, InnerClasses, EnclosingMethod
