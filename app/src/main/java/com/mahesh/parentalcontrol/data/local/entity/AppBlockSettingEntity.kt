package com.mahesh.parentalcontrol.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_blocklist_setting")
data class AppBlockSettingEntity(
    @PrimaryKey val id: Int = 0,
    val enabled: Boolean = false
)