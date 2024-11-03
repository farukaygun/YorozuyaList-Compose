package com.farukaygun.yorozuyalist.presentation.detail.views

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TextIconVertical(
	text: String,
	@DrawableRes icon: Int,
	modifier: Modifier = Modifier,
	color: Color = MaterialTheme.colorScheme.onBackground,
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Icon(
			painter = painterResource(icon),
			contentDescription = text,
			modifier = Modifier.padding(4.dp),
			tint = color
		)
		Text(
			text = text,
			modifier = Modifier
				.padding(horizontal = 4.dp),
			color = color,
			style = MaterialTheme.typography.bodyMedium,
		)
	}
}