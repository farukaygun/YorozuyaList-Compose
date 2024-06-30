package com.farukaygun.yorozuyalist.presentation.profile.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.data.di.apiServiceModule
import com.farukaygun.yorozuyalist.data.di.repositoryModule
import com.farukaygun.yorozuyalist.data.di.useCaseModule
import com.farukaygun.yorozuyalist.data.di.viewModelModule
import com.farukaygun.yorozuyalist.domain.model.user.User
import com.farukaygun.yorozuyalist.presentation.profile.ProfileEvent
import com.farukaygun.yorozuyalist.presentation.profile.ProfileState
import com.farukaygun.yorozuyalist.presentation.profile.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun ProfileScreen(
	navController: NavController,
	viewModel: ProfileViewModel = koinViewModel()
) {
	val state = viewModel.state.value

	LaunchedEffect(Unit) {
		viewModel.onEvent(ProfileEvent.InitRequestChain)
	}

	Column {
		state.profileData?.let {
			UserInfoSection(data = it)
		}

		HorizontalDivider(
			color = MaterialTheme.colorScheme.onBackground,
			thickness = 1.dp,
			modifier = Modifier.padding(vertical = 16.dp)
		)

		state.profileData?.let {
			AnimeStatisticsSection(state = state, data = it)
		}

		HorizontalDivider(
			color = MaterialTheme.colorScheme.onBackground,
			thickness = 1.dp,
			modifier = Modifier.padding(vertical = 16.dp)
		)

		state.profileData?.let {

		}
	}
}

@Composable
fun UserInfoSection(data: User) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 32.dp, vertical = 16.dp)
	) {
		SubcomposeAsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(data.picture)
				.crossfade(true)
				.crossfade(300)
				.build(),
			loading = {
				Column(
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					CircularProgressIndicator()
				}
			},
			error = {
				Icon(
					painter = painterResource(id = R.drawable.outline_broken_image_24px),
					contentDescription = "Error icon",
					tint = MaterialTheme.colorScheme.onBackground
				)
			},
			contentDescription = "",
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(100.dp, 150.dp)
		)

		Column(
			modifier = Modifier
				.padding(horizontal = 16.dp, vertical = 4.dp),
		) {
			Text(
				text = data.name,
				modifier = Modifier
					.padding(start = 12.dp, bottom = 16.dp),
				color = MaterialTheme.colorScheme.onBackground,
				style = MaterialTheme.typography.titleLarge,
				fontWeight = FontWeight.Bold

			)
			Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
				Icon(
					painter = painterResource(id = R.drawable.outline_location_on_24px),
					contentDescription = "Season icon",
					modifier = Modifier.padding(end = 8.dp),
					tint = MaterialTheme.colorScheme.onBackground

				)

				Text(
					text = data.location,
					textAlign = TextAlign.Start,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onBackground,
					style = MaterialTheme.typography.bodyMedium,
					modifier = Modifier.padding(top = 2.dp)
				)
			}

			Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
				Icon(
					painter = painterResource(id = R.drawable.outline_cake_24px),
					contentDescription = "Season icon",
					modifier = Modifier.padding(end = 8.dp),
					tint = MaterialTheme.colorScheme.onBackground
				)

				Text(
					text = data.birthday,
					textAlign = TextAlign.Start,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onBackground,
					style = MaterialTheme.typography.bodyMedium,
					modifier = Modifier.padding(top = 2.dp)
				)
			}

			Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
				Icon(
					painter = painterResource(id = R.drawable.outline_calendar_month_24px),
					contentDescription = "Register date icon",
					modifier = Modifier.padding(end = 8.dp),
					tint = MaterialTheme.colorScheme.onBackground
				)

				Text(
					text = data.joinedAt,
					textAlign = TextAlign.Start,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onSurface,
					style = MaterialTheme.typography.bodyMedium,
					modifier = Modifier.padding(top = 2.dp)
				)
			}
		}
	}
}

@Composable
fun AnimeStatisticsSection(state: ProfileState, data: User) {
	val chartData = PieChartData(
		slices = listOf(
			PieChartData.Slice(
				value = data.userAnimeStatistics.numItemsWatching.toFloat(),
				color = Color(0xFF4CAF50),
				label = "Watching"
			),
			PieChartData.Slice(
				value = data.userAnimeStatistics.numItemsCompleted.toFloat(),
				color = Color(0xFF2196F3),
				label = "Completed"
			),
			PieChartData.Slice(
				value = data.userAnimeStatistics.numItemsOnHold.toFloat(),
				color = Color(0xFFFFEB3B),
				label = "On Hold"
			),
			PieChartData.Slice(
				value = data.userAnimeStatistics.numItemsDropped.toFloat(),
				color = Color(0xFFF44336),
				label = "Dropped"
			),
			PieChartData.Slice(
				value = data.userAnimeStatistics.numItemsPlanToWatch.toFloat(),
				color = Color(0xFF9E9E9E),
				label = "Plan to Watch"
			)
		),
		plotType = PlotType.Donut
	)

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 16.dp)
	) {
		Text(
			text = "Anime Stats",
			modifier = Modifier
				.padding(bottom = 16.dp)
				.fillMaxWidth(),
			style = MaterialTheme.typography.titleMedium,
			fontWeight = FontWeight.Bold,
			color = MaterialTheme.colorScheme.onBackground,
			textAlign = TextAlign.Center
		)

		Row(
			modifier = Modifier
				.fillMaxWidth()
		) {
			DonutChart(chartData)

			Column {
				chartData.slices.forEach { slice ->
					AssistChip(
						onClick = { /*TODO*/ },
						label = { Text(
							text = "${slice.value.toInt()} ${slice.label}",
							fontWeight = FontWeight.Bold,
						)},
						colors = AssistChipDefaults.assistChipColors(
							labelColor = MaterialTheme.colorScheme.onPrimary,
							containerColor = slice.color
						)
					)
				}
			}
		}
	}
}

@Composable
private fun DonutChart(data: PieChartData) {
	val donutChartConfig = PieChartConfig(
		isAnimationEnable = true,
		animationDuration = 1000,
		labelVisible = true,
		isClickOnSliceEnabled = false,
		labelType = PieChartConfig.LabelType.VALUE,
		isSumVisible = true,
		strokeWidth = 64f,
		chartPadding = 32,
		backgroundColor = MaterialTheme.colorScheme.background,
	)

	DonutPieChart(
		modifier = Modifier
			.height(250.dp),
		pieChartConfig = donutChartConfig,
		pieChartData = data,
	)
}

@Composable
@Preview
fun PreviewProfileScreen() {
	val context = LocalContext.current

	KoinApplication(application = {
		androidContext(context)
		modules(
			viewModelModule,
			repositoryModule,
			useCaseModule,
			apiServiceModule
		)
	}) {
		ProfileScreen(
			navController = rememberNavController()
		)
	}
}