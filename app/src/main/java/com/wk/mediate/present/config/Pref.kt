package com.wk.mediate.present.config

import android.content.Context
import com.wk.mediate.domain.models.LoginEntity
import com.wk.mediate.data.models.LoginResult
import com.wk.mediate.present.extension.toJson
import com.wk.mediate.present.extension.toJsonModel

data class Info (
    var profile_url: String = "",   // ": "....",
    var nickname: String  = "",      // ": ASDF1234",
    var description: String = ""     // ": "자기소개",
)

object Pref {

    enum class Key {
        Info,
        Auth,
        FcmToken
    }

    const val NAME = "Pref"

    var fcmToken: String
        get() = get(Key.FcmToken.name, "")
        set(value) = set(value, Key.FcmToken.name)

    var auth: LoginEntity? = null
        get() {
            if (field == null) {
                val json = get(Key.Auth.name, def = "")
                if (json.isNotEmpty()) {
                    field = try {
                        json.toJsonModel(LoginEntity::class.java)
                    } catch (e: Exception) {
                        null
                    }
                }
            }
            return field
        }
        set(value) {
            field = value
            if (value == null) {
                set("", Key.Auth.name)
            } else {
                val json = value.toJson()
                set(json, Key.Auth.name)
            }
        }

    var info: Info? = null
        get() {
            if (field == null) {
                val json = get(Key.Info.name, def = "")
                if (json.isNotEmpty()) {
                    field = try {
                        json.toJsonModel(Info::class.java)
                    } catch (e: Exception) {
                        null
                    }
                }
            }
            return field
        }
        set(value) {
            field = value
            if (value == null) {
                set("", Key.Info.name)
            } else {
                val json = value.toJson()
                set(json, Key.Info.name)
            }
        }

    inline fun <reified T> set(value: T, key: String) {
        set(value, key, NAME)
    }

    inline fun <reified T> set(value: T, key: String, name: String) {
        setCache(value, key)
        val edit = Common.appContext.getSharedPreferences(name, Context.MODE_PRIVATE).edit()
        when (value) {
            is String  -> {
                edit.putString(key, value)
            }
            is Long    -> {
                edit.putLong(key, value)
            }
            is Int     -> {
                edit.putInt(key, value)
            }
            is Float   -> {
                edit.putFloat(key, value)
            }
            is Boolean -> {
                edit.putBoolean(key, value)
            }
        }
        edit.apply()
    }

    private fun clearPref(name: String = NAME) {
        val edit = Common.appContext.getSharedPreferences(name, Context.MODE_PRIVATE).edit()
        edit.clear()
        edit.apply()
    }

    fun resetPref(name: String = NAME) {
        clearPref(name)
        cacheMap.clear()
    }

    inline fun <reified T> get(key: String, def: T): T {
        return get(key, NAME, def)
    }

    inline fun <reified T> get(key: String, name: String, def: T): T {
        val cache = getCache(key) as? T
        if (cache != null) {
            return cache
        }

        val pref = Common.appContext.getSharedPreferences(name, Context.MODE_PRIVATE)
        var value: T? = null
        when (def) {
            is String  -> {
                value = pref.getString(key, def) as T
            }
            is Long    -> {
                value = pref.getLong(key, def) as T
            }
            is Int     -> {
                value = pref.getInt(key, def) as T
            }
            is Float   -> {
                value = pref.getFloat(key, def) as T
            }
            is Boolean -> {
                value = pref.getBoolean(key, def) as T
            }
        }
        val result = value ?: def
        setCache(result, key)
        return result
    }

    private val cacheMap = mutableMapOf<String, Any?>()
    fun getCache(key: String): Any? {
        return cacheMap[key]
    }

    fun setCache(value: Any?, key: String) {
        cacheMap[key] = value
    }
}
