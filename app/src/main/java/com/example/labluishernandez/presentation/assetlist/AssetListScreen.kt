//Luis Alejandro Hernández Márquez (241424)
// Programación de plataformas moviles
// prof: Juan Carlos Durini

package com.example.labluishernandez.presentation.assetlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.labluishernandez.domain.model.Asset

@Composable
fun AssetListScreen(
    state: AssetListState,
    onReload: () -> Unit,
    onVerOffline: () -> Unit,
    onAssetClick: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Criptomonedas",
                    style = MaterialTheme.typography.headlineSmall
                )
                Button(onClick = onReload) {
                    Text(text = "Actualizar")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onVerOffline) {
                    Text(text = "Ver offline")
                }

                if (state.originLabel != null) {
                    Text(
                        text = state.originLabel,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (state.error != null) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(state.assets) { asset ->
                    AssetItem(
                        asset = asset,
                        onClick = { onAssetClick(asset.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun AssetItem(
    asset: Asset,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = asset.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = asset.symbol,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "USD ${"%.2f".format(asset.priceUsd)}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    val change = asset.changePercent24h
                    val changeText = "${"%.2f".format(change)} %"
                    val color = if (change >= 0) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    }

                    Text(
                        text = changeText,
                        color = color,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
