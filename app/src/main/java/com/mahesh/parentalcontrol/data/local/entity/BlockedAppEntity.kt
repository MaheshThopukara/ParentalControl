package com.mahesh.parentalcontrol.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blocked_apps")
data class BlockedAppEntity(
    @PrimaryKey val pkg: String
)