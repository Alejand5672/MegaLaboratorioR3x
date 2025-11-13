//Luis Alejandro Hernández Márquez (241424)
// Programación de plataformas moviles
// prof: Juan Carlos Durini

package com.example.labluishernandez

import android.app.Application
import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.labluishernandez.presentation.assetdetail.AssetDetailScreen
import com.example.labluishernandez.presentation.assetdetail.AssetDetailState
import com.example.labluishernandez.presentation.assetdetail.AssetDetailViewModel
import com.example.labluishernandez.presentation.assetdetail.AssetDetailViewModelFactory
import com.example.labluishernandez.presentation.assetlist.AssetListScreen
import com.example.labluishernandez.presentation.assetlist.AssetListViewModel
import com.example.labluishernandez.presentation.navigation.AssetDetailRoute
import com.example.labluishernandez.presentation.navigation.AssetListRoute
import com.example.labluishernandez.ui.theme.LabLuisHernandezTheme// ajusta al nombre de tu theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabLuisHernandezTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    CryptoNavHost()
                }
            }
        }
    }
}

@Composable
fun CryptoNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "assetList"
    ) {
        composable("assetList") {
            val vm: AssetListViewModel = viewModel()
            AssetListScreen(
                state = vm.state,
                onReload = { vm.onReload() },
                onVerOffline = { vm.onSaveOfflineClicked() },
                onAssetClick = { assetId ->
                    navController.navigate("assetDetail/$assetId")
                }
            )
        }

        composable(
            route = "assetDetail/{assetId}",
            arguments = listOf(
                navArgument("assetId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val assetId = backStackEntry.arguments?.getString("assetId") ?: ""
            val vm: AssetDetailViewModel = viewModel(
                factory = AssetDetailViewModelFactory(
                    application = (navController.context.applicationContext as Application),
                    assetId = assetId
                )
            )
            AssetDetailScreen(state = vm.state)
        }
    }
}
