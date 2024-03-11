package com.mentalhealth.eifie.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mentalhealth.eifie.ui.navigation.HomeNavigation
import com.mentalhealth.eifie.ui.theme.LightGray
import com.mentalhealth.eifie.ui.theme.Purple
import com.mentalhealth.eifie.ui.theme.WhitePink

@Composable
fun MainHome(
    mainNavController: NavHostController,
    viewModel: MainViewModel = hiltViewModel<MainViewModel>()
) {

    val navController = rememberNavController()
    val selectedItem by viewModel.item.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = WhitePink,
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
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Purple,
                            unselectedIconColor = LightGray
                        ),
                        icon = { Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        ) }
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