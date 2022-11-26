package com.example.data.datasource.local

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class AutoLoginDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    var isAutoLogin: Boolean
        set(value) = sharedPreferences.edit { putBoolean(IS_AUTO_LOGIN, value) }
        get() = sharedPreferences.getBoolean(IS_AUTO_LOGIN, false)

    companion object {
        private const val IS_AUTO_LOGIN = "isAutoLogin"
    }
}
