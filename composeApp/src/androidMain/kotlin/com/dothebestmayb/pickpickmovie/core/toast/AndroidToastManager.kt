package com.dothebestmayb.pickpickmovie.core.toast

import android.content.Context
import android.widget.Toast
import com.dothebestmayb.pickpickmovie.core.toast.ToastDurationType
import com.dothebestmayb.pickpickmovie.core.toast.ToastManager

class AndroidToastManager(private val context: Context): ToastManager {
    override fun showToast(message: String, durationType: ToastDurationType) {
        val duration = when (durationType) {
            ToastDurationType.SHORT -> Toast.LENGTH_SHORT
            ToastDurationType.LONG -> Toast.LENGTH_LONG
        }
        Toast.makeText(context, message, duration).show()
    }
}