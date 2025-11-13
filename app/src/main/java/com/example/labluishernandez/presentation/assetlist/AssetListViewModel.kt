package com.example.labluishernandez.presentation.assetlist

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.labluishernandez.domain.repository.CryptoRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AssetListViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = CryptoRepositoryImpl(application)

    var state by mutableStateOf(AssetListState())
        private set

    init {
        loadAssets()
    }

    fun onReload() {
        loadAssets()
    }

    fun onSaveOfflineClicked() {
        viewModelScope.launch {
            if (state.assets.isNotEmpty()) {
                repository.saveAssetsOffline(state.assets)
            }

            loadAssetsFromLocal()
        }
    }

    private fun loadAssets() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            delay(2000)

            val result = repository.getAssetList()

            if (result.assets.isEmpty()) {
                state = state.copy(
                    isLoading = false,
                    error = "No se pudo obtener información.",
                    assets = emptyList(),
                    originLabel = null
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
                    assets = result.assets,
                    originLabel = label
                )
            }
        }
    }

    private fun loadAssetsFromLocal() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            // Delay de 2 segundos
            delay(2000)

            val result = repository.getAssetListFromLocal()

            if (result.assets.isEmpty()) {
                state = state.copy(
                    isLoading = false,
                    error = "No hay datos guardados offline.",
                    assets = emptyList(),
                    originLabel = null
                )
            } else {
                val label = result.lastUpdateText?.let { "Viendo data del $it" }
                    ?: "Viendo data guardada offline"

                state = state.copy(
                    isLoading = false,
                    error = null,
                    assets = result.assets,
                    originLabel = label
                )
            }
        }
    }
}