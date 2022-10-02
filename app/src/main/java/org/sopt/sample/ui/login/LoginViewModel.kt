package org.sopt.sample.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel : ViewModel() {
    val idText = MutableStateFlow("")
    val pwText = MutableStateFlow("")
    val mbtiText = MutableStateFlow("")
}
