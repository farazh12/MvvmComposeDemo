package com.android.mvvmcomposetest.ui.activities.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mvvmcomposetest.data.local.entities.User
import com.android.mvvmcomposetest.data.network.models.Drug
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

    private val _medicines = MutableStateFlow<List<Drug>>(emptyList())
    val medicines: StateFlow<List<Drug>> = _medicines

    fun fetchMedicines() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                networkRepository.fetchAllMedicines().collect { apiResponse ->
                    Log.d("TAG_API_RESPONSE", "fetchMedicines: $apiResponse \n")
                    val medicines = arrayListOf<Drug>()
                    if (apiResponse.problems.isNotEmpty()) {
                        apiResponse.problems.forEach { diabetes ->
                            diabetes.medications.forEach { drug ->
                                medicines.add(drug)
                            }
                        }
                        _medicines.value = medicines
                    }

                }
            }
        }
    }

    fun getDrugs(): StateFlow<List<Drug>> {
        return _medicines
    }

    fun findMedicine(medicineName: String): Drug? {
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