package kz.market.updater.data.receiver

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kz.market.updater.domain.model.UpdateStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InstallStateHolder @Inject constructor() {
    private val _status = MutableStateFlow<UpdateStatus>(UpdateStatus.Idle)
    val status: StateFlow<UpdateStatus> = _status

    fun emit(status: UpdateStatus) {
        _status.value = status
    }
}