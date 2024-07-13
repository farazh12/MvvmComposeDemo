package com.android.mvvmcomposetest.ui.activities.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mvvmcomposetest.data.local.entities.User
import com.android.mvvmcomposetest.data.network.models.AssociatedDrug
import com.android.mvvmcomposetest.data.repository.LocalRepository
import com.android.mvvmcomposetest.data.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkRepository: NetworkRepository, private val localRepository: LocalRepository
) : ViewModel() {

    private val _medicines = MutableStateFlow<List<AssociatedDrug>>(emptyList())
    val medicines: StateFlow<List<AssociatedDrug>> = _medicines

    fun fetchMedicines() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                networkRepository.fetchAllMedicines().collect { apiResponse ->
                    Log.d("TAG_API_RESPONSE", "fetchMedicines: $apiResponse \n")
                    val medicines = arrayListOf<AssociatedDrug>()
                    if (apiResponse.problems.isNotEmpty()) {
                        apiResponse.problems.forEach { problem ->
                            problem.Diabetes?.forEach { diabetes ->
                                diabetes.medications?.forEach { medication ->
                                    medication.medicationsClasses.forEach { medicationClass ->
                                        medicationClass.className?.forEach { drug ->
                                            drug.associatedDrug?.forEach { associatedDrug ->
                                                medicines.add(associatedDrug)
                                            }
                                            drug.associatedDrug2?.forEach { associatedDrug ->
                                                medicines.add(associatedDrug)
                                            }
                                        }
                                        medicationClass.className2?.forEach { drug ->
                                            drug.associatedDrug?.forEach { associatedDrug ->
                                                medicines.add(associatedDrug)
                                            }
                                            drug.associatedDrug2?.forEach { associatedDrug ->
                                                medicines.add(associatedDrug)
                                            }
                                        }
                                    }
                                }
                            }
                            _medicines.value = medicines
                        }

                    }
                }
            }
        }
    }

    fun getDrugs(): StateFlow<List<AssociatedDrug>> {
        return _medicines
    }

    fun findMedicine(medicineName: String): AssociatedDrug? {
        return _medicines.value.find {
            it.name == medicineName
        }
    }

    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.insertUser(user)
        }
    }
}