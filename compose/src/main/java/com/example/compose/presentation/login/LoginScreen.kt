package com.example.compose.presentation.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.compose.component.SoptButton
import com.example.compose.component.SoptTextField
import com.example.compose.navigation.AuthNavGraph
import com.example.compose.presentation.destinations.MainScreenDestination
import com.example.compose.presentation.destinations.SignUpScreenDestination
import com.example.compose.ui.theme.INSOPTAndroidPracticeTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AuthNavGraph(start = true)
@Destination(route = "login")
@Composable
fun LoginScreen(
    isSignUp: Boolean = false,
    loginViewModel: LoginViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    context: Context = LocalContext.current,
    focusManager: FocusManager = LocalFocusManager.current,
    navigator: DestinationsNavigator
) {
    val uiState by loginViewModel.loginUiState.collectAsState()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    LaunchedEffect(true) {
        if (isSignUp) {
            scaffoldState.snackbarHostState.showSnackbar(message = "회원가입 성공")
        }
        loginViewModel.isLoginEvent.flowWithLifecycle(lifecycleOwner.lifecycle)
            .onEach {
                Toast.makeText(context, "로그인에 성공했습니다", Toast.LENGTH_SHORT).show()
                navigator.popBackStack()
                navigator.navigate(MainScreenDestination.route)
            }
            .launchIn(lifecycleOwner.lifecycleScope)
    }

    Scaffold(
        modifier = Modifier.padding(horizontal = 16.dp),
        scaffoldState = scaffoldState
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                onTextChange = { id -> loginViewModel.dispatch(LoginEvent.WriteId(id)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
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
                onTextChange = { pw -> loginViewModel.dispatch(LoginEvent.WritePw(pw)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
            Spacer(modifier = Modifier.height(36.dp))

            SoptButton(buttonText = "LOGIN") {
                loginViewModel.dispatch(LoginEvent.IsLogin)
            }
            Spacer(modifier = Modifier.height(20.dp))

            SoptButton(buttonText = "SIGN UP") { navigator.navigate(SignUpScreenDestination) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    INSOPTAndroidPracticeTheme {
        LoginScreen(isSignUp = false, navigator = EmptyDestinationsNavigator)
    }
}
