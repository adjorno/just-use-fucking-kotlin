package com.ifochka.jufk.app

import android.app.Application
import android.content.Context

class JufkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
    }
}
