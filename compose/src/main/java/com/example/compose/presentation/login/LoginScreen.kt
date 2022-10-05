package com.example.compose.presentation.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.component.SoptButton
import com.example.compose.component.SoptTextField
import com.example.compose.ui.theme.INSOPTAndroidPracticeTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    context: Context = LocalContext.current,
    toSignUp: () -> Unit,
    toMain: () -> Unit
) {
    val uiState by loginViewModel.loginUiState.collectAsStateWithLifecycle()
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(true) {
        loginViewModel.loginEvent.flowWithLifecycle(lifecycleOwner.lifecycle)
            .onEach {
                /* go login */
                Toast.makeText(context, "로그인에 성공했습니다", Toast.LENGTH_SHORT).show()
                toMain()
            }
            .launchIn(lifecycleOwner.lifecycleScope)
//        if (parameter) {
//            scaffoldState.snackbarHostState.showSnackbar("회원가입이 완료되었습니다.")
//        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Welcome to SOPT",
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "ID",
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.height(16.dp))

        SoptTextField(
            text = uiState.id,
            hint = "아이디를 입력하세요",
            writeText = { id -> loginViewModel.onEvent(LoginEvent.WriteId(id)) }
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "PW",
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.height(12.dp))

        SoptTextField(
            text = uiState.pw,
            hint = "비밀번호를 입력하세요",
            writeText = { pw -> loginViewModel.onEvent(LoginEvent.WritePw(pw)) }
        )
        Spacer(modifier = Modifier.height(36.dp))

        SoptButton(buttonText = "LOGIN") {
            loginViewModel.onEvent(LoginEvent.IsLogin)
        }
        Spacer(modifier = Modifier.height(20.dp))

        SoptButton(buttonText = "SIGNUP") { toSignUp() }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    INSOPTAndroidPracticeTheme {
        LoginScreen(toMain = {}, toSignUp = {})
    }
}
