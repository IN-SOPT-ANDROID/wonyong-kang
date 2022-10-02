package org.sopt.sample.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val _signUpEvent = MutableSharedFlow<UiEvent>()
    val signUpEvent = _signUpEvent.asSharedFlow()

    val idText = MutableStateFlow("")
    val pwText = MutableStateFlow("")
    val mbtiText = MutableStateFlow("")

    private val isSignUp = combine(idText, pwText) { id, pw ->
        id.length in 7..9 && pw.length in 9..12
    }.stateIn(started = SharingStarted.Eagerly, scope = viewModelScope, initialValue = false)

    fun signUpButtonOnClick() {
        viewModelScope.launch {
            if (!isSignUp.value) {
                _signUpEvent.emit(UiEvent.ShowSnackBar)
            } else {
                _signUpEvent.emit(UiEvent.Success)
            }
        }
    }
}

sealed class UiEvent {
    object ShowSnackBar : UiEvent()
    object Success : UiEvent()
}
