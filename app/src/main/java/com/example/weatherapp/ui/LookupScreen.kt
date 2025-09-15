package com.example.weatherapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R

@Composable
fun LookupScreen(
    modifier: Modifier = Modifier,
    onLookup: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var cityName by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(
            value = cityName,
            singleLine = true,
            onValueChange = { cityName = it },
            label = { Text(stringResource(R.string.city_name)) },
            modifier = Modifier.semantics {
                contentType = ContentType.Companion.AddressLocality
            }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedButton(
            onClick = {
                onLookup(cityName)
            }
        ) {
            Text(
                text = stringResource(R.string.lookup),
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LookupScreenPreview() {
    LookupScreen()
}