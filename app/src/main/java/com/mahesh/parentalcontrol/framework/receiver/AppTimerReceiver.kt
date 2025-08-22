package com.mahesh.parentalcontrol.framework.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mahesh.parentalcontrol.domain.usecase.SuspendAppUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AppTimerReceiver : BroadcastReceiver() {

    @Inject lateinit var suspendAppUseCase: SuspendAppUseCase

    override fun onReceive(context: Context, intent: Intent) {
        val packageName = intent.getStringExtra("PACKAGE_NAME") ?: return
        CoroutineScope(Dispatchers.IO).launch {
            suspendAppUseCase(packageName)
        }

        // Display dialog if user watching UI
    }
}