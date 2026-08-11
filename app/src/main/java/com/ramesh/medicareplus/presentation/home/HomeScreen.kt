package com.ramesh.medicareplus.presentation.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramesh.medicareplus.core.ui.components.EmptyView
import com.ramesh.medicareplus.core.ui.theme.MedicareplusTheme
import com.ramesh.medicareplus.core.ui.theme.White
import com.ramesh.medicareplus.domain.model.Medicine
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

// ============================================================
// HOME SCREEN
// ============================================================
@Composable
fun HomeScreen(
    onMenuClick: () -> Unit = {},
    viewModel: MedicineViewModel = hiltViewModel(),
    previewMedicines: List<Medicine>? = null
) {
    val medicinesFromDb by viewModel.allMedicines.collectAsState()
    val medicines = previewMedicines ?: medicinesFromDb

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        // Header
        HomeHeader(
            onMenuClick = onMenuClick
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )
        // Weekly Calendar
        CalendarStrip()

        Spacer(
            modifier = Modifier.height(32.dp)
        )
        // Today
        Text(
            text = "Today",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))
        // Medicine List
        if (medicines.isEmpty()) {
            EmptyView(
                text = "No medicines scheduled for today",
                modifier = Modifier.weight(1f)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(medicines.size) { index ->
                    val medicine = medicines[index]
                    MedicineCard(
                        medicine = medicine,
                        onToggleTaken = {
                            viewModel.toggleMedicineTaken(medicine)
                        }
                    )
                }
            }
        }
    }
}
// ============================================================
// HOME HEADER
// ============================================================
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
        // Profile section
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile placeholder
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF5F59F7))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE0E0E0),
                        shape = CircleShape
                    )
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(
                text = "Ramesh R",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        // Menu button
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFF5F59F7))
                .clickable {
                    onMenuClick()
                },
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    onMenuClick()
                }
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
// ============================================================
// WEEKLY CALENDAR
// ============================================================
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarStrip(
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit = {}
) {
    /*
     * Initially select today's date.
     */
    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    /*
     * Find Monday of the current week.
     *
     * Example:
     *
     * Monday    16
     * Tuesday   17
     * Wednesday 18
     * Thursday  19
     * Friday    20
     * Saturday  21
     * Sunday    22
     */
    val startOfWeek = remember {
        LocalDate.now()
            .with(
                TemporalAdjusters.previousOrSame(
                    DayOfWeek.MONDAY
                )
            )
    }
    /*
     * Create 7 dates:
     *
     * Monday -> Sunday
     */
    val weekDates = remember(startOfWeek) {
        (0..6).map { index ->
            startOfWeek.plusDays(index.toLong())
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        weekDates.forEach { date ->
            CalendarDateItem(
                date = date,
                isSelected = date == selectedDate,
                onClick = {
                    selectedDate = date

                    onDateSelected(date)
                }
            )
        }
    }
}
// ============================================================
// CALENDAR DATE ITEM
// ============================================================
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun CalendarDateItem(
    date: LocalDate,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    /*
     * Convert DayOfWeek into short display name.
     */
    val dayName = when (date.dayOfWeek) {
        DayOfWeek.MONDAY -> "Mon"
        DayOfWeek.TUESDAY -> "Tue"
        DayOfWeek.WEDNESDAY -> "Wed"
        DayOfWeek.THURSDAY -> "Thu"
        DayOfWeek.FRIDAY -> "Fri"
        DayOfWeek.SATURDAY -> "Sat"
        DayOfWeek.SUNDAY -> "Sun"
    }

    Column(
        modifier = Modifier
            .width(40.dp)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ----------------------------------------------------
        // Day name
        // ----------------------------------------------------
        Text(
            text = dayName,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )
        // ----------------------------------------------------
        // Date circle
        // ----------------------------------------------------
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(
                    color = if (isSelected) {
                        Color(0xFF5F59F7)
                    } else {
                        Color.Transparent
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) {
                    White
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
            )
        }
    }
}
// ============================================================
// MEDICINE CARD
// ============================================================
@Composable
fun MedicineCard(
    medicine: Medicine,
    onToggleTaken: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleTaken() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ------------------------------------------------
            // Medicine Icon
            // ------------------------------------------------
            Box(
                modifier = Modifier
                    .size(
                        width = 60.dp,
                        height = 80.dp
                    )
                    .clip(
                        RoundedCornerShape(12.dp)
                    )
                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Medication,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(16.dp)
            )
            // ------------------------------------------------
            // Medicine Details
            // ------------------------------------------------
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Medicine name
                Text(
                    text = medicine.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                // Dosage + Instruction
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Medication,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )

                    Text(
                        text = medicine.dosage,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Icon(
                        imageVector = Icons.Default.Restaurant,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )

                    Text(
                        text = medicine.instruction,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                // Time
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )

                    Text(
                        text = medicine.time,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            // ------------------------------------------------
            // Taken Status
            // ------------------------------------------------
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
// ============================================================
// PREVIEW
// ============================================================
@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "Home Screen - Data",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    MedicareplusTheme {
        HomeScreen(
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
@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "Home Screen - Empty",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenEmptyPreview() {
    MedicareplusTheme {
        HomeScreen(
            previewMedicines = emptyList()
        )
    }
}
