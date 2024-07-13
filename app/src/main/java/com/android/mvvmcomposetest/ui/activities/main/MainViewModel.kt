package com.android.mvvmcomposetest.ui.activities.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mvvmcomposetest.data.local.entities.Medicine
import com.android.mvvmcomposetest.data.local.entities.User
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

    private val _medicines = MutableStateFlow<List<Medicine>>(emptyList())
    val medicines: StateFlow<List<Medicine>> = _medicines

    init {
        fetchMedicines()
    }

    private fun fetchMedicines() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                networkRepository.fetchAllMedicines().collect {
                    it.data?.let { medicines ->
                        Log.d("TAG_API_RESPONSE", "fetchMedicines: $medicines \n")
                        Log.d("TAG_API_RESPONSE", "fetchMedicines: insert all data to db")
                    }
                }
                // TODO: api call here
                val medicines = getMedicines()
                _medicines.value = medicines
            }
        }
    }

    fun findMedicine(medicineName: String): Medicine? {
        return getMedicines().find {
            it.name == medicineName
        }
    }

    private fun getMedicines(): List<Medicine> {
        return arrayListOf(
            Medicine("A"),
            Medicine("B"),
            Medicine("C"),
            Medicine("E"),
        )

    }

    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.insertUser(user)
        }
    }
}