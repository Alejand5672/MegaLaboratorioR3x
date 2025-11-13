//Luis Alejandro Hernández Márquez (241424)
// Programación de plataformas moviles
// prof: Juan Carlos Durini

package com.example.labluishernandez.presentation.assetdetail

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.labluishernandez.domain.repository.CryptoRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AssetDetailViewModel(
    application: Application,
    private val assetId: String
) : AndroidViewModel(application) {

    private val repository = CryptoRepositoryImpl(application)

    var state by mutableStateOf(AssetDetailState())
        private set

    init {
        loadAsset()
    }

    private fun loadAsset() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            delay(2000)

            val result = repository.getAssetById(assetId)

            val asset = result.asset
            if (asset == null) {
                state = state.copy(
                    isLoading = false,
                    error = "No se encontró información del asset.",
                    asset = null
                )
            } else {
                val label = if (result.isFromRemote) {
                    "Viendo data más reciente"
                } else {
                    result.lastUpdateText?.let { "Viendo data del $it" }
                }

                state = state.copy(
                    isLoading = false,
                    error = null,
                    asset = asset,
                    originLabel = label
                )
            }
        }
    }
}