package com.example.composefirebaseauth.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.composefirebaseauth.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordOutlineTextField(
    value: String,
    onValueChanged: (value: String) -> Unit,
    label: String,
    isError: Boolean,
    supportingText: String,
    modifier: Modifier = Modifier
) {
    var isPasswordVisibility by remember {
        mutableStateOf(false)
    }

    val icon =
        if (isPasswordVisibility) painterResource(id = R.drawable.ic_visibility) else painterResource(
            id = R.drawable.ic_visibility_off
        )

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChanged,
        label = {
            Text(label)
        },
        isError = isError,
        supportingText = {
            Text(text = supportingText, color = MaterialTheme.colorScheme.error)
        },
        visualTransformation = if (isPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            Icon(
                painter = icon,
                contentDescription = "Show Password",
                modifier = Modifier.clickable {
                    isPasswordVisibility = !isPasswordVisibility
                })
        },
        singleLine = true
    )

}