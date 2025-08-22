package com.mahesh.parentalcontrol.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device_time_limit")
data class DeviceTimeLimitEntity(
    @PrimaryKey val id: Int = 0,
    val isTurnedOn: Boolean = false,
    val timeLimitMillis: Long = 0L
)