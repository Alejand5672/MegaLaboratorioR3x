//Luis Alejandro Hernández Márquez (241424)
// Programación de plataformas moviles
// prof: Juan Carlos Durini

package com.example.labluishernandez.presentation.assetdetail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AssetDetailScreen(
    state: AssetDetailState
) {
    Box(modifier = Modifier.fillMaxSize()) {

        state.asset?.let { asset ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = asset.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = asset.symbol,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                state.originLabel?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Precio USD: ${"%.4f".format(asset.priceUsd)}")
                        Text("Cambio 24h: ${"%.2f".format(asset.changePercent24h)} %")
                        Text("Supply: ${asset.supply ?: 0.0}")
                        Text("Max Supply: ${asset.maxSupply ?: 0.0}")
                        Text("Market Cap USD: ${asset.marketCapUsd ?: 0.0}")
                    }
                }

                state.error?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
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
