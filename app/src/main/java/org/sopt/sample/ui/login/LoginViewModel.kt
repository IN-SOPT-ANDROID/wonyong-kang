package org.sopt.sample.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _loginEvent = MutableSharedFlow<Boolean>()
    val loginEvent = _loginEvent.asSharedFlow()
    val idText = MutableStateFlow("")
    val pwText = MutableStateFlow("")

    fun isAutoLogin() {
        viewModelScope.launch {
            if (authRepository.isAutoLogin()) _loginEvent.emit(true)
        }
    }

    fun loginOnClick() {
        viewModelScope.launch {
            authRepository.postSignIn(idText.value, pwText.value)
                .onSuccess {
                    authRepository.setAutoLogin(true)
                    _loginEvent.emit(true)
                }
                .onFailure { Log.e(TAG, it.message.toString()) }
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}
