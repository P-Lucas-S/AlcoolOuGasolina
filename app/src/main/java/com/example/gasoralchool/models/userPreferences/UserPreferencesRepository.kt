package com.example.gasoralchool.models.userPreferences

import android.content.Context
import com.example.gasoralchool.models.KeyValueRepository

class UserPreferencesRepository(private val context: Context) :
  KeyValueRepository<UserPreferences> {
  override val key: String = "UserPreferencesRepository"

  override fun read(): UserPreferences {
    val userPreferences = readHelper(context, UserPreferences::class.java)
    if (userPreferences == null) {
      return UserPreferences(carEfficiencyIs75 = true)
    }
    return userPreferences
  }

  override fun save(objectModel: UserPreferences) {
    saveHelper(context, objectModel, UserPreferences::class.java)
  }
}
