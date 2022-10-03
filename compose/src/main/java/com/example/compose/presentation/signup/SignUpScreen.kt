package com.example.compose.presentation.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.component.SoptButton
import com.example.compose.component.SoptTextField
import com.example.compose.ui.theme.INSOPTAndroidPracticeTheme

@Composable
fun SignUpScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "SIGN UP",
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "ID",
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.height(16.dp))

        SoptTextField(text = "", hint = "아이디를 입력하세요", writeText = {})
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "PW",
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.height(16.dp))

        SoptTextField(text = "", hint = "비밀번호를 입력하세요", writeText = {})
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "MBTI",
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.height(12.dp))

        SoptTextField(text = "", hint = "MBTI를 입력하세요", writeText = {})
        Spacer(modifier = Modifier.height(36.dp))

        SoptButton(buttonText = "회원가입 완료") {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    INSOPTAndroidPracticeTheme {
        SignUpScreen()
    }
}
