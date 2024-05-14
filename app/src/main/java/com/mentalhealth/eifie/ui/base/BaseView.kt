package com.mentalhealth.eifie.ui.base

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mentalhealth.eifie.ui.navigation.HomeNavigation
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.DarkGreen
import com.mentalhealth.eifie.ui.theme.LightGray
import com.mentalhealth.eifie.ui.theme.LightSkyGray
import com.mentalhealth.eifie.ui.theme.Purple
import com.mentalhealth.eifie.ui.theme.WhitePink
import com.mentalhealth.eifie.util.getActivity

@Composable
fun BaseView(
    mainNavController: NavHostController,
    viewModel: BaseViewModel = hiltViewModel<BaseViewModel>()
) {

    val navController = rememberNavController()
    val selectedItem by viewModel.item.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = CustomWhite,
        bottomBar = {
            NavigationBar(
                tonalElevation = 10.dp,
                containerColor = Color.White,
                modifier = Modifier.shadow(elevation = 30.dp)
            ) {
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
                            selectedIconColor = DarkGreen,
                            unselectedIconColor = LightSkyGray
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