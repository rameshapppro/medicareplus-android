package com.ramesh.medicareplus.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramesh.medicareplus.core.ui.theme.Primary
import com.ramesh.medicareplus.core.ui.theme.White
import com.ramesh.medicareplus.core.ui.theme.Gray

@Composable
fun BottomBar(
    selectedItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Medicine,
        BottomNavItem.Chart,
        BottomNavItem.Profile
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        color = White,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = selectedItem == item
                
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Primary else Color.Transparent)
                        .clickable { onItemSelected(item) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when (item) {
                            BottomNavItem.Home -> if (isSelected) Icons.Filled.Home else Icons.Outlined.Home
                            BottomNavItem.Medicine -> if (isSelected) Icons.Filled.Medication else Icons.Outlined.Medication
                            BottomNavItem.Chart -> if (isSelected) Icons.Filled.BarChart else Icons.Outlined.BarChart
                            BottomNavItem.Profile -> if (isSelected) Icons.Filled.Person else Icons.Outlined.Person
                        },
                        contentDescription = item.title,
                        tint = if (isSelected) White else Gray,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}
