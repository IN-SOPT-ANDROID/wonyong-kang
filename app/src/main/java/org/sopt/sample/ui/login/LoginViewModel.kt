package org.sopt.sample.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.datasource.local.UserDataSource
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
    private val userDataSource: UserDataSource
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
                _userInfo.value = userDataSource.getUserInfo()
                _loginEvent.emit(true)
            }
        }
    }

    fun setUserInfo(user: User) {
        _userInfo.value = user
    }

    fun loginOnClick() {
        viewModelScope.launch {
            if (idText.value == userInfo.value?.id && pwText.value == userInfo.value?.pw) {
                userDataSource.setAutoLogin(true)
                userDataSource.setUserInfo(userInfo.value!!)
                _loginEvent.emit(true)
            }
        }
    }
}
