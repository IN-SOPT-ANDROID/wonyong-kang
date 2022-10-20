package com.example.compose.component

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.compose.navigation.destination.BottomBarDestination
import com.example.compose.presentation.NavGraphs
import com.example.compose.presentation.appCurrentDestinationAsState
import com.example.compose.presentation.startAppDestination
import com.ramcosta.composedestinations.navigation.navigate

@Composable
fun BottomNavItem(
    navController: NavController
) {
    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.mainContent.startAppDestination
    BottomNavigation(
        contentColor = Color.White,
        backgroundColor = Color.Black
    ) {
        BottomBarDestination.values().forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigate(destination.direction) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        destination.icon,
                        contentDescription = stringResource(destination.label)
                    )
                },
                label = { Text(stringResource(destination.label)) }
            )
        }
    }
}
