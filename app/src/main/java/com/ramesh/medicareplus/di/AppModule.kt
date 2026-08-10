package com.ramesh.medicareplus.di

import android.app.Application
import androidx.room.Room
import com.ramesh.medicareplus.data.local.MedicineDao
import com.ramesh.medicareplus.data.local.MedicineDatabase
import com.ramesh.medicareplus.data.repository.MedicineRepositoryImpl
import com.ramesh.medicareplus.domain.repository.MedicineRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMedicineDatabase(app: Application): MedicineDatabase {
        return Room.databaseBuilder(
            app,
            MedicineDatabase::class.java,
            MedicineDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideMedicineDao(db: MedicineDatabase): MedicineDao {
        return db.medicineDao
    }

    @Provides
    @Singleton
    fun provideMedicineRepository(dao: MedicineDao): MedicineRepository {
        return MedicineRepositoryImpl(dao)
    }
}
