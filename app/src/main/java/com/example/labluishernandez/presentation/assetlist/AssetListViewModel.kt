package com.example.labluishernandez.presentation.assetlist

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.labluishernandez.domain.repository.CryptoRepositoryImpl
import kotlinx.coroutines.launch

class AssetListViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = CryptoRepositoryImpl(application)

    // ✅ ahora es estado observable para Compose
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
            repository.saveAssetsOffline(state.assets)
        }
    }

    private fun loadAssets() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
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
}

