package com.android.mvvmcomposetest.ui.activities.main

import LoginForm
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.mvvmcomposetest.ui.navigations.Screen
import com.android.mvvmcomposetest.ui.screens.HomeScreen
import com.android.mvvmcomposetest.ui.screens.MedicineDetailScreen
import com.android.mvvmcomposetest.ui.theme.MvvmComposeTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MvvmComposeTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    AppNavGraph(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Screen.Login.route
                    )
                }
            }
        }
    }

    @Composable
    fun AppNavGraph(
        modifier: Modifier = Modifier, navController: NavHostController, startDestination: String
    ) {
        NavHost(navController = navController, startDestination = startDestination) {
            composable(Screen.Login.route) {
                LoginForm(modifier = modifier) { username ->
                    navController.navigate(Screen.Home.createRoute(username))
                }
            }
            composable(Screen.Home.route) { backStackEntry ->
                val username = backStackEntry.arguments?.getString("username") ?: ""
                // Assume getMedicines() fetches the medicines from a JSON or mock API
                HomeScreen(
                    modifier = modifier, username = username,
                ) { medicine ->
                    navController.navigate(Screen.Detail.createRoute(medicine))
                }
            }
            composable(Screen.Detail.route) { backStackEntry ->
                val medicineName = backStackEntry.arguments?.getString("medicine") ?: ""
                // Assume findMedicine() finds a medicine by name from the list
                MedicineDetailScreen(modifier = modifier, medicineName= medicineName)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MvvmComposeTestTheme {
        LoginForm()
    }
}