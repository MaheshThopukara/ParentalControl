package com.mahesh.parentalcontrol.domain.model

import android.graphics.drawable.Drawable

data class AppUsage(
    val packageName: String,
    val appName: String,
    val icon: Drawable,
    val screenTimeMillis: Long,
    val dailyLimitMillis: Long?
)
