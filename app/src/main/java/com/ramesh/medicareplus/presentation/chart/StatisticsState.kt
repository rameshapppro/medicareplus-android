package com.ramesh.medicareplus.presentation.chart

data class StatisticsState(
    val totalMedicines: Int = 0,
    val completedMedicines: Int = 0,
    val incompleteMedicines: Int = 0,
    val rewards: Int = 0,
    val progressPercentage: Int = 0
)
