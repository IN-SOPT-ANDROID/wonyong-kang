package org.sopt.sample.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _signUpEvent = MutableSharedFlow<UiEvent>()
    val signUpEvent = _signUpEvent.asSharedFlow()

    val idText = MutableStateFlow("")
    val pwText = MutableStateFlow("")
    val nameText = MutableStateFlow("")

    val isSignUp = combine(idText, pwText, nameText) { id, pw, name ->
        id.length in 6..10 && pw.length in 8..12 && name.length in 2..8
    }.stateIn(
        started = SharingStarted.WhileSubscribed(5000L),
        scope = viewModelScope,
        initialValue = false
    )

    fun signUpButtonOnClick() {
        viewModelScope.launch {
            authRepository.postSignUp(idText.value, pwText.value, nameText.value)
                .onSuccess { _signUpEvent.emit(UiEvent.Success) }
                .onFailure { _signUpEvent.emit(UiEvent.Fail) }
        }
    }
}

sealed class UiEvent {
    object Fail : UiEvent()
    object Success : UiEvent()
}
