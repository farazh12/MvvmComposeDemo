package com.android.mvvmcomposetest.data.network.api

import com.android.mvvmcomposetest.data.network.models.ApiResponse
import okhttp3.Response
import retrofit2.http.GET

interface ApiService {

    @GET("14db8290-75c7-4fcd-a399-fe7b3100b450")
    suspend fun fetchAllMedicines(): ApiResponse
}