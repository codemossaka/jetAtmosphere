package ru.godsonpeya.atmosphere.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.godsonpeya.atmosphere.data.local.entity.ApiStatus

object SongBookEventBroadcast {
    private val status = MutableStateFlow(ApiStatus.IDLE)

    val watchSongBookStatus = status.asStateFlow()

    fun setStatus(currentStatus: ApiStatus) {
        status.value = currentStatus
    }

    fun resetState() {
        status.value = ApiStatus.IDLE
    }
}
