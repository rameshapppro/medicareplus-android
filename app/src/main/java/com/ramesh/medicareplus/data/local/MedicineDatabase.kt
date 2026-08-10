package com.ramesh.medicareplus.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ramesh.medicareplus.domain.model.Medicine

@Database(
    entities = [Medicine::class],
    version = 2,
    exportSchema = false
)
abstract class MedicineDatabase : RoomDatabase() {

    abstract val medicineDao: MedicineDao

    companion object {
        const val DATABASE_NAME = "medicine_db"
    }
}
