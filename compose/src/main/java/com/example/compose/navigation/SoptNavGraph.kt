package com.example.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.presentation.login.LoginScreen
import com.example.compose.presentation.main.MainScreen
import com.example.compose.presentation.signup.SignUpScreen

@Composable
fun SoptNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "${SoptDestinations.LOGIN}/{isSignUp}"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier
    ) {
        composable(
            route = "${SoptDestinations.LOGIN}/{isSignUp}",
            arguments = listOf(
                navArgument("isSignUp") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->
            LoginScreen(
                isSignUp = backStackEntry.arguments?.getBoolean("isSignUp") ?: false,
                toSignUp = { navController.navigate(SoptDestinations.SIGN_UP) },
                toMain = {
                    navController.navigate(SoptDestinations.MAIN) {
                        popUpTo("${SoptDestinations.LOGIN}/{isSignUp}") {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = SoptDestinations.SIGN_UP) {
            SignUpScreen(toLogin = { isSignUp ->
                navController.navigate("${SoptDestinations.LOGIN}/$isSignUp") {
                    popUpTo("${SoptDestinations.LOGIN}/{isSignUp}") {
                        inclusive = true
                    }
                }
            })
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
