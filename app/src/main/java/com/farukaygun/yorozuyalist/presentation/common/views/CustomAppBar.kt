package com.farukaygun.yorozuyalist.presentation.common.views

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.presentation.common.AppBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
	appBarState: AppBarState,
	modifier: Modifier = Modifier
) {
	CenterAlignedTopAppBar(
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer,
			titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
		),
		title = {
			Text(text = appBarState.title)
		},
		navigationIcon = {
			IconButton(onClick = {
				// onSearchClicked()
			}) {
				Icon(
					imageVector = ImageVector.vectorResource(id = R.drawable.outline_search_24),
					contentDescription = "Back"
				)
			}
		},
		modifier = modifier
	)
}

@Composable
@Preview
fun AppBarPreview() {
	CustomTopAppBar(AppBarState(rememberNavController(), rememberCoroutineScope()))
}