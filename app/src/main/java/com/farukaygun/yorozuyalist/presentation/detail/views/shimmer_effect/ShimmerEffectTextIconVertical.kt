package com.farukaygun.yorozuyalist.presentation.detail.views.shimmer_effect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect.ShimmerEffect

@Composable
fun ShimmerEffectTextIconVertical() {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		ShimmerEffect(
			modifier = Modifier
				.padding(4.dp)
				.size(36.dp),
		)
	}
}