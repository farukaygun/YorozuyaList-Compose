package com.farukaygun.yorozuyalist.presentation.detail.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MediaInfoView(
	title: String,
	info: String?,
	modifier: Modifier = Modifier
) {
	Row(
		modifier = Modifier
			.padding(vertical = 4.dp)
			.then(modifier)
	) {
		Text(
			text = title,
			modifier = Modifier.weight(1f),
			color = MaterialTheme.colorScheme.onBackground,
			style = MaterialTheme.typography.bodyMedium
		)
		Column(modifier = Modifier.weight(1.4f)) {
			SelectionContainer {
				Text(
					text = info ?: "",
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onBackground,
				)
			}
		}
	}
}