package com.example.composefirebaseauth.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleLineOutlineTextField(
    value: String,
    onValueChanged: (value: String) -> Unit,
    label: String,
    isError: Boolean,
    supportingText: String,
    modifier:Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChanged,
        label = { Text(label) },
        isError = isError,
        supportingText = {
            Text(text = supportingText, color = MaterialTheme.colorScheme.error)
        },
        singleLine = true
    )
}
