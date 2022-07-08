package com.egrf.contactsapp.domain.utils

import android.content.SharedPreferences
import com.egrf.contactsapp.ui.extensions.EMPTY
import javax.inject.Inject

import javax.inject.Singleton


@Singleton
class PreferencesHelper @Inject internal constructor(private val preferences: SharedPreferences) {

    fun putString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? =
        preferences.getString(key, String.EMPTY)

}