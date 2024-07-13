package com.android.mvvmcomposetest.data.network.api

import com.android.mvvmcomposetest.data.network.models.ApiResponse
import retrofit2.http.GET

interface ApiService {
//    https://run.mocky.io/v3/19e0e83b-2a65-41b5-aeb6-33cc116eb632
//    @GET("19e0e83b-2a65-41b5-aeb6-33cc116eb632") //working for modified json
    @GET("b5976512-a2d7-4c76-8a2a-98f1671421ac\t")
    suspend fun fetchAllMedicines(): ApiResponse
}