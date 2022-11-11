package com.example.compose.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.compose.component.BottomNavItem
import com.example.compose.presentation.NavGraphs
import com.example.compose.ui.theme.INSOPTAndroidPracticeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination

@Destination(route = "main")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavItem(navController = navController) }
    ) {
        DestinationsNavHost(
            modifier = Modifier.padding(it),
            navGraph = NavGraphs.mainContent,
            navController = navController
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    INSOPTAndroidPracticeTheme {
        MainScreen()
    }
}
