package com.example.data.datasource.local

import android.content.Context
import com.example.data.entity.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
        val userToJson = Json.encodeToString(user)
        preferences.edit()
            .putString(USER_INFO, userToJson)
            .apply()
    }

    fun getUserInfo(): User {
        val user = preferences.getString(USER_INFO, EMPTY_USER).let { user ->
            Json.decodeFromString<User>(user!!)
        }
        return user
    }

    companion object {
        const val STORAGE_KEY = "STORAGE_KEY"
        const val AUTO_LOGIN = "AutoLogin"
        const val USER_INFO = "USER_INFO"
        private val userEmpty = User("", "", "")
        val EMPTY_USER = Json.encodeToString(userEmpty)
    }
}
