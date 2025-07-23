package com.dothebestmayb.pickpickmovie.shared.core.toast

interface ToastManager {
    fun showToast(
        message: String,
        durationType: ToastDurationType = ToastDurationType.SHORT
    )
}