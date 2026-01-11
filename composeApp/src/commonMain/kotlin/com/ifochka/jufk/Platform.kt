package com.ifochka.jufk

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
