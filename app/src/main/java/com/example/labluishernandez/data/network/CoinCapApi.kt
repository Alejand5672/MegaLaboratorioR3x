//Luis Alejandro Hernández Márquez (241424)
// Programación de plataformas moviles
// prof: Juan Carlos Durini

package com.example.labluishernandez.data.network

import com.example.labluishernandez.data.network.dto.AssetDetailResponseDto
import com.example.labluishernandez.data.network.dto.AssetDto
import com.example.labluishernandez.data.network.dto.AssetsResponseDto
import io.ktor.client.call.body
import io.ktor.client.request.get

interface CoinCapApi {
    suspend fun getAssets(): List<AssetDto>
    suspend fun getAssetById(id: String): AssetDto
}

class CoinCapApiImpl(
    private val client: io.ktor.client.HttpClient = CoinCapClient.client
) : CoinCapApi {

    override suspend fun getAssets(): List<AssetDto> {
        val response: AssetsResponseDto = client.get("assets").body()
        return response.data
    }

    override suspend fun getAssetById(id: String): AssetDto {
        val response: AssetDetailResponseDto = client.get("assets/$id").body()
        return response.data
    }
}
