//Luis Alejandro Hernández Márquez (241424)
// Programación de plataformas moviles
// prof: Juan Carlos Durini

package com.example.labluishernandez.presentation.assetdetail

import com.example.labluishernandez.domain.model.Asset

data class AssetDetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val asset: Asset? = null,
    val originLabel: String? = null
)
