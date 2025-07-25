@file:OptIn(ExperimentalForeignApi::class)

package com.dothebestmayb.pickpickmovie.core.toast

import com.dothebestmayb.pickpickmovie.core.window.currentKeyWindow
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

class IosToastManager : ToastManager {
    override fun showToast(message: String, durationType: ToastDurationType) {
        val duration = when (durationType) {
            ToastDurationType.SHORT -> 2.0
            ToastDurationType.LONG -> 5.0
        }

        val keyWindow = UIApplication.sharedApplication.currentKeyWindow()
        val rootViewController = keyWindow?.rootViewController ?: return

        val toastLabel = UILabel(
            frame = CGRectMake(
                0.0,
                0.0,
                UIScreen.mainScreen.bounds.useContents { size.width } - 40,
                35.0
            )
        ).apply {
            center = CGPointMake(
                UIScreen.mainScreen.bounds.useContents { size.width } / 2,
                UIScreen.mainScreen.bounds.useContents { size.height } - 100.0
            )
            textAlignment = NSTextAlignmentCenter
            backgroundColor = UIColor.blackColor.colorWithAlphaComponent(0.6)
            textColor = UIColor.whiteColor
            text = message
            alpha = 1.0
            layer.cornerRadius = 15.0
            clipsToBounds = true
        }

        rootViewController.view.addSubview(toastLabel)

        UIView.animateWithDuration(
            duration = duration,
            delay = 0.1,
            options = UIViewAnimationOptionCurveEaseOut,
            animations = { toastLabel.alpha = 0.0 },
            completion = { finished ->
                if (finished) {
                    toastLabel.removeFromSuperview()
                }
            })
    }
}
