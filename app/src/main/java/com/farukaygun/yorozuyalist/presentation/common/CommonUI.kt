package com.farukaygun.yorozuyalist.presentation.common

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
							Text("OK")
						}
					},
					modifier = Modifier.padding(8.dp)
				) { Text(text = message) }
			}
		}
	}
}