//Luis Alejandro Hernández Márquez (241424)
// Programación de plataformas moviles
// prof: Juan Carlos Durini

package com.example.labluishernandez.domain.repository

import com.example.labluishernandez.domain.model.Asset

data class AssetListResult(
    val assets: List<Asset>,
    val isFromRemote: Boolean,
    val lastUpdateText: String?
)

data class AssetDetailResult(
    val asset: Asset?,
    val isFromRemote: Boolean,
    val lastUpdateText: String?
)

interface CryptoRepository {
    suspend fun getAssetList(): AssetListResult
    suspend fun getAssetListFromLocal(): AssetListResult
    suspend fun getAssetById(id: String): AssetDetailResult
    suspend fun saveAssetsOffline(assets: List<Asset>)
}