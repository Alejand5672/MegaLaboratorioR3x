package com.example.labluishernandez.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object AssetListRoute

@Serializable
data class AssetDetailRoute(
    val assetId: String
)
