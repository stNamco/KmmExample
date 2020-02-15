package com.stnamco.shared

import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual fun platformName(): String {
        return UIDevice.currentDevice.systemName()
    }
}