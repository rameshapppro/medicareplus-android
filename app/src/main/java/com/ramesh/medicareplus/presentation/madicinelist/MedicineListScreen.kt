package com.ramesh.medicareplus.presentation.madicinelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramesh.medicareplus.core.ui.theme.Background
import com.ramesh.medicareplus.core.ui.theme.MedicareplusTheme
import com.ramesh.medicareplus.core.ui.theme.Primary
import com.ramesh.medicareplus.core.ui.theme.Secondary
import com.ramesh.medicareplus.core.ui.theme.Surface
import com.ramesh.medicareplus.core.ui.theme.TextPrimary
import com.ramesh.medicareplus.domain.model.Medicine

// ============================================================
// Medicine List Screen
// ============================================================

@Composable
fun MedicineListScreen(
    onBackClick: () -> Unit = {},
    onMedicineClick: (Medicine) -> Unit = {},
    onMedicineMenuClick: (Medicine) -> Unit = {}
) {
    val medicines = previewMedicines()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // Header
        MedicineListHeader(
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Medicine List
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(medicines.size) { index ->
                val medicine = medicines[index]
                MedicineListItem(
                    medicine = medicine,
                    onClick = {
                        onMedicineClick(medicine)
                    },
                    onMenuClick = {
                        onMedicineMenuClick(medicine)
                    }
                )
            }
        }
    }
}

// ============================================================
// Header
// ============================================================

@Composable
private fun MedicineListHeader(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    ) {

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .background(Secondary, CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = TextPrimary
            )
        }

        Text(
            text = "Your Medicine",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
    }
}

// ============================================================
// Medicine List Item
// ============================================================

@Composable
private fun MedicineListItem(
    medicine: Medicine,
    onClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Medicine image/icon
            Box(
                modifier = Modifier
                    .sizeIn(
                        minWidth = 60.dp,
                        minHeight = 80.dp
                    )
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

            // Medicine details
            Column(
                modifier = Modifier.weight(1f)
            ) {

                // Medicine name + menu
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = medicine.name,
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    IconButton(
                        onClick = onMenuClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = "Options for ${medicine.name}",
                            tint = TextPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Dosage + instruction
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Medication,
                            contentDescription = null,
                            tint = TextPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = medicine.dosage,
                            fontSize = 14.sp,
                            color = TextPrimary
                        )
                    }

                    Text(
                        text = "•",
                        fontSize = 14.sp,
                        color = TextPrimary,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Restaurant,
                            contentDescription = null,
                            tint = TextPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = medicine.instruction,
                            fontSize = 14.sp,
                            color = TextPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Time
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = TextPrimary,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = medicine.time,
                        fontSize = 14.sp,
                        color = TextPrimary
                    )
                }
            }
        }
    }
}

// ============================================================
// Preview Data
// ============================================================

private fun previewMedicines(): List<Medicine> {
    return listOf(
        Medicine(
            id = "1",
            name = "Melformin 500mg tablets",
            dosage = "1 Pill",
            time = "8:30 AM",
            isTaken = false,
            instruction = "After Meal"
        ),
        Medicine(
            id = "2",
            name = "Paracetamol",
            dosage = "1 Pill",
            time = "8:30 AM",
            isTaken = false,
            instruction = "After Meal"
        ),
        Medicine(
            id = "3",
            name = "Omega - 4",
            dosage = "1 Pill",
            time = "8:30 AM",
            isTaken = false,
            instruction = "After Meal"
        ),
        Medicine(
            id = "4",
            name = "Vitamin C",
            dosage = "1 Pill",
            time = "8:30 AM",
            isTaken = false,
            instruction = "After Meal"
        ),
        Medicine(
            id = "5",
            name = "Napa Extra",
            dosage = "1 Pill",
            time = "8:30 AM",
            isTaken = false,
            instruction = "After Meal"
        )
    )
}

// ============================================================
// Preview
// ============================================================

@Preview(
    showBackground = true,
)
@Composable
private fun MedicineListScreenPreview() {
    MedicareplusTheme {
        MedicineListScreen()
    }
}
