//Luis Alejandro Hernández Márquez (241424)
// Programación de plataformas moviles
// prof: Juan Carlos Durini

package com.example.labluishernandez.presentation.assetlist

import com.example.labluishernandez.domain.model.Asset

data class AssetListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val assets: List<Asset> = emptyList(),
    val originLabel: String? = null
)
