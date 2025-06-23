package com.example.gasoralchool.models

import android.content.Context
import com.example.gasoralchool.R
import com.google.gson.Gson

interface KeyValueRepository<Model> {
  val key: String

  fun read(): Model

  fun save(objectModel: Model)

  fun saveHelper(context: Context, value: Model, type: Class<Model>) {
    val json = Gson().toJson(value)
    val sharedPreferences =
      context.getSharedPreferences(
        context.getString(R.string.nome_app),
        Context.MODE_PRIVATE,
      )
    val editableSharedPreferences = sharedPreferences.edit()
    editableSharedPreferences.putString(key, json)
    editableSharedPreferences.apply()
  }

  fun readHelper(context: Context, type: Class<Model>): Model? {
    val sharedPreferences =
      context.getSharedPreferences(
        context.getString(R.string.nome_app),
        Context.MODE_PRIVATE,
      )
    val objectJson = sharedPreferences?.getString(key, "") ?: "{}"
    return Gson().fromJson(objectJson, type)
  }
}
