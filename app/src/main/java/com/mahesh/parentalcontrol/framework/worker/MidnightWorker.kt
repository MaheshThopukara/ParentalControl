package com.mahesh.parentalcontrol.framework.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mahesh.parentalcontrol.domain.usecase.MidnightRolloverUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MidnightWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val rollover: MidnightRolloverUseCase
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            rollover()
            Result.success()
        } catch (t: Throwable) {
            Result.retry()
        }
    }
}