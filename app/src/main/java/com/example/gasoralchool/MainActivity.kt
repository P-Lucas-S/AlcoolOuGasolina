package com.example.gasoralchool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gasoralchool.view.EthanolOrGas
import com.example.gasoralchool.view.GasStationView
import com.example.gasoralchool.view.ListGasStations
import com.example.gasoralchool.view.Routes
import com.example.gasoralchool.view.ui.theme.GasOrAlchoolTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      GasOrAlchoolTheme {
        val navController: NavHostController = rememberNavController()
        NavHost(navController = navController, startDestination = Routes.GAS_STATION) {
          composable(Routes.GAS_STATION) { ListGasStations(navController) }
          composable(Routes.GAS_STATION_FORM) { EthanolOrGas(navController, null) }
          composable(Routes.GAS_STATION_FORM + "/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            EthanolOrGas(navController, id)
          }
          composable(Routes.GAS_STATION_INFO + "/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            GasStationView(navController, id)
          }
        }
      }
    }
  }
}
