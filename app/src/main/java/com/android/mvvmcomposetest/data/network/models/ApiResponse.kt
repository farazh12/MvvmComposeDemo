package com.android.mvvmcomposetest.data.network.models

data class ApiResponse(
    val problems: List<Problem> = arrayListOf()
)

data class Problem(
    val diabetes: List<Diabetes> = arrayListOf(), val asthma: List<Any> = arrayListOf()
)

data class Diabetes(
    val medications: List<Medications> =  arrayListOf(), val labs: List<Labs> = arrayListOf()
)

data class Medications(
    val medicationsClasses: List<MedicationsClasses> = arrayListOf()
)

data class MedicationsClasses(
    val className: List<ClassName> = arrayListOf(), val className2: List<ClassName> = arrayListOf()
)

data class ClassName(
    val associatedDrug: List<AssociatedDrug> = arrayListOf(), val associatedDrug2: List<AssociatedDrug> = arrayListOf()
)

data class AssociatedDrug(
    val name: String = "", val dose: String = "", val strength: String = ""
)

data class Labs(
    val missing_field: String =""
)