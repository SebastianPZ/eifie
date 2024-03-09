package com.mentalhealth.eifie.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mentalhealth.eifie.ui.navigation.HomeNavigation

@Composable
fun MainHome(
    mainNavController: NavHostController,
    viewModel: MainViewModel = hiltViewModel<MainViewModel>()
) {

    val navController = rememberNavController()
    val selectedItem by viewModel.item.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                viewModel.navigationItems.forEach { item ->
                    NavigationBarItem(
                        selected = selectedItem.ordinal == item.ordinal,
                        onClick = {
                            viewModel.updateSelectedItem(item)
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            HomeNavigation(navController = navController, mainNavController = mainNavController)
        }
    }
}