package com.wk.mediate.present.extension

import com.google.gson.Gson
import java.lang.reflect.Type

var gson = Gson()
fun Any.toJson(): String {

    return gson.toJson(this)
}

fun <T> String.toJsonModel(type: Type): T {
    return gson.fromJson(this, type)
}