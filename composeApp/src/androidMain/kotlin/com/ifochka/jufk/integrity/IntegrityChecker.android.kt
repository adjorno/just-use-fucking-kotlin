package com.ifochka.jufk.integrity

import com.google.android.play.core.integrity.IntegrityManagerFactory
import com.google.android.play.core.integrity.IntegrityTokenRequest
import com.ifochka.jufk.AppContext
import kotlinx.coroutines.tasks.await
import java.util.UUID

actual class IntegrityChecker actual constructor() {
    actual suspend fun verify(): IntegrityVerdict? = runCatching {
        val context = AppContext.applicationContext
        val manager = IntegrityManagerFactory.create(context)
        val token = manager.requestIntegrityToken(
            IntegrityTokenRequest.builder().setNonce(UUID.randomUUID().toString()).build(),
        ).await().token()
        IntegrityApiClient().verify(token, context.packageName)
    }.getOrNull()
}
