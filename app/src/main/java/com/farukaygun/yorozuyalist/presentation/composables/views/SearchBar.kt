package com.farukaygun.yorozuyalist.presentation.composables.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.animations.Animations
import com.farukaygun.yorozuyalist.presentation.common.AppBarState
import com.farukaygun.yorozuyalist.presentation.search.SearchState

@Composable
fun SearchBar(
	navController: NavController,
	isVisible: Boolean,
) {
	Column(
		modifier = Modifier
			.padding(top = 16.dp)
			.verticalScroll(rememberScrollState())
	) {
		AnimatedVisibility(
			visible = isVisible,
			enter = expandVertically(),
			exit = shrinkVertically()
		) {

		Surface(
			modifier = Modifier
				.fillMaxWidth()
				.height(56.dp)
				.padding(horizontal = 16.dp)
				.clickable { navController.navigate(Screen.SearchScreen.route) },
			shape = RoundedCornerShape(32.dp),
			color = MaterialTheme.colorScheme.surfaceVariant,
		) {
				Row(
					modifier = Modifier
						.padding(horizontal = 16.dp),
					horizontalArrangement = Arrangement.spacedBy(16.dp),
					verticalAlignment = Alignment.CenterVertically
				) {
					Icon(
						painter = painterResource(id = R.drawable.outline_search_24),
						contentDescription = "Search",
						tint = MaterialTheme.colorScheme.onSurfaceVariant
					)

					Text(
						text = "Search",
						modifier = Modifier
							.weight(1f)
							.alpha(.5f),
						color = MaterialTheme.colorScheme.onSurfaceVariant,
						style = MaterialTheme.typography.bodyMedium
					)
				}
			}
		}
	}
}

@Composable
@Preview
fun SearchBarPreview() {
	SearchBar(rememberNavController(), true)
}