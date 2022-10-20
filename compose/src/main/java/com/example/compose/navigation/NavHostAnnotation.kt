package com.example.compose.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@NavGraph
annotation class AuthNavGraph(
    val start: Boolean = false
)

@RootNavGraph
@NavGraph
annotation class MainContentNavGraph(
    val start: Boolean = false
)
