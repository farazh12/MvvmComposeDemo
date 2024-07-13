package com.android.mvvmcomposetest.data.repository

import com.android.mvvmcomposetest.data.network.api.ApiService
import com.android.mvvmcomposetest.data.network.models.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val apiService: ApiService, private val isNetworkConnected: Boolean
) {

    fun fetchAllMedicines(): Flow<ApiResponse> {
        return if (isNetworkConnected) {
            flow {
                emit(apiService.fetchAllMedicines())
            }
        } else {
            flow {
                val modifiedApiResponse = ApiResponse(emptyList())
                emit(modifiedApiResponse)
            }
        }
    }
}