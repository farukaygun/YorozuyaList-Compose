package com.farukaygun.yorozuyalist.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

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

	fun getBool(key: String) = sharedPreferences?.getBoolean(key, false) == true

	fun saveInt(key: String, int: Int) {
		sharedPreferences?.edit(commit = true) {
			putInt(key, int)
		}
	}

	fun getInt(key: String) = sharedPreferences?.getInt(key, 0) ?: 0

	fun saveLong(key: String, long: Long) {
		sharedPreferences?.edit(commit = true) {
			putLong(key, long)
		}
	}

	fun getLong(key: String) = sharedPreferences?.getLong(key, 0) ?: 0

	fun removeKey(key: String) {
		sharedPreferences?.edit(commit = true) {
			remove(key)
		}
	}
}