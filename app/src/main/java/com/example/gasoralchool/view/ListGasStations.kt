package com.example.gasoralchool.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gasoralchool.R
import com.example.gasoralchool.models.gasStation.GasStationRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListGasStations(navController: NavHostController) {
  val context = LocalContext.current
  val gasStationRepository = GasStationRepository(context)
  val gasStationsList = gasStationRepository.readAll()
  Scaffold(
    topBar = { TopAppBar(title = { Text(context.getString(R.string.lista_postos)) }) },
    floatingActionButton = {
      FloatingActionButton(onClick = { navController.navigate(Routes.GAS_STATION_FORM) }) {
        Icon(Icons.Filled.Add, context.getString(R.string.add_posto))
      }
    },
  ) { innerPadding ->
    LazyColumn(
      modifier = Modifier.fillMaxSize().padding(innerPadding),
      contentPadding = PaddingValues(16.dp),
    ) {
      items(gasStationsList) { item ->
        Card(
          onClick = {
            val id = item.id
            navController.navigate(Routes.GAS_STATION_INFO + "/$id")
          },
          modifier = Modifier.fillMaxWidth().padding(10.dp),
        ) {
          Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
          ) {
            Text(
              text = item.name ?: context.getString(R.string.sem_nome),
              modifier = Modifier.padding(16.dp),
            )
            Column(
              verticalArrangement = Arrangement.SpaceBetween,
              horizontalAlignment = AbsoluteAlignment.Right,
            ) {
              Row {
                Text(text = item.fuels[0].name, modifier = Modifier.padding(8.dp))
                Text(text = item.fuels[0].price.toString(), modifier = Modifier.padding(8.dp))
              }
              Row {
                Text(text = item.fuels[1].name, modifier = Modifier.padding(8.dp))
                Text(text = item.fuels[1].price.toString(), modifier = Modifier.padding(8.dp))
              }
            }
          }
        }
      }
    }
  }
}
