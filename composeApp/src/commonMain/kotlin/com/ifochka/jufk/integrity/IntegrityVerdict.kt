package com.ifochka.jufk.integrity

import kotlinx.serialization.Serializable

@Serializable
data class IntegrityVerdict(
    val pass: Boolean,
    val appRecognized: Boolean,
    val deviceIntegrity: List<String>,
    val licensingVerdict: String,
)
