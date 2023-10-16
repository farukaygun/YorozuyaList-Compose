package com.farukaygun.yorozuyalist.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.farukaygun.yorozuyalist.R

object CommonUI {
	@Composable
	fun IndeterminateCircularIndicator() {
		CircularProgressIndicator(
			modifier = Modifier.width(32.dp),
			color = MaterialTheme.colorScheme.surfaceVariant,
			trackColor = MaterialTheme.colorScheme.secondary,
		)
	}

	@Composable
	fun MySnackbar(message: String) {
		Column {
			val (snackbarVisibleState, setSnackBarState) = remember { mutableStateOf(true) }

			if (snackbarVisibleState) {
				Snackbar(
					action = {
						Button(onClick = { setSnackBarState(false) }) {
							Text(stringResource(R.string.ok))
						}
					},
					modifier = Modifier.padding(8.dp)
				) { Text(text = message) }
			}
		}
	}
}

@Preview
@Composable
fun CommonUIPreview() {
	Box(modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center) {
		Column(
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			CommonUI.IndeterminateCircularIndicator()
			CommonUI.MySnackbar("Hello World")
		}
	}
}