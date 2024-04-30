package com.farukaygun.yorozuyalist.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.farukaygun.yorozuyalist.R

object CommonUI {


	@Composable
	fun searchAppBar(
		text: String,
		onTextChange: (String) -> Unit,
		onSearchClicked: () -> Unit,
		onCloseClicked: () -> Unit
	) {
		Surface(
			modifier = Modifier
				.padding(8.dp)
				.fillMaxWidth(),
			color = MaterialTheme.colorScheme.surface,
		) {
			TextField(
				value = text,
				onValueChange = {
					onTextChange(it)
				},
				placeholder = { Text(
						text = "Search",
						modifier = Modifier.alpha(.5f),
						color = MaterialTheme.colorScheme.onSurface
					)
			    },
				textStyle = MaterialTheme.typography.bodyMedium,
				singleLine = true,
				leadingIcon = {
					IconButton(onClick = onSearchClicked) {
						Icon(
							imageVector = ImageVector.vectorResource(id = R.drawable.outline_search_24),
							contentDescription = "Search"
						)
					}
				},
				trailingIcon = {
					IconButton(onClick = {
						if (text.isNotEmpty())
							onTextChange("")
						else onCloseClicked()
					}) {
						Icon(
							imageVector = ImageVector.vectorResource(id = R.drawable.outline_close_24),
							contentDescription = "Close Searchbar"
						)
					}
				}
			)
		}
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

@Composable
@Preview
fun SearchBarPreview() {
	CommonUI.searchAppBar(text = "", onTextChange = {}, onSearchClicked = {}, onCloseClicked = {})
}