package com.example.compose.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.ui.theme.INSOPTAndroidPracticeTheme

@Composable
fun SoptTextField(
    text: String,
    hint: String,
    writeText: (String) -> Unit
) {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, Color.Black), MaterialTheme.shapes.medium)
            .padding(8.dp),
        value = text,
        maxLines = 1,
        onValueChange = { writeText(it) },
        textStyle = TextStyle(
            color = Color.Black,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        cursorBrush = SolidColor(Color.Black)
    ) { innerTextField ->
        if (text.isEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = hint,
                color = MaterialTheme.colors.onSecondary,
                style = MaterialTheme.typography.body1
            )
        }
        innerTextField()
    }
}

@Preview(showBackground = true)
@Composable
private fun SoptTextFieldPreview() {
    INSOPTAndroidPracticeTheme() {
        SoptTextField(text = "", hint = "hi", writeText = {})
    }
}
