package com.ifochka.jufk

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() =
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "JUFK",
            state = rememberWindowState(width = 1300.dp, height = 730.dp),
        ) {
            App()
        }
    }
