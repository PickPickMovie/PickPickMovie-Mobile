package com.dothebestmayb.pickpickmovie.core.toast

interface ToastManager {
    fun showToast(
        message: String,
        durationType: ToastDurationType = ToastDurationType.SHORT
    )
}