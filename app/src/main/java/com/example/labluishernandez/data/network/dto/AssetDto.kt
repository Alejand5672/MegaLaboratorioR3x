//Luis Alejandro Hernández Márquez (241424)
// Programación de plataformas moviles
// prof: Juan Carlos Durini

package com.example.labluishernandez.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssetDto(
    val id: String,
    val rank: String,
    val symbol: String,
    val name: String,
    val supply: String,
    val maxSupply: String? = null,
    val marketCapUsd: String,
    val priceUsd: String,
    @SerialName("changePercent24Hr")
    val changePercent24Hr: String
)

@Serializable
data class AssetsResponseDto(
    val data: List<AssetDto>
)

@Serializable
data class AssetDetailResponseDto(
    val data: AssetDto
)
