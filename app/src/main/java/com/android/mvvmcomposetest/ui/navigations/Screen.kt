package com.android.mvvmcomposetest.ui.navigations

import com.android.mvvmcomposetest.data.local.entities.Medicine

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home/{username}") {
        fun createRoute(username: String) = "home/${username}"
    }

    object Detail : Screen("detail/{medicine}") {
        fun createRoute(medicine: Medicine) = "detail/${medicine.name}"
    }
}