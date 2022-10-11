package com.example.data.datasource.local

import android.content.Context
import com.example.data.entity.User
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val preferences = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
    fun isAutoLogin(): Boolean =
        preferences.getBoolean(AUTO_LOGIN, false)

    fun setAutoLogin(isAutoLogin: Boolean) {
        preferences.edit()
            .putBoolean(AUTO_LOGIN, isAutoLogin)
            .apply()
    }

    fun setUserInfo(user: User) {
        val userToJson = Gson().toJson(user)
        preferences.edit()
            .putString(USER_INFO, userToJson)
            .apply()
    }

    fun getUserInfo(): User {
        val user = preferences.getString(USER_INFO, "")
        return Gson().fromJson(user, User::class.java)
    }

    companion object {
        const val STORAGE_KEY = "STORAGE_KEY"
        const val AUTO_LOGIN = "AutoLogin"
        const val USER_INFO = "USER_INFO"
    }
}
