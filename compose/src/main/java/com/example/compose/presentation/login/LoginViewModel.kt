package com.example.compose.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.datasource.local.UserDataSource
import com.example.data.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataSource: UserDataSource
) : ViewModel() {
    private val _userInfo: MutableStateFlow<User?> = MutableStateFlow(null)
    val userInfo = _userInfo.asStateFlow()

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    init {
        viewModelScope.launch {
            if (userDataSource.isAutoLogin()) {
                _loginUiState.value = _loginUiState.value.copy(moveToMain = true)
                return@launch
            }
            _userInfo.value = userDataSource.getUserInfo()
        }
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.IsLogin -> {
                if (userInfo.value != null && loginUiState.value.id == userInfo.value?.id && loginUiState.value.pw == userInfo.value?.pw) {
                    viewModelScope.launch {
                        userDataSource.setAutoLogin(true)
                        userDataSource.setUserInfo(userInfo.value!!)
                        _loginUiState.value = _loginUiState.value.copy(moveToMain = true)
                    }
                }
            }
            is LoginEvent.WriteId -> _loginUiState.value = _loginUiState.value.copy(id = event.id)
            is LoginEvent.WritePw -> _loginUiState.value = _loginUiState.value.copy(pw = event.pw)
        }
    }
}

data class LoginUiState(
    val id: String = "",
    val pw: String = "",
    val moveToMain: Boolean = false
)

sealed class LoginEvent {
    object IsLogin : LoginEvent()
    data class WriteId(val id: String) : LoginEvent()
    data class WritePw(val pw: String) : LoginEvent()
}
