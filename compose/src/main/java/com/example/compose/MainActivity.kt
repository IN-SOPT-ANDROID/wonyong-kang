package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.compose.navigation.SoptNavGraph
import com.example.compose.ui.theme.INSOPTAndroidPracticeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            INSOPTAndroidPracticeTheme {
                SoptNavGraph()
            }
        }
    }
}
