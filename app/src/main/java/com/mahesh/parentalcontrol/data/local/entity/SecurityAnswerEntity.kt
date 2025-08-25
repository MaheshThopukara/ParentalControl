package com.mahesh.parentalcontrol.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "security_answers")
data class SecurityAnswerEntity(
    @PrimaryKey val questionKey: String, // e.g., "q_city_partner"
    val hashB64: String,
    val saltB64: String,
    val iterations: Int
)