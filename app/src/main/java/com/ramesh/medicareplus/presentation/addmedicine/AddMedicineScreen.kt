package com.ramesh.medicareplus.presentation.addmedicine

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramesh.medicareplus.core.ui.theme.MedicareplusTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ---------------------------------------------------------------------
// Colors
// ---------------------------------------------------------------------

private val ScreenBackground = Color.White
private val InputBackground = Color(0xFFF1F7FD)
private val PrimaryPurple = Color(0xFF5B4BFF)
private val PrimaryText = Color(0xFF111111)
private val SecondaryText = Color(0xFF909090)
private val ArrowColor = Color(0xFF777777)

// ---------------------------------------------------------------------
// Add Medicine Screen
// ---------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicineScreen(
    onBackClick: () -> Unit = {},
    onMakeSchedule: () -> Unit = {}
) {
    var medicineName by remember { mutableStateOf("") }
    var strength by remember { mutableStateOf("") }

    var whenToTake by remember { mutableStateOf("") }
    var medicineType by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }

    var startDate by remember { mutableStateOf<Long?>(null) }
    var finishDate by remember { mutableStateOf<Long?>(null) }

    var showStartDatePicker by remember { mutableStateOf(false) }
    var showFinishDatePicker by remember { mutableStateOf(false) }

    var selectedDays by remember {
        mutableStateOf(
            setOf(
                "Sun",
                "Mon",
                "Tue",
                "Wed",
                "Thu",
                "Fri",
                "Sat"
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 38.dp)
        ) {

            Spacer(modifier = Modifier.height(18.dp))

            // ---------------------------------------------------------
            // Header
            // ---------------------------------------------------------

            AddMedicineHeader(
                onBackClick = onBackClick
            )

            Spacer(modifier = Modifier.height(34.dp))

            // ---------------------------------------------------------
            // Medicine Photo
            // ---------------------------------------------------------

            MedicinePhotoPicker(
                onClick = {
                    // TODO: Open image picker
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            // ---------------------------------------------------------
            // Medicine Name
            // ---------------------------------------------------------

            MedicineLabel(
                text = "Medicine Name"
            )

            Spacer(modifier = Modifier.height(8.dp))

            MedicineTextField(
                value = medicineName,
                onValueChange = {
                    medicineName = it
                },
                placeholder = "Enter Medicine Name"
            )

            Spacer(modifier = Modifier.height(22.dp))

            // ---------------------------------------------------------
            // Strength
            // ---------------------------------------------------------

            MedicineLabel(
                text = "Strength"
            )

            Spacer(modifier = Modifier.height(8.dp))

            MedicineTextField(
                value = strength,
                onValueChange = {
                    strength = it
                },
                placeholder = "Enter The Strength"
            )

            Spacer(modifier = Modifier.height(22.dp))

            // ---------------------------------------------------------
            // When To Take
            // ---------------------------------------------------------

            MedicineLabel(
                text = "When To Take"
            )

            Spacer(modifier = Modifier.height(8.dp))

            MedicineDropdown(
                value = whenToTake,
                placeholder = "Select When To Take",
                options = listOf(
                    "Before Meal",
                    "After Meal",
                    "With Meal",
                    "Anytime"
                ),
                onSelected = {
                    whenToTake = it
                }
            )

            Spacer(modifier = Modifier.height(22.dp))

            // ---------------------------------------------------------
            // Type
            // ---------------------------------------------------------

            MedicineLabel(
                text = "Type"
            )

            Spacer(modifier = Modifier.height(8.dp))

            MedicineDropdown(
                value = medicineType,
                placeholder = "Select Medicine Type",
                options = listOf(
                    "Tablet",
                    "Capsule",
                    "Syrup",
                    "Injection",
                    "Drops",
                    "Other"
                ),
                onSelected = {
                    medicineType = it
                }
            )

            Spacer(modifier = Modifier.height(22.dp))

            // ---------------------------------------------------------
            // Amount + Frequency
            // ---------------------------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    MedicineLabel(
                        text = "Amount"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    MedicineDropdown(
                        value = amount,
                        placeholder = "Select Amount",
                        options = listOf(
                            "1",
                            "2",
                            "3",
                            "4",
                            "5"
                        ),
                        onSelected = {
                            amount = it
                        }
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    MedicineLabel(
                        text = "Frequency"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    MedicineDropdown(
                        value = frequency,
                        placeholder = "Select Frequency",
                        options = listOf(
                            "Once a day",
                            "Twice a day",
                            "Three times a day",
                            "Every 4 hours",
                            "Every 6 hours",
                            "Every 8 hours"
                        ),
                        onSelected = {
                            frequency = it
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // ---------------------------------------------------------
            // Start + Finish
            // ---------------------------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    MedicineLabel(
                        text = "Start"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    MedicineDateField(
                        date = startDate,
                        placeholder = "Select Date",
                        onClick = {
                            showStartDatePicker = true
                        }
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    MedicineLabel(
                        text = "Finish"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    MedicineDateField(
                        date = finishDate,
                        placeholder = "Select Date",
                        onClick = {
                            showFinishDatePicker = true
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // ---------------------------------------------------------
            // Days
            // ---------------------------------------------------------

            DaysSelector(
                selectedDays = selectedDays,
                onDayClick = { day ->

                    selectedDays =
                        if (day in selectedDays) {
                            selectedDays - day
                        } else {
                            selectedDays + day
                        }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ---------------------------------------------------------
            // Make Schedule
            // ---------------------------------------------------------

            Button(
                onClick = onMakeSchedule,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryPurple
                ),
                contentPadding = PaddingValues(
                    horizontal = 24.dp
                )
            ) {
                Text(
                    text = "Make Schedule",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // -----------------------------------------------------------------
    // Start Date Picker
    // -----------------------------------------------------------------

    if (showStartDatePicker) {

        MedicineDatePickerDialog(
            onDismiss = {
                showStartDatePicker = false
            },
            onDateSelected = { date ->

                startDate = date
                showStartDatePicker = false
            }
        )
    }

    // -----------------------------------------------------------------
    // Finish Date Picker
    // -----------------------------------------------------------------

    if (showFinishDatePicker) {

        MedicineDatePickerDialog(
            onDismiss = {
                showFinishDatePicker = false
            },
            onDateSelected = { date ->

                finishDate = date
                showFinishDatePicker = false
            }
        )
    }
}

// =====================================================================
// Header
// =====================================================================

@Composable
private fun AddMedicineHeader(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {

        // Back button
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(InputBackground)
                .align(Alignment.CenterStart)
        ) {

            IconButton(
                onClick = onBackClick,
                modifier = Modifier.fillMaxSize()
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = PrimaryText,
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        // Title
        Text(
            text = "Add Medicine",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText
        )
    }
}

// =====================================================================
// Medicine Photo
// =====================================================================

@Composable
private fun MedicinePhotoPicker(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(154.dp)
                .clip(CircleShape)
                .background(InputBackground)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Default.Medication,
                contentDescription = "Add medicine photo",
                tint = PrimaryPurple,
                modifier = Modifier.size(62.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Add photo",
            fontSize = 20.sp,
            color = SecondaryText
        )
    }
}

// =====================================================================
// Label
// =====================================================================

@Composable
private fun MedicineLabel(
    text: String
) {
    Text(
        text = text,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = PrimaryText
    )
}

// =====================================================================
// Text Field
// =====================================================================

@Composable
private fun MedicineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        singleLine = true,
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 18.sp,
                color = SecondaryText
            )
        },
        shape = RoundedCornerShape(14.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = InputBackground,
            unfocusedContainerColor = InputBackground,

            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,

            focusedTextColor = PrimaryText,
            unfocusedTextColor = PrimaryText,

            cursorColor = PrimaryPurple
        )
    )
}

// =====================================================================
// Dropdown
// =====================================================================

@Composable
private fun MedicineDropdown(
    value: String,
    placeholder: String,
    options: List<String>,
    onSelected: (String) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(InputBackground)
                .clickable {
                    expanded = true
                }
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = value.ifEmpty {
                    placeholder
                },
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                color = if (value.isEmpty()) {
                    SecondaryText
                } else {
                    PrimaryText
                }
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Select",
                tint = ArrowColor,
                modifier = Modifier.size(28.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            options.forEach { option ->

                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            fontSize = 16.sp
                        )
                    },
                    onClick = {

                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

// =====================================================================
// Date Field
// =====================================================================

@Composable
private fun MedicineDateField(
    date: Long?,
    placeholder: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(InputBackground)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = date?.let {
                formatDate(it)
            } ?: placeholder,
            modifier = Modifier.weight(1f),
            fontSize = 18.sp,
            color = if (date == null) {
                SecondaryText
            } else {
                PrimaryText
            }
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Select date",
            tint = ArrowColor,
            modifier = Modifier.size(28.dp)
        )
    }
}

// =====================================================================
// Date Picker
// =====================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MedicineDatePickerDialog(
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {

            TextButton(
                onClick = {

                    datePickerState.selectedDateMillis?.let {
                        onDateSelected(it)
                    }
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    ) {

        DatePicker(
            state = datePickerState
        )
    }
}

// =====================================================================
// Days Selector
// =====================================================================

@Composable
private fun DaysSelector(
    selectedDays: Set<String>,
    onDayClick: (String) -> Unit
) {
    val days = listOf(
        "Sun",
        "Mon",
        "Tue",
        "Wed",
        "Thu",
        "Fri",
        "Sat"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        days.forEach { day ->

            val isSelected = day in selectedDays

            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        color = if (isSelected) {
                            InputBackground
                        } else {
                            Color.Transparent
                        }
                    )
                    .clickable {
                        onDayClick(day)
                    },
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = day,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryText,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// =====================================================================
// Date Formatter
// =====================================================================

private fun formatDate(
    millis: Long
): String {
    val formatter = SimpleDateFormat(
        "dd MMM yyyy",
        Locale.getDefault()
    )

    return formatter.format(
        Date(millis)
    )
}

// =====================================================================
// Preview
// =====================================================================

@Preview(
    name = "Add Medicine Screen",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun AddMedicineScreenPreview() {
    MedicareplusTheme {
        AddMedicineScreen()
    }
}
