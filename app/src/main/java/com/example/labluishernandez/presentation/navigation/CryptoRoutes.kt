//Luis Alejandro Hernández Márquez (241424)
// Programación de plataformas moviles
// prof: Juan Carlos Durini

package com.example.labluishernandez.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object AssetListRoute

@Serializable
data class AssetDetailRoute(
    val assetId: String
)
