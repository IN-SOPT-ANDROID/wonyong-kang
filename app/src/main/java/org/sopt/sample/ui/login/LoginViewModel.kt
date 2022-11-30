package org.sopt.sample.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _loginEvent = MutableSharedFlow<Boolean>()
    val loginEvent = _loginEvent.asSharedFlow()
    val idText = MutableStateFlow("")
    val pwText = MutableStateFlow("")

    val isIdValid = idText.map { idText ->
        idText.matches("^(?=.*[a-zA-Z])(?=.*[0-9]).{6,10}\$".toRegex()) || idText.isEmpty()
    }.stateIn(
        started = SharingStarted.WhileSubscribed(5000L),
        scope = viewModelScope,
        initialValue = false
    )
    val isPwValid = pwText.map { pwText ->
        pwText.matches("^(?=.*[a-zA-Z])(?=.*[0-9]).{6,10}\$".toRegex()) || pwText.isEmpty()
    }.stateIn(
        started = SharingStarted.WhileSubscribed(5000L),
        scope = viewModelScope,
        initialValue = false
    )
    val isSignUp = combine(isIdValid, isPwValid, idText, pwText) { isIdValid, isPwValid, id, pw ->
        isIdValid && isPwValid && id.isNotEmpty() && pw.isNotEmpty()
    }.stateIn(
        started = SharingStarted.WhileSubscribed(5000L),
        scope = viewModelScope,
        initialValue = false
    )

    fun isAutoLogin() {
        viewModelScope.launch {
            delay(10)
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
                .onFailure {
                    _loginEvent.emit(false)
                    Log.e(TAG, it.message.toString())
                }
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}
