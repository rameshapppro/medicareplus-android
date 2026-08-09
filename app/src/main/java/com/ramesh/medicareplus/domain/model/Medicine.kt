package com.ramesh.medicareplus.domain.model

data class Medicine(
    val id: String,
    val name: String,
    val dosage: String,
    val time: String,
    val isTaken: Boolean = false,
    val instruction: String = "After Meal"
)
