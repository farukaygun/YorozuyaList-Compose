package com.farukaygun.yorozuyalist.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NoDataView() {
	Box(
		modifier = Modifier
			.padding(16.dp)
			.fillMaxSize(),
		contentAlignment = Alignment.Center,
	) {
		Text(
			text = "There is nothing here\n" + "\uD83D\uDE1E",
			color = MaterialTheme.colorScheme.onBackground,
			style = MaterialTheme.typography.bodyLarge,
			textAlign = TextAlign.Center
		)
	}
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun NoDataViewPreview() {
	NoDataView()
}