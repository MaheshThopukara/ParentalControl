package com.mahesh.parentalcontrol.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_limits")
data class AppLimitEntity(
    @PrimaryKey val packageName: String,
    val dailyLimitMillis: Long?, // null = no limit
    val observerId: Long
)