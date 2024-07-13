package com.android.mvvmcomposetest.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("code")
    @Expose
    val code: Int,

    @SerializedName("message")
    @Expose
    val message: String,

    @SerializedName("data")
    @Expose
    val data: T?,
)