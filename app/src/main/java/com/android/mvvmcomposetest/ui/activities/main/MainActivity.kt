package com.android.mvvmcomposetest.ui.activities.main

import LoginForm
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.mvvmcomposetest.data.local.entities.User
import com.android.mvvmcomposetest.ui.navigations.Screen
import com.android.mvvmcomposetest.ui.screens.HomeScreen
import com.android.mvvmcomposetest.ui.screens.MedicineDetailScreen
import com.android.mvvmcomposetest.ui.theme.MvvmComposeTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    var topBarTitle by mutableStateOf("Login")

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MvvmComposeTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(title = { Text(text = topBarTitle) })
                }) { innerPadding ->
                    val navController = rememberNavController()
                    AppNavGraph(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Screen.Login.route
                    )
                }
            }
        }
        viewModel.fetchMedicines()
    }

    @Composable
    fun AppNavGraph(
        modifier: Modifier = Modifier, navController: NavHostController, startDestination: String
    ) {
        NavHost(navController = navController, startDestination = startDestination) {
            composable(Screen.Login.route) {
                topBarTitle = Screen.Login.title
                Box(modifier = modifier) {
                    LoginForm(modifier = Modifier) { username, password ->
                        viewModel.insertUser(User(username = username, password = password))
                        navController.navigate(Screen.Home.createRoute(username))
                    }
                }
            }
            composable(Screen.Home.route) { backStackEntry ->
                topBarTitle = Screen.Home.title
                val username = backStackEntry.arguments?.getString("username") ?: ""
                // Assume getMedicines() fetches the medicines.json from a JSON or mock API
                Box(modifier = modifier) {
                    HomeScreen(
                        modifier = Modifier, username = username, viewModel = viewModel
                    ) { medicine ->
                        navController.navigate(Screen.Detail.createRoute(medicine))
                    }
                }
            }
            composable(Screen.Detail.route) { backStackEntry ->
                topBarTitle = Screen.Detail.title
                val medicineName = backStackEntry.arguments?.getString("medicine") ?: ""
                // Assume findMedicine() finds a medicine by name from the list
                Box(modifier = modifier) {
                    MedicineDetailScreen(
                        modifier = Modifier, medicineName = medicineName, viewModel = viewModel
                    )
                }
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