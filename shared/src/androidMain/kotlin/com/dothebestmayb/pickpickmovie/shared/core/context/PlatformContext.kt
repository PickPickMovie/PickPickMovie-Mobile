package com.dothebestmayb.pickpickmovie.shared.core.context

import androidx.activity.ComponentActivity

actual class PlatformContext(private val activity: ComponentActivity) {
    actual fun getContext(): Any {
        return activity.applicationContext
    }
}