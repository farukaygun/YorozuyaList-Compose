package com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun ShimmerEffect(
	modifier: Modifier,
	widthOfShadowBrush: Int = 400,
	angleOfAxisY: Float = 270f,
	durationMillis: Int = 1000,
) {


	val shimmerColors = listOf(
		Color.LightGray.copy(alpha = 0.3f),
		Color.LightGray.copy(alpha = 0.5f),
		Color.LightGray.copy(alpha = 0.8f),
		Color.LightGray.copy(alpha = 0.5f),
		Color.LightGray.copy(alpha = 0.3f),
	)

	val transition = rememberInfiniteTransition(label = "")

	val translateAnimation = transition.animateFloat(
		initialValue = 0f,
		targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = durationMillis,
				easing = LinearEasing,
			),
			repeatMode = RepeatMode.Restart,
		),
		label = "Shimmer loading animation",
	)

	val brush = Brush.linearGradient(
		colors = shimmerColors,
		start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
		end = Offset(x = translateAnimation.value, y = angleOfAxisY),
	)

	Box(
		modifier = modifier
	) {
		Spacer(
			modifier = Modifier
				.matchParentSize()
				.background(brush)
		)
	}
}