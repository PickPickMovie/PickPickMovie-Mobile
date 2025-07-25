package com.dothebestmayb.pickpickmovie.core.window

import platform.UIKit.UIApplication
import platform.UIKit.UISceneActivationStateForegroundActive
import platform.UIKit.UIWindow
import platform.UIKit.UIWindowScene

fun UIApplication.currentKeyWindow(): UIWindow? =
    connectedScenes
        .filterIsInstance<UIWindowScene>()
        .firstOrNull { it.activationState == UISceneActivationStateForegroundActive }
        ?.windows
        ?.firstOrNull { (it as? UIWindow)?.isKeyWindow() ?: false } as? UIWindow
