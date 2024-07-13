package com.android.mvvmcomposetest.ui.screens.list_items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.mvvmcomposetest.data.network.models.AssociatedDrug

@Composable
fun MedicineCard(medicine: AssociatedDrug, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Name: ${medicine.name}")
            Text("Dose: ${medicine.dose}")
            Text("Strength: ${medicine.strength}")
        }
    }
}