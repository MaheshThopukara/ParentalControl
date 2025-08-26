package com.mahesh.parentalcontrol.framework.scheduling

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mahesh.parentalcontrol.framework.worker.MidnightWorker
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.concurrent.TimeUnit

object MidnightWorkScheduler {
    const val UNIQUE_NAME = "midnight-rollover"

    fun enqueue(context: Context) {
        val delayMs = delayToNextMidnight()
        val req = PeriodicWorkRequestBuilder<MidnightWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(delayMs, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            UNIQUE_NAME,
            ExistingPeriodicWorkPolicy.UPDATE, // replace if already scheduled
            req
        )
    }

    private fun delayToNextMidnight(zone: ZoneId = ZoneId.systemDefault()): Long {
        val now = Instant.now().toEpochMilli()
        val nextMidnight = LocalDate.now(zone).plusDays(1)
            .atStartOfDay(zone).toInstant().toEpochMilli()
        return maxOf(0L, nextMidnight - now)
    }
}