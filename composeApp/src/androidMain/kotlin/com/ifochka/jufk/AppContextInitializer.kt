package com.ifochka.jufk

import android.content.Context
import androidx.startup.Initializer

object AppContext {
    private lateinit var _applicationContext: Context
    val applicationContext: Context
        get() = _applicationContext

    fun setApplicationContext(context: Context) {
        _applicationContext = context.applicationContext
    }
}

class AppContextInitializer : Initializer<Context> {
    override fun create(context: Context): Context {
        AppContext.setApplicationContext(context)
        return context
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
