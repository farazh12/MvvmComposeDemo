package com.android.mvvmcomposetest.data.network.models

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val problems: List<Problem>
)

data class Problem(
    val Diabetes: List<DiabetesItem>? = null, val Asthma: List<AsthmaItem>? = null
)

data class DiabetesItem(
    val medications: List<Medication>?, val labs: List<Lab>?
)

data class Medication(
    val medicationsClasses: List<MedicationClass>
)

data class MedicationClass(
    val className: List<Drug>?, val className2: List<Drug>?
)

data class Drug(
    @SerializedName("associatedDrug") val associatedDrug: List<AssociatedDrug>?,
    @SerializedName("associatedDrug#2") val associatedDrug2: List<AssociatedDrug>?
)

data class AssociatedDrug(
    val name: String, val dose: String, val strength: String
)

data class Lab(
    val missing_field: String
)

data class AsthmaItem(
    val any: Any
)
