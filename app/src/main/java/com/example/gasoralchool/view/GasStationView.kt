package com.example.gasoralchool.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gasoralchool.R
import com.example.gasoralchool.models.gasStation.GasStation
import com.example.gasoralchool.models.gasStation.GasStationRepository

@Composable
fun GasStationView(navController: NavHostController, id: String?) {
  val context = LocalContext.current
  val gasStationRepository = remember { GasStationRepository(context) }

  val gasStationState = remember { mutableStateOf<GasStation?>(null) }
  val errorState = remember { mutableStateOf(false) }

  LaunchedEffect(id) {
    if (id == null) {
      errorState.value = true
      return@LaunchedEffect
    }
    try {
      val gasStation = gasStationRepository.read(id)
      gasStationState.value = gasStation
    } catch (e: Exception) {
      errorState.value = true
    }

    if (errorState.value || gasStationState.value == null) {
      navController.navigate(Routes.GAS_STATION)
      Toast.makeText(context, context.getString(R.string.posto_nao_encontrado), Toast.LENGTH_SHORT)
        .show()
    }
  }

  if (gasStationState.value == null || errorState.value) return

  val gasStation = gasStationState.value!!
  Log.d("GasStationView", gasStation.toString())

  val name = gasStation.name ?: ""
  val gas = gasStation.fuels[0].price.toString() ?: ""
  val ethanol = gasStation.fuels[1].price.toString() ?: ""
  val createdAt = gasStation.createdAt ?: ""

  fun editGasStation() {
    navController.navigate(Routes.GAS_STATION_FORM + "/$id")
  }

  fun deleteGasStation() {
    if (id == null) return
    navController.navigate(Routes.GAS_STATION)
    gasStationRepository.delete(id)
  }

  Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
    Column(
      modifier = Modifier.wrapContentSize(Alignment.Center).padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(name)
      Text(gas)
      Text(ethanol)
      Text(createdAt)
      Button(onClick = { editGasStation() }) {
        Text(context.getString(R.string.edicao))
      }
      Button(onClick = { deleteGasStation() }) {
        Text(context.getString(R.string.exclusao))
      }
    }
  }
}
