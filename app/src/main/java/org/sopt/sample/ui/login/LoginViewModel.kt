package org.sopt.sample.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.datasource.local.AutoLoginDataSource
import com.example.data.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataSource: AutoLoginDataSource
) : ViewModel() {
    private val _userInfo: MutableStateFlow<User?> = MutableStateFlow(null)
    val userInfo = _userInfo.asStateFlow()
    private val _loginEvent = MutableSharedFlow<Boolean>()
    val loginEvent = _loginEvent.asSharedFlow()
    val idText = MutableStateFlow("")
    val pwText = MutableStateFlow("")

    init {
        viewModelScope.launch {
            if (userDataSource.isAutoLogin()) {
                delay(10)
                _loginEvent.emit(true)
            }
        }
    }

    fun loginOnClick() {
        viewModelScope.launch {
            if (userInfo.value != null && idText.value == userInfo.value?.id && pwText.value == userInfo.value?.pw) {
                userDataSource.setAutoLogin(true)
                userDataSource.setUserInfo(userInfo.value!!)
                _loginEvent.emit(true)
            }
        }
    }

    fun getUserInfo() {
        _userInfo.value = userDataSource.getUserInfo()
    }
}
