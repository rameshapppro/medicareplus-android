package com.ramesh.medicareplus.presentation.navigation

import com.ramesh.medicareplus.core.utils.Routes

sealed class BottomNavItem(val title: String, val route: String) {
    data object Home : BottomNavItem("Home", Routes.Home.route)
    data object Medicine : BottomNavItem("Medicine", Routes.Medicine.route)
    data object Chart : BottomNavItem("Chart", Routes.Chart.route)
    data object Profile : BottomNavItem("Profile", Routes.Settings.route)
}
