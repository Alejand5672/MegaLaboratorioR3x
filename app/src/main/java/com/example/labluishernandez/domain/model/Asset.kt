package com.example.labluishernandez.domain.model

data class Asset(
    val id: String,
    val name: String,
    val symbol: String,
    val priceUsd: Double,
    val changePercent24h: Double,
    val supply: Double?,
    val maxSupply: Double?,
    val marketCapUsd: Double?
)
