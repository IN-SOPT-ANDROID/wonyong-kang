package com.example.compose.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.datasource.local.AutoLoginDataSource
import com.example.data.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userDataSource: AutoLoginDataSource
) : ViewModel() {
    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    val signUpUiState = _signUpUiState.asStateFlow()
    private val _isSignUpEvent = MutableSharedFlow<Boolean>()
    val isSignUpEvent = _isSignUpEvent.asSharedFlow()

    fun dispatch(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.IsSignUp -> isSignUp()
            is SignUpEvent.WriteId ->
                _signUpUiState.value = _signUpUiState.value.copy(id = event.id)
            is SignUpEvent.WritePw ->
                _signUpUiState.value = _signUpUiState.value.copy(pw = event.pw)
            is SignUpEvent.WriteMbti ->
                _signUpUiState.value = _signUpUiState.value.copy(mbti = event.mbti)
            is SignUpEvent.MoveToLogin -> {
                val user = User(
                    id = signUpUiState.value.id,
                    pw = signUpUiState.value.pw,
                    mbti = signUpUiState.value.mbti
                )
                userDataSource.setUserInfo(user)
            }
        }
    }

    private fun isSignUp() {
        viewModelScope.launch {
            if (signUpUiState.value.id.length in 6..10 && signUpUiState.value.pw.length in 8..12) {
                _isSignUpEvent.emit(true)
            }
        }
    }
}

data class SignUpUiState(
    val id: String = "",
    val pw: String = "",
    val mbti: String = ""
)

sealed class SignUpEvent {
    object IsSignUp : SignUpEvent()
    object MoveToLogin : SignUpEvent()
    data class WriteId(val id: String) : SignUpEvent()
    data class WritePw(val pw: String) : SignUpEvent()
    data class WriteMbti(val mbti: String) : SignUpEvent()
}
