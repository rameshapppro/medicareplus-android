package com.ramesh.medicareplus.domain.repository

import com.ramesh.medicareplus.domain.model.Medicine
import kotlinx.coroutines.flow.Flow

interface MedicineRepository {

    fun getAllMedicines(): Flow<List<Medicine>>

    suspend fun getMedicineById(id: String): Medicine?

    suspend fun insertMedicine(medicine: Medicine)

    suspend fun updateMedicine(medicine: Medicine)

    suspend fun deleteMedicine(medicine: Medicine)
}
