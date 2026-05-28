package com.ifochka.jufk.integrity

actual class IntegrityChecker actual constructor() {
    actual suspend fun verify(): IntegrityVerdict? = null
}
