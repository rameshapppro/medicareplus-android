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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramesh.medicareplus.core.ui.components.EmptyView
import com.ramesh.medicareplus.core.ui.theme.Background
import com.ramesh.medicareplus.core.ui.theme.MedicareplusTheme
import com.ramesh.medicareplus.core.ui.theme.Primary
import com.ramesh.medicareplus.core.ui.theme.Secondary
import com.ramesh.medicareplus.core.ui.theme.Surface
import com.ramesh.medicareplus.core.ui.theme.TextPrimary
import com.ramesh.medicareplus.domain.model.Medicine
import com.ramesh.medicareplus.presentation.home.MedicineViewModel

// ============================================================
// Medicine List Screen
// ============================================================

@Composable
fun MedicineListScreen(
    onBackClick: () -> Unit = {},
    onMedicineClick: (Medicine) -> Unit = {},
    onEditClick: (Medicine) -> Unit = {},
    viewModel: MedicineViewModel = hiltViewModel(),
    previewMedicines: List<Medicine>? = null
) {
    val medicinesFromDb by viewModel.allMedicines.collectAsState()
    val medicines = previewMedicines ?: medicinesFromDb

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
        if (medicines.isEmpty()) {
            EmptyView(
                text = "No medicines added yet",
                modifier = Modifier.weight(1f)
            )
        } else {
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
                        onEditClick = {
                            onEditClick(medicine)
                        },
                        onDeleteClick = {
                            viewModel.deleteMedicine(medicine)
                        }
                    )
                }
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
            style = MaterialTheme.typography.headlineSmall,
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
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

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
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary
                    )

                    Box {
                        IconButton(
                            onClick = { showMenu = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreHoriz,
                                contentDescription = "Options for ${medicine.name}",
                                tint = TextPrimary
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Edit") },
                                onClick = {
                                    showMenu = false
                                    onEditClick()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Delete") },
                                onClick = {
                                    showMenu = false
                                    onDeleteClick()
                                }
                            )
                        }
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
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextPrimary
                        )
                    }

                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodyMedium,
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
                            style = MaterialTheme.typography.bodyMedium,
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
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary
                    )
                }
            }
        }
    }
}

@Preview(
    name = "Medicine List - Data",
    showBackground = true,
)
@Composable
private fun MedicineListScreenPreview() {
    MedicareplusTheme {
        MedicineListScreen(
            previewMedicines = listOf(
                Medicine(
                    id = "1",
                    name = "Paracetamol",
                    dosage = "500mg",
                    time = "08:00 AM",
                    isTaken = false,
                    instruction = "After Food"
                ),
                Medicine(
                    id = "2",
                    name = "Amoxicillin",
                    dosage = "250mg",
                    time = "01:00 PM",
                    isTaken = true,
                    instruction = "Before Food"
                )
            )
        )
    }
}

@Preview(
    name = "Medicine List - Empty",
    showBackground = true,
)
@Composable
private fun MedicineListScreenEmptyPreview() {
    MedicareplusTheme {
        MedicineListScreen(
            previewMedicines = emptyList()
        )
    }
}
