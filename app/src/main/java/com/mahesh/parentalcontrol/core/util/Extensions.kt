package com.mahesh.parentalcontrol.core.util

import kotlin.time.Duration.Companion.milliseconds

fun Long.toScreenTimeDisplay(): String {
    val duration = this.milliseconds

    val hours = duration.inWholeHours
    val minutes = duration.inWholeMinutes % 60

    return when {
        hours > 0 -> "$hours hr, $minutes min"
        else -> "$minutes minutes"
    }
}

fun Long.toAppTimeDisplay(): String {
    val duration = this.milliseconds

    val hours = duration.inWholeHours
    val minutes = duration.inWholeMinutes % 60

    return when {
        hours > 0 -> "${hours}h ${minutes}m"
        else -> "${minutes}m"
    }
}

fun Long.toDashBoardTotalScreenTimeDisplay(): String {
    val duration = this.milliseconds

    val hours = duration.inWholeHours
    val minutes = duration.inWholeMinutes % 60

    return when {
        hours > 0 -> "${hours} h ${minutes} m"
        else -> "${minutes} m"
    }
}

fun Long.toHour(): Int {
    val duration = this.milliseconds
    val hours = duration.inWholeHours
    return hours.toInt()
}

fun Long.toMinutes(): Int {
    val duration = this.milliseconds
    val minutes = duration.inWholeMinutes % 60
    return minutes.toInt()
}

