package com.android.mvvmcomposetest.data.repository

import com.android.mvvmcomposetest.data.network.api.ApiService
import com.android.mvvmcomposetest.data.network.models.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val apiService: ApiService, val isNetworkConnected: Boolean
) {

    private val TAG = "Repository"


    fun fetchAllMedicines(): Flow<ApiResponse<Any>> {
        return if (isNetworkConnected) {
            flow {
                emit(apiService.fetchAllMedicines())
            }
        } else {
            flow {
                val apiResponse = ApiResponse<Any>(
                    code = 401, message = "Unauthorized", data = null
                )
                emit(apiResponse)
            }
        }
    }
}