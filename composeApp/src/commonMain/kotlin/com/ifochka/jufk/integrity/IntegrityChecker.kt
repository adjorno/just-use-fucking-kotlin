package com.ifochka.jufk.integrity

expect class IntegrityChecker() {
    suspend fun verify(): IntegrityVerdict?
}
