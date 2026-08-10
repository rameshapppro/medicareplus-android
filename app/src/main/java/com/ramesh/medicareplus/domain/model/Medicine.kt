package com.ramesh.medicareplus.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey
    val id: String,
    val name: String,
    val dosage: String, // Combined display string
    val strength: String = "",
    val amount: String = "",
    val medicineType: String = "",
    val time: String,
    val isTaken: Boolean = false,
    val instruction: String = "After Food",
    val startDate: Long? = null,
    val endDate: Long? = null,
    val selectedDays: String = "" // Comma separated days
)
