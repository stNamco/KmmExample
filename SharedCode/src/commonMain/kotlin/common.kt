package com.stnamco.shared

import io.ktor.client.HttpClient

expect class Platform() {
    fun platformName(): String
}

class Greeting {
    fun greeting(): String = "Hello, ${Platform().platformName()}"

    private val httpClient = HttpClient {
        print("initiated httpclient")
    }
}