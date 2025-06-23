package com.example.gasoralchool.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gasoralchool.R
import com.example.gasoralchool.models.gasStation.Fuel
import com.example.gasoralchool.models.gasStation.GasStation
import com.example.gasoralchool.models.gasStation.GasStationRepository
import com.example.gasoralchool.models.userPreferences.UserPreferences
import com.example.gasoralchool.models.userPreferences.UserPreferencesRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EthanolOrGas(navController: NavHostController, id: String?) {
  val context = LocalContext.current
  val gasStationRepository = GasStationRepository(context)
  val gasStation = id?.let { gasStationRepository.read(it) }

  val userPreferences = UserPreferencesRepository(context)

  val initialName = gasStation?.name ?: ""
  val initialGas = gasStation?.fuels?.get(0)?.price?.toString() ?: ""
  val initialEthanol = gasStation?.fuels?.get(1)?.price?.toString() ?: ""

  var name by remember { mutableStateOf(initialName) }
  var ethanol by remember { mutableStateOf(initialEthanol) }
  var gas by remember { mutableStateOf(initialGas) }
  var checkedState by remember { mutableStateOf(userPreferences.read().carEfficiencyIs75) }

  fun saveGasStation() {
    val newGasStation = GasStation(
      name = name,
      fuels = listOf(
        Fuel(name = "gas", price = gas.toDouble()),
        Fuel(name = "ethanol", price = ethanol.toDouble())
      )
    )
    gasStationRepository.save(newGasStation)
  }

  fun editGasStation() {
    if (gasStation == null || id == null) return
    val editedGasStation = gasStation.copy(
      name = name,
      fuels = listOf(
        Fuel(name = "gas", price = gas.toDouble()),
        Fuel(name = "ethanol", price = ethanol.toDouble())
      )
    )
    gasStationRepository.edit(id, editedGasStation)
  }

  fun mutateGasStationAndNavigate() {
    if (id == null) {
      saveGasStation()
    } else {
      editGasStation()
    }
    navController.popBackStack()
  }

  val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
    focusedBorderColor = MaterialTheme.colorScheme.onSurface,                  // Borda ao focar
    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 1f), // Borda sem foco - MAIS ESCURA
    focusedLabelColor = MaterialTheme.colorScheme.onSurface,
    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
    cursorColor = MaterialTheme.colorScheme.onSurface
  )

  Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
    Column(
      modifier = Modifier
        .wrapContentSize(Alignment.Center)
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      OutlinedTextField(
        value = ethanol,
        onValueChange = { ethanol = it },
        label = { Text("Preço do Álcool (R$)") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = textFieldColors
      )

      OutlinedTextField(
        value = gas,
        onValueChange = { gas = it },
        label = { Text("Preço da Gasolina (R$)") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = textFieldColors
      )

      OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        label = { Text("Nome do Posto (Opcional)") },
        modifier = Modifier.fillMaxWidth(),
        colors = textFieldColors
      )

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        horizontalArrangement = Arrangement.Start,
      ) {
        Text(
          text = "75%",
          style = MaterialTheme.typography.bodyLarge,
          modifier = Modifier.padding(top = 16.dp),
        )
        Switch(
          modifier = Modifier.semantics { contentDescription = "Demo with icon" },
          checked = checkedState,
          onCheckedChange = {
            checkedState = it
            userPreferences.save(UserPreferences(checkedState))
          },
          thumbContent = {
            if (checkedState) {
              Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize),
              )
            }
          },
        )
      }

      Button(
        onClick = { mutateGasStationAndNavigate() },
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(context.getString(R.string.calculo))
      }

      Text(
        text = "Vamos Calcular?",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(top = 16.dp),
      )
    }
  }
}
