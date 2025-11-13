package com.example.labluishernandez.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest      // 👈 este es el bueno
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object CoinCapClient {

    val client: HttpClient by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    }
                )
            }

            defaultRequest {
                // URL correcta del API
                url("https://api.coincap.io/v3/")
                header(
                    "Authorization",
                    "Bearer 6f8c2f757cc81e9590a5aeed892ab4ff835114ebc73197f73f5a588b1e9371a"
                )
            }
        }
    }
}
