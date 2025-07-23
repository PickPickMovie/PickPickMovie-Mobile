@file:OptIn(ExperimentalForeignApi::class)

package com.dothebestmayb.pickpickmovie.core.toast

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGPointMake
import platform.CoreGraphics.CGRectMake
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UILabel
import platform.UIKit.UIScreen
import platform.UIKit.UIView
import platform.UIKit.UIViewAnimationOptionCurveEaseOut

class IosToastManager: ToastManager {
    override fun showToast(message: String, durationType: ToastDurationType) {
        val duration = when (durationType) {
            ToastDurationType.SHORT -> 2.0
            ToastDurationType.LONG -> 5.0
        }

        val rootViewController = UIApplication.Companion.sharedApplication.keyWindow?.rootViewController
        val toastLabel = UILabel(
            frame = CGRectMake(
                0.0,
                0.0,
                UIScreen.Companion.mainScreen.bounds.useContents { size.width } - 40,
                35.0
            )
        )
        toastLabel.center = CGPointMake(
            UIScreen.Companion.mainScreen.bounds.useContents { size.width } / 2,
            UIScreen.Companion.mainScreen.bounds.useContents { size.height } - 100.0
        )
        toastLabel.textAlignment = NSTextAlignmentCenter
        toastLabel.backgroundColor = UIColor.Companion.blackColor.colorWithAlphaComponent(0.6)
        toastLabel.textColor = UIColor.Companion.whiteColor
        toastLabel.text = message
        toastLabel.alpha = 1.0
        toastLabel.layer.cornerRadius = 15.0
        toastLabel.clipsToBounds = true
        rootViewController?.view?.addSubview(toastLabel)

        UIView.Companion.animateWithDuration(
            duration = duration,
            delay = 0.1,
            options = UIViewAnimationOptionCurveEaseOut,
            animations = {
                toastLabel.alpha = 0.0
            },
            completion = {
                if (it)
                    toastLabel.removeFromSuperview()
            })
    }
}