package com.example.gasoralchool.models.gasStation

import android.content.Context
import com.example.gasoralchool.models.Repository

class GasStationRepository(private val context: Context) : Repository<GasStation> {
  override val key: String = "GasStationRepository"

  override fun readAll(): List<GasStation> {
    return this.readHelper(context, GasStation::class.java)
  }

  override fun read(id: String): GasStation {
    val allGasStations = readAll()
    val gasStation = allGasStations.find { gasStationItem -> gasStationItem.id == id }
    if (gasStation == null) {
      throw Exception("Gas station with id='$id' is not found")
    }
    return gasStation
  }

  override fun delete(id: String) {
    val allGasStations = readAll()
    val gasStationsAfterDeleteOne =
      allGasStations.filter { gasStationItem -> gasStationItem.id != id }
    saveHelper(context, gasStationsAfterDeleteOne, GasStation::class.java)
  }

  override fun edit(id: String, objectModel: GasStation) {
    val objectModelCopy = objectModel.copy(id = id, createdAt = objectModel.createdAt)
    delete(id)
    save(objectModelCopy)
  }

  override fun save(objectModel: GasStation) {
    val allValues = readHelper(context, GasStation::class.java).toMutableList()
    allValues.add(objectModel)
    saveHelper(context, allValues, GasStation::class.java)
  }
}
