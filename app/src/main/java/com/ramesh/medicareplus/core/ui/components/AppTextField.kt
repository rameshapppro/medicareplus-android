package com.ramesh.medicareplus.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ramesh.medicareplus.core.ui.theme.MedicareplusTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = modifier.fillMaxWidth(),
        isError = isError,
        supportingText = errorMessage?.let { { Text(text = it) } }
    )
}

@Preview(showBackground = true)
@Composable
fun AppTextFieldPreview() {
    MedicareplusTheme {
        AppTextField(value = "", onValueChange = {}, label = "Username")
    }
}
