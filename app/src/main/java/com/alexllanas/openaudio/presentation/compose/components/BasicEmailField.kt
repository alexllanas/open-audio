package com.alexllanas.openaudio.presentation.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.alexllanas.openaudio.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BasicEmailField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
) {
    Column(modifier = modifier) {
        BasicInputField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            label = label,
            leadingIcon = {
                if (isError) {
                    Icon(
                        Icons.Filled.Error,
                        "error",
                        tint = MaterialTheme.colors.error
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = stringResource(R.string.email)
                    )
                }
            },
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        if (isError) {
            Text(
                text = stringResource(R.string.valid_email),
                modifier = Modifier.padding(),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption
            )
        }
    }

}

@Preview
@Composable
fun PreviewBasicEmailField() {
    BasicEmailField(
        value = "",
        onValueChange = {},
        label = { Text("email") },
        isError = true
    )
}