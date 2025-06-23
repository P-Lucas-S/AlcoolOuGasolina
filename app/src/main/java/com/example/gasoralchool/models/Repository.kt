package com.example.gasoralchool.models

import android.content.Context
import com.example.gasoralchool.R
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

interface Repository<Model> {
  val key: String

  fun readAll(): List<Model>

  fun read(id: String): Model

  fun save(objectModel: Model)

  fun edit(id: String, objectModel: Model)

  fun delete(id: String)

  fun saveHelper(context: Context, allValues: List<Model>, type: Class<Model>) {
    val json = Gson().toJson(allValues)
    val sharedPreferences =
      context.getSharedPreferences(
        context.getString(R.string.nome_app),
        Context.MODE_PRIVATE,
      )
    val editableSharedPreferences = sharedPreferences.edit()
    editableSharedPreferences.putString(key, json).toString()
    editableSharedPreferences.apply()
  }

  fun readHelper(context: Context, type: Class<Model>): List<Model> {
    val sharedPreferences =
      context.getSharedPreferences(
        context.getString(R.string.nome_app),
        Context.MODE_PRIVATE,
      )
    val objectJson = sharedPreferences?.getString(key, "") ?: "[]"
    if (objectJson.isEmpty()) return emptyList()

    val typeToken = TypeToken.getParameterized(List::class.java, type).type
    return try {
      Gson().fromJson(objectJson, typeToken)
    } catch (err: JsonSyntaxException) {
      emptyList()
    }
  }
}
