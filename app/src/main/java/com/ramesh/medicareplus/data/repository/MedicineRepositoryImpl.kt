package com.ramesh.medicareplus.data.repository

import com.ramesh.medicareplus.data.local.MedicineDao
import com.ramesh.medicareplus.domain.model.Medicine
import com.ramesh.medicareplus.domain.repository.MedicineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MedicineRepositoryImpl @Inject constructor(
    private val dao: MedicineDao
) : MedicineRepository {

    override fun getAllMedicines(): Flow<List<Medicine>> {
        return dao.getAllMedicines()
    }

    override suspend fun getMedicineById(id: String): Medicine? {
        return dao.getMedicineById(id)
    }

    override suspend fun insertMedicine(medicine: Medicine) {
        dao.insertMedicine(medicine)
    }

    override suspend fun updateMedicine(medicine: Medicine) {
        dao.updateMedicine(medicine)
    }

    override suspend fun deleteMedicine(medicine: Medicine) {
        dao.deleteMedicine(medicine)
    }
}
