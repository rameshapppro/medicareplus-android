package com.ramesh.medicareplus.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramesh.medicareplus.domain.model.Medicine
import com.ramesh.medicareplus.domain.repository.MedicineRepository
import com.ramesh.medicareplus.presentation.chart.StatisticsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val repository: MedicineRepository
) : ViewModel() {

    val allMedicines: StateFlow<List<Medicine>> = repository.getAllMedicines()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val statisticsState: StateFlow<StatisticsState> = allMedicines.map { medicines ->
        val total = medicines.size
        val completed = medicines.count { it.isTaken }
        val incomplete = total - completed
        val percentage = if (total > 0) (completed.toFloat() / total * 100).toInt() else 0
        val rewards = completed / 2 // Mock logic

        StatisticsState(
            totalMedicines = total,
            completedMedicines = completed,
            incompleteMedicines = incomplete,
            rewards = rewards,
            progressPercentage = percentage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatisticsState()
    )

    fun addMedicine(medicine: Medicine) {
        viewModelScope.launch {
            repository.insertMedicine(medicine)
        }
    }

    fun updateMedicine(medicine: Medicine) {
        viewModelScope.launch {
            repository.updateMedicine(medicine)
        }
    }

    fun deleteMedicine(medicine: Medicine) {
        viewModelScope.launch {
            repository.deleteMedicine(medicine)
        }
    }

    fun toggleMedicineTaken(medicine: Medicine) {
        viewModelScope.launch {
            repository.updateMedicine(medicine.copy(isTaken = !medicine.isTaken))
        }
    }

    suspend fun getMedicineById(id: String): Medicine? {
        return repository.getMedicineById(id)
    }
}
