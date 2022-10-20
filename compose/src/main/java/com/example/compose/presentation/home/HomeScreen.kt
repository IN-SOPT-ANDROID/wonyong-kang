package com.example.compose.presentation.home

import androidx.compose.runtime.Composable
import com.example.compose.navigation.MainContentNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@MainContentNavGraph(start = true)
@Destination("home")
@Composable
fun HomeScreen() {
}
