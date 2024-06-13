package com.mentalhealth.eifie.ui.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mentalhealth.eifie.ui.viewmodel.ParkViewModel
import com.mentalhealth.eifie.ui.navigation.HomeNavigation
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.DarkGreen
import com.mentalhealth.eifie.ui.theme.LightSkyGray

@Composable
fun ParkScreen(
    mainNavController: NavHostController?,
    viewModel: ParkViewModel?
) {

    val navController = rememberNavController()
    val selectedItem = viewModel?.item?.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = CustomWhite,
        bottomBar = {
            NavigationBar(
                tonalElevation = 10.dp,
                containerColor = Color.White,
                modifier = Modifier.shadow(elevation = 30.dp)
            ) {
                viewModel?.navigationItems?.value?.forEach { item ->
                    NavigationBarItem(
                        selected = selectedItem?.value?.ordinal == item.ordinal,
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

@Preview
@Composable
fun BasePreview() {
    ParkScreen(mainNavController = null, viewModel = null)
}