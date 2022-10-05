package com.example.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.presentation.login.LoginScreen
import com.example.compose.presentation.main.MainScreen
import com.example.compose.presentation.signup.SignUpScreen

@Composable
fun SoptNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = SoptDestinations.LOGIN
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier
    ) {
        composable(route = SoptDestinations.LOGIN) {
            LoginScreen(
                toSignUp = { navController.navigate(SoptDestinations.SIGN_UP) },
                toMain = { navController.navigate(SoptDestinations.MAIN) }
            )
        }
        composable(route = SoptDestinations.SIGN_UP) {
            SignUpScreen()
        }
        composable(route = SoptDestinations.MAIN) {
            MainScreen()
        }
    }
}

object SoptDestinations {
    const val LOGIN = "login"
    const val SIGN_UP = "signUp"
    const val MAIN = "main"
}
