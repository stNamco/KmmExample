package com.stnamco.shared

actual class Platform actual constructor() {
    actual fun platformName(): String {
        return "Android"
    }
}