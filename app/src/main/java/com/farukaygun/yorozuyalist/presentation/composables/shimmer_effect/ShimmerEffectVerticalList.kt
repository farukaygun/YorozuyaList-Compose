package com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.farukaygun.yorozuyalist.presentation.composables.ShimmerEffectItemColumn

@Composable
fun ShimmerEffectVerticalList() {
	LazyColumn(
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		items(10) {
			ShimmerEffectItemColumn()
		}
	}
}