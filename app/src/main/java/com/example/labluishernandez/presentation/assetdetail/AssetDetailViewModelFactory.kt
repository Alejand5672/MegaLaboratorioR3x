package com.example.labluishernandez.presentation.assetdetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AssetDetailViewModelFactory(
    private val application: Application,
    private val assetId: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AssetDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AssetDetailViewModel(application, assetId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
