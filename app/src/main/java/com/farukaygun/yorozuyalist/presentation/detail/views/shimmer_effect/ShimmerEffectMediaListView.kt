package com.farukaygun.yorozuyalist.presentation.detail.views.shimmer_effect

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect.ShimmerEffectHorizontalList

@Composable
fun ShimmerEffectMediaListView(
	title: String
) {
	Column {
		Text(
			text = title,
			style = MaterialTheme.typography.titleMedium,
			color = MaterialTheme.colorScheme.onBackground,
		)

		ShimmerEffectHorizontalList()
	}
}