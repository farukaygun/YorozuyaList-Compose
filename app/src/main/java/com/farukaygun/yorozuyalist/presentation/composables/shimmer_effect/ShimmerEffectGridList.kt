package com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.farukaygun.yorozuyalist.presentation.composables.ShimmerEffectGridListItem

@Composable
fun ShimmerEffectGridList() {
	Box {
		Column {
			LazyVerticalGrid(
				columns = GridCells.Adaptive(minSize = 100.dp),
				horizontalArrangement = Arrangement.SpaceEvenly,
				verticalArrangement = Arrangement.spacedBy(16.dp)
			) {
				items(20) {
					ShimmerEffectGridListItem()
				}
			}
		}
	}
}