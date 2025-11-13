//Luis Alejandro Hernández Márquez (241424)
// Programación de plataformas moviles
// prof: Juan Carlos Durini

package com.example.labluishernandez.domain.repository

import android.app.Application
import com.example.labluishernandez.data.local.datastore.LastUpdateDataStore
import com.example.labluishernandez.data.local.datastore.cryptoPrefsDataStore
import com.example.labluishernandez.data.local.room.AppDatabase
import com.example.labluishernandez.data.local.room.AssetEntity
import com.example.labluishernandez.data.network.CoinCapApi
import com.example.labluishernandez.data.network.CoinCapApiImpl
import com.example.labluishernandez.domain.model.Asset
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CryptoRepositoryImpl(
    private val app: Application,
    private val api: CoinCapApi = CoinCapApiImpl()
) : CryptoRepository {

    private val db by lazy { AppDatabase.getInstance(app) }
    private val dao by lazy { db.assetDao() }
    private val lastUpdateDataStore by lazy {
        LastUpdateDataStore(app.cryptoPrefsDataStore)
    }

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    override suspend fun getAssetList(): AssetListResult {
        return try {
            val remote = api.getAssets()
            val assets = remote.map { dto ->
                Asset(
                    id = dto.id,
                    name = dto.name,
                    symbol = dto.symbol,
                    priceUsd = dto.priceUsd.toDoubleOrNull() ?: 0.0,
                    changePercent24h = dto.changePercent24Hr.toDoubleOrNull() ?: 0.0,
                    supply = dto.supply.toDoubleOrNull(),
                    maxSupply = dto.maxSupply?.toDoubleOrNull(),
                    marketCapUsd = dto.marketCapUsd.toDoubleOrNull()
                )
            }
            AssetListResult(
                assets = assets,
                isFromRemote = true,
                lastUpdateText = null
            )
        } catch (e: Exception) {
            val local = dao.getAll().map { it.toDomain() }
            val lastUpdate = lastUpdateDataStore.lastUpdateFlow.firstOrNull()
            AssetListResult(
                assets = local,
                isFromRemote = false,
                lastUpdateText = lastUpdate
            )
        }
    }

    override suspend fun getAssetListFromLocal(): AssetListResult {
        val local = dao.getAll().map { it.toDomain() }
        val lastUpdate = lastUpdateDataStore.lastUpdateFlow.firstOrNull()
        return AssetListResult(
            assets = local,
            isFromRemote = false,
            lastUpdateText = lastUpdate
        )
    }

    override suspend fun getAssetById(id: String): AssetDetailResult {
        return try {
            val dto = api.getAssetById(id)
            val asset = Asset(
                id = dto.id,
                name = dto.name,
                symbol = dto.symbol,
                priceUsd = dto.priceUsd.toDoubleOrNull() ?: 0.0,
                changePercent24h = dto.changePercent24Hr.toDoubleOrNull() ?: 0.0,
                supply = dto.supply.toDoubleOrNull(),
                maxSupply = dto.maxSupply?.toDoubleOrNull(),
                marketCapUsd = dto.marketCapUsd.toDoubleOrNull()
            )
            AssetDetailResult(
                asset = asset,
                isFromRemote = true,
                lastUpdateText = null
            )
        } catch (e: Exception) {
            val entity = dao.getById(id)
            val lastUpdate = lastUpdateDataStore.lastUpdateFlow.firstOrNull()
            AssetDetailResult(
                asset = entity?.toDomain(),
                isFromRemote = false,
                lastUpdateText = lastUpdate
            )
        }
    }

    override suspend fun saveAssetsOffline(assets: List<Asset>) {
        val entities = assets.map { it.toEntity() }
        dao.clearAll()
        dao.insertAll(entities)

        val now = LocalDateTime.now().format(dateFormatter)
        lastUpdateDataStore.saveLastUpdate(now)
    }

    private fun AssetEntity.toDomain() = Asset(
        id = id,
        name = name,
        symbol = symbol,
        priceUsd = priceUsd,
        changePercent24h = changePercent24h,
        supply = supply,
        maxSupply = maxSupply,
        marketCapUsd = marketCapUsd
    )

    private fun Asset.toEntity() = AssetEntity(
        id = id,
        name = name,
        symbol = symbol,
        priceUsd = priceUsd,
        changePercent24h = changePercent24h,
        supply = supply,
        maxSupply = maxSupply,
        marketCapUsd = marketCapUsd
    )
}