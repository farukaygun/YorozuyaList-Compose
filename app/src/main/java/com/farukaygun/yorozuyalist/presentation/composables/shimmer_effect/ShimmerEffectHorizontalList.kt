package com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farukaygun.yorozuyalist.presentation.composables.ShimmerEffectItemRow

@Composable
fun ShimmerEffectHorizontalList() {
	Box(
		modifier = Modifier
			.padding(vertical = 8.dp)
			.fillMaxWidth(),
		contentAlignment = Alignment.Center
	) {
		LazyRow(
			modifier = Modifier
				.fillMaxWidth(),
			horizontalArrangement = Arrangement.spacedBy(8.dp)
		) {
			items(10) {
				ShimmerEffectItemRow()
			}
		}
	}
}