package com.mahesh.parentalcontrol.domain.model

import android.graphics.drawable.Drawable

data class AppBlockList(
    val packageName: String,
    val appName: String,
    val icon: Drawable,
    val isBlockListed: Boolean
)