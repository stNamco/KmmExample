package com.stnamco.shared

expect class Platform() {
    fun platformName(): String
}

class Greeting {
    fun greeting(): String = "Hello, ${Platform().platformName()}"
}