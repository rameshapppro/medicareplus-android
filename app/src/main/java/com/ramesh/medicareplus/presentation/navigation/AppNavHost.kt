package com.ramesh.medicareplus.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramesh.medicareplus.core.utils.Routes
import com.ramesh.medicareplus.presentation.addmedicine.AddMedicineScreen
import com.ramesh.medicareplus.presentation.chart.StatisticsScreen
import com.ramesh.medicareplus.presentation.home.HomeScreen
import com.ramesh.medicareplus.presentation.madicinelist.MedicineListScreen
import com.ramesh.medicareplus.presentation.settings.ProfileScreen
import com.ramesh.medicareplus.presentation.splash.SplashScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
fun AppNavHost(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    // Determine which item is selected based on current route
    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }
    // Sync selected item with navigation state
    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            Routes.Home.route -> selectedItem = BottomNavItem.Home
            Routes.Medicine.route -> selectedItem = BottomNavItem.Medicine
            Routes.Chart.route -> selectedItem = BottomNavItem.Chart
            Routes.Settings.route -> selectedItem = BottomNavItem.Profile
        }
    }
    // Only show BottomBar on specific screens
    val showBottomBar = currentRoute in listOf(
        Routes.Home.route,
        Routes.Medicine.route,
        Routes.Chart.route,
        Routes.Settings.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    selectedItem = selectedItem,
                    onItemSelected = { item ->
                        selectedItem = item
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { scaffoldPadding ->
        Box(modifier = Modifier.padding(scaffoldPadding)) {
            NavHost(
                navController = navController,
                startDestination = Routes.Splash.route
            ) {
                composable(Routes.Splash.route) {
                    SplashScreen(
                        onFinished = {
//                            navController.navigate(Routes.Home.route) {
//                                popUpTo(Routes.Splash.route) {
//                                    inclusive = true
//                                }
//                            }
                        }
                    )
                }

                composable(Routes.Home.route) {
                    HomeScreen(
                        onMenuClick = {
                            navController.navigate(Routes.AddMedicineSchedule.route)
                        }
                    )
                }
                composable(Routes.Medicine.route) {
                    MedicineListScreen(
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onEditClick = { medicine ->
                            navController.navigate(Routes.EditMedicine.createRoute(medicine.id))
                        }
                    )
                }
                composable(Routes.Chart.route) {
                    StatisticsScreen(
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
                composable(Routes.Settings.route) {
                    ProfileScreen(
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
                composable(Routes.AddMedicineSchedule.route) {
                    AddMedicineScreen(
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onMakeSchedule = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(
                    route = Routes.EditMedicine.route,
                    arguments = listOf(navArgument("medicineId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val medicineId = backStackEntry.arguments?.getString("medicineId")
                    AddMedicineScreen(
                        medicineId = medicineId,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onMakeSchedule = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
