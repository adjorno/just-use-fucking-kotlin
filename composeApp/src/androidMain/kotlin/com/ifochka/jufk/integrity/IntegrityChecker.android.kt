package com.ifochka.jufk.integrity

import android.util.Log
import com.google.android.play.core.integrity.IntegrityManagerFactory
import com.google.android.play.core.integrity.IntegrityTokenRequest
import com.ifochka.jufk.app.JufkApplication
import kotlinx.coroutines.tasks.await
import java.util.UUID

actual class IntegrityChecker actual constructor() {
    actual suspend fun verify(): IntegrityVerdict? = runCatching {
        val manager = IntegrityManagerFactory.create(JufkApplication.context)
        val token = manager.requestIntegrityToken(
            IntegrityTokenRequest.builder().setNonce(UUID.randomUUID().toString()).build(),
        ).await().token()
        Log.d(TAG, "Token obtained, calling backend")
        IntegrityApiClient().verify(token, JufkApplication.context.packageName)
    }.onFailure { e ->
        Log.e(TAG, "Integrity check failed: ${e.message}", e)
    }.getOrNull()

    private companion object {
        const val TAG = "IntegrityChecker"
    }
}
