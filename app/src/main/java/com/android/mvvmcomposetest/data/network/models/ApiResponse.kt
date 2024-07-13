package com.android.mvvmcomposetest.data.network.models

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("problems") val problems: List<Problem> = arrayListOf()
)

data class Problem(
    @SerializedName("medications") val medications: List<Drug> = arrayListOf(),
    @SerializedName("labs") val labs: List<Lab> = arrayListOf()
)

data class Drug(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("dose")
    val dose: String = "",
    @SerializedName("strength")
    val strength: String = ""
)

data class Lab(
    @SerializedName("missing_field")
    val missing_field: String = ""
)