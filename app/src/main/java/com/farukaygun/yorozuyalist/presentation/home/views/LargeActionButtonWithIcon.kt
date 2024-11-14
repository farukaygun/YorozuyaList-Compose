package com.farukaygun.yorozuyalist.presentation.home.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LargeActionButtonWithIcon(
	text: String,
	icon: Int,
	onClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	Button(
		onClick = onClick,
		shape = RoundedCornerShape(10.dp),
		modifier = modifier
			.height(56.dp)
			.fillMaxWidth(),
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.Start,
		) {
			Icon(
				painter = painterResource(id = icon),
				contentDescription = "Large action button icon",
				tint = MaterialTheme.colorScheme.onPrimary
			)

			Text(
				text = text,
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onPrimary,
				modifier = Modifier.weight(1f)
			)
		}
	}
}

@Preview
@Composable
fun LargeActionButtonWithIconPreview() {
	LargeActionButtonWithIcon(
		text = "Button",
		icon = android.R.drawable.ic_menu_add,
		onClick = {}
	)
}