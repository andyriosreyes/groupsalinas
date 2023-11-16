package com.andy.rios.elektra.common

import com.google.gson.Gson
import com.google.gson.JsonObject


fun haveError(jsonObject: JsonObject): Boolean = jsonObject.get("error").asBoolean

fun <T> fromJson(jsonObject: JsonObject, classOfT: Class<T>): T =
    fromJson(getResponse(jsonObject), classOfT)

fun <T> fromJson(jsonObject: JsonObject, clazz: Class<Array<T>>): List<T> =
    Gson().fromJson(getResponse(jsonObject), clazz).toList()

fun <T> fromJson(json: String, classOfT: Class<T>): T {
    return try {
        Gson().fromJson(json, classOfT)
    } catch (error: Exception) {
        Gson().fromJson("{}", classOfT)
    }
}

fun getResponse(jsonObject: JsonObject): String {
    val jsonElement = jsonObject.get("response")
    if (jsonElement.isJsonArray || jsonElement.isJsonObject) {
        return jsonElement.toString()
    }
    return jsonElement.asString
}

fun createErrorBody(icon: String, title: String, message: String): String {
    return "{\"error_code\": 999, \"is_error\": true, \"response\":{" + "}}"
}
