package com.ramesh.medicareplus.core.utils


sealed class Routes(val route: String) {

    data object Splash : Routes("splash")

    data object Onboarding : Routes("onboarding")

    data object Login : Routes("login")

    data object Home : Routes("home")

    data object MedicineList : Routes("medicine_list")

    data object AddMedicine : Routes("add_medicine")

    data object MedicineDetails : Routes("medicine_details/{medicineId}") {
        fun createRoute(medicineId: Long) =
            "medicine_details/$medicineId"
    }

    data object EditMedicine : Routes("edit_medicine/{medicineId}") {
        fun createRoute(medicineId: Long) =
            "edit_medicine/$medicineId"
    }


    data object Medicine : Routes("medicine")

    data object Chart : Routes("Chart")

    data object Settings : Routes("settings")

}
