package com.farukaygun.yorozuyalist.presentation.detail.views.shimmer_effect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect.ShimmerEffect

@Composable
fun ShimmerEffectMediaInfoView() {
	Row(
		horizontalArrangement = Arrangement.spacedBy(72.dp)
	) {
		ShimmerEffect(
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(72.dp, 12.dp)
		)

		Column {
			SelectionContainer {
				ShimmerEffect(
					modifier = Modifier
						.clip(RoundedCornerShape(10.dp))
						.size(72.dp, 12.dp)
				)
			}
		}
	}
}