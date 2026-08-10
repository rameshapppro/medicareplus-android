package com.ramesh.medicareplus.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramesh.medicareplus.core.ui.theme.Background
import com.ramesh.medicareplus.core.ui.theme.MedicareplusTheme
import com.ramesh.medicareplus.core.ui.theme.Primary
import com.ramesh.medicareplus.core.ui.theme.Secondary
import com.ramesh.medicareplus.core.ui.theme.Success
import com.ramesh.medicareplus.core.ui.theme.Surface
import com.ramesh.medicareplus.core.ui.theme.TextPrimary
import com.ramesh.medicareplus.core.ui.theme.TextSecondary
import com.ramesh.medicareplus.core.ui.theme.White
import com.ramesh.medicareplus.domain.model.Medicine

@Composable
fun HomeScreen(
    onMenuClick: () -> Unit = {}
) {
    val medicines = listOf(
        Medicine("1", "Melformin 500mg tablets", "1 Pill | 1 Pill", "8:30 AM", true),
        Medicine("2", "Paracetamol", "1 Pill | 1 Pill", "8:30 AM", true),
        Medicine("3", "Omega - 4", "1 Pill | 1 Pill", "8:30 AM", true),
        Medicine("4", "Vitamin C", "1 Pill | 1 Pill", "8:30 AM", true),
        Medicine("5", "Iron Supplement", "1 Pill | 1 Pill", "9:00 PM", false)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        HomeHeader(onMenuClick = onMenuClick)
        Spacer(modifier = Modifier.height(24.dp))
        CalendarStrip()
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Today",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(medicines.size) { index ->
                MedicineCard(medicines[index])
            }
        }
    }
}
@Composable
fun HomeHeader(
    onMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Placeholder for profile image
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Primary)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE0E0E0),
                        shape = CircleShape
                    )
            ) {
                // If you have a real image, use Image() here
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Ramesh R",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
        Box(
            modifier = Modifier.clickable{
                onMenuClick.invoke()
            }
                .size(48.dp)
                .clip(CircleShape)
                .background(Primary)

        ) {
            IconButton(
                modifier = Modifier.align(Alignment.Center),
                onClick = { onMenuClick.invoke() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Menu",
                    tint = White
                )
            }
        }
    }
}
@Composable
fun CalendarStrip() {
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val dates = listOf("16", "17", "18", "19", "20", "21", "22")
    val selectedDate = "19"

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(days.size) { index ->
            val isSelected = dates[index] == selectedDate
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text(
                    text = days[index],
                    fontSize = 14.sp,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .sizeIn(minWidth = 40.dp, minHeight = 40.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Primary else Color.Transparent)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dates[index],
                        fontSize = 16.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) White else TextPrimary
                    )
                }
            }
        }
    }
}
@Composable
fun MedicineCard(medicine: Medicine) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(width = 60.dp, height = 80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Secondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Medication,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = medicine.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Medication,
                        contentDescription = null,
                        tint = TextPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = medicine.dosage,
                        fontSize = 14.sp,
                        color = TextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = TextPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = medicine.time,
                        fontSize = 14.sp,
                        color = TextPrimary
                    )
                }
            }
            if (medicine.isTaken) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Taken",
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MedicareplusTheme {
        HomeScreen()
    }
}
