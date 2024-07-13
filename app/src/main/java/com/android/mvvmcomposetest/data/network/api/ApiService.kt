package com.android.mvvmcomposetest.data.network.api

import com.android.mvvmcomposetest.data.network.models.ApiResponse
import okhttp3.Response
import retrofit2.http.GET

interface ApiService {
//    https://run.mocky.io/v3/19e0e83b-2a65-41b5-aeb6-33cc116eb632
    @GET("19e0e83b-2a65-41b5-aeb6-33cc116eb632")
    suspend fun fetchAllMedicines(): ApiResponse
}