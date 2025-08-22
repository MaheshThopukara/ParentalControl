package com.mahesh.parentalcontrol.domain.model

data class DeviceTimeLimit(
    val isTurnedOn: Boolean = false,
    val timeLimitMillis: Long = 0L
)