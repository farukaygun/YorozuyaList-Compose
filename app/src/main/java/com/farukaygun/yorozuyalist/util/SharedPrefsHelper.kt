package com.farukaygun.yorozuyalist.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class SharedPrefsHelper {
    companion object {
        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: SharedPrefsHelper? = null
        private val lock = Any()

        operator fun invoke(context: Context)
                : SharedPrefsHelper = instance ?: synchronized(lock) {
            instance ?: makeSharedPrefsHelper(context).also {
                instance = it
            }
        }

        // TODO: güncelle
        private fun makeSharedPrefsHelper(context: Context): SharedPrefsHelper {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPrefsHelper()
        }
    }

    fun saveString(key: String, string: String) {
        sharedPreferences?.edit(commit = true) {
            putString(key, string)
        }
    }

    fun getString(key: String) = sharedPreferences?.getString(key, "null") ?: "null"

    fun saveBool(key: String, bool: Boolean) {
        sharedPreferences?.edit(commit = true) {
            putBoolean(key, bool)
        }
    }

    fun getBool(key: String) = sharedPreferences?.getBoolean(key, false) ?: false
}