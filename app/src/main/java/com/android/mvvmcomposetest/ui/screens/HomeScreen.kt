package com.android.mvvmcomposetest.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.mvvmcomposetest.data.network.models.AssociatedDrug
import com.android.mvvmcomposetest.data.network.models.Drug
import com.android.mvvmcomposetest.ui.activities.main.MainViewModel
import com.android.mvvmcomposetest.ui.screens.list_items.MedicineCard
import java.time.LocalTime

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    username: String,
    viewModel: MainViewModel = hiltViewModel(),
    onMedicineClick: (AssociatedDrug) -> Unit
) {
    val medicines by viewModel.medicines.collectAsState()
    val greeting = when (LocalTime.now().hour) {
        in 5..11 -> "Good Morning"
        in 12..17 -> "Good Afternoon"
        else -> "Good Evening"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "$greeting, $username!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = modifier.height(16.dp))
        LazyColumn {
            items(medicines) { medicine ->
                MedicineCard(medicine = medicine, onClick = { onMedicineClick(medicine) })
            }
        }
    }
}