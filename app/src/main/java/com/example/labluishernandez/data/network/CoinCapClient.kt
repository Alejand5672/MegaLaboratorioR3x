//Luis Alejandro Hernández Márquez (241424)
// Programación de plataformas moviles
// prof: Juan Carlos Durini

package com.example.labluishernandez.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
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
                url("https://rest.coincap.io/v3/")
                header(
                    "Authorization",
                    "Bearer 6f8c2f757cc81e9950a05aeed8292abff853114ebc731977f3f5a580b1e9371a"
                )
            }
        }
    }
}
