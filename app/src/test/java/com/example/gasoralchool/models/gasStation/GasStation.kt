package com.example.gasoralchool.models.gasStation

import org.junit.Assert.assertEquals
import org.junit.Test

class GasStationTest {

  @Test
  fun shouldReturnEthanolWhenProportionIsLower75AndTheCarProfileIs75() {
    val gas = Fuel(name = "gas", price = 6.5)
    val ethanol = Fuel(name = "ethanol", price = 4.81)
    val gasStation =
      GasStation(
        name = null,
        fuels = listOf(gas, ethanol),
        coordinates = Coordinates(lat = -3.7528539, long = -38.5578778),
      )
    assertEquals(ethanol, gasStation.whichFuelIsBetterOption(true))
  }

  @Test
  fun shouldReturnGasWhenProportionIsBigger75AndTheCarProfileIs75() {
    val gas = Fuel(name = "gas", price = 6.5)
    val ethanol = Fuel(name = "ethanol", price = 5.0)
    val gasStation =
      GasStation(
        name = null,
        fuels = listOf(gas, ethanol),
        coordinates = Coordinates(lat = -3.7528539, long = -38.5578778),
      )
    assertEquals(gas, gasStation.whichFuelIsBetterOption(true))
  }
}
