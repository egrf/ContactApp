package com.egrf.contactsapp.domain.utils

import android.content.SharedPreferences
import javax.inject.Inject

import javax.inject.Singleton


@Singleton
class PreferencesHelper @Inject internal constructor(private val preferences: SharedPreferences) {

    fun putString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return preferences.getString(key, "")
    }

}