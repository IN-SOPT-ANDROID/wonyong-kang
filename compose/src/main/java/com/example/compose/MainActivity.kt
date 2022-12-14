package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.compose.presentation.NavGraphs
import com.example.compose.ui.theme.INSOPTAndroidPracticeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            INSOPTAndroidPracticeTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
