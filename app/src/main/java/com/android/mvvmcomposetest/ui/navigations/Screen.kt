package com.android.mvvmcomposetest.ui.navigations

import com.android.mvvmcomposetest.data.network.models.Drug

sealed class Screen(val route: String, val title:String) {
    object Login : Screen("login", "Login")
    object Home : Screen("home/{username}", "Home") {
        fun createRoute(username: String) = "home/${username}"
    }

    object Detail : Screen("detail/{medicine}", "Detail") {
        fun createRoute(medicine: Drug) = "detail/${medicine.name}"
    }
}