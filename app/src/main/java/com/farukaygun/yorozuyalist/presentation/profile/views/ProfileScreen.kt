package com.farukaygun.yorozuyalist.presentation.profile.views

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.data.di.apiServiceModule
import com.farukaygun.yorozuyalist.data.di.repositoryModule
import com.farukaygun.yorozuyalist.data.di.useCaseModule
import com.farukaygun.yorozuyalist.data.di.viewModelModule
import com.farukaygun.yorozuyalist.domain.models.user.User
import com.farukaygun.yorozuyalist.presentation.profile.ProfileEvent
import com.farukaygun.yorozuyalist.presentation.profile.ProfileViewModel
import com.farukaygun.yorozuyalist.util.Extensions.CustomExtensions.formatDate
import com.farukaygun.yorozuyalist.util.Extensions.CustomExtensions.formatToAbbreviatedDate
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun ProfileScreen(
	viewModel: ProfileViewModel = koinViewModel()
) {
	val state = viewModel.state.value

	if (state.profileData != null && !state.isLoading) {
		Column(
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			UserInfoSection(
				viewModel = viewModel,
				data = state.profileData
			)

			HorizontalDivider(
				color = MaterialTheme.colorScheme.onBackground,
				thickness = 1.dp,
			)

			AnimeStatisticsSection(data = state.profileData)
		}
	} else {
		ShimmerEffectProfileScreen()
	}

	if (state.error.isNotEmpty())
		Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
}

@Composable
fun UserInfoSection(
	viewModel: ProfileViewModel,
	data: User
) {
	val context = LocalContext.current

	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(16.dp),
		horizontalArrangement = Arrangement.spacedBy(32.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		SubcomposeAsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(data.picture)
				.crossfade(true)
				.crossfade(350)
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
					painter = painterResource(id = R.drawable.broken_image_24px),
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
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			Row(
				horizontalArrangement = Arrangement.spacedBy(8.dp),
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.clickable {
						viewModel.onEvent(
							ProfileEvent.OpenProfileWithCustomTab(
								context = context,
								username = data.name
							)
						)
					}
			) {
				Text(
					text = data.name,
					color = MaterialTheme.colorScheme.onBackground,
					style = MaterialTheme.typography.titleLarge,
					fontWeight = FontWeight.Bold
				)

				Icon(
					painter = painterResource(id = R.drawable.link_24px),
					contentDescription = "Verified icon",
					tint = MaterialTheme.colorScheme.onBackground
				)
			}

			Row(
				horizontalArrangement = Arrangement.spacedBy(8.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				Icon(
					painter = painterResource(id = R.drawable.location_on_24px),
					contentDescription = "Season icon",
					tint = MaterialTheme.colorScheme.onBackground
				)

				Text(
					text = data.location,
					textAlign = TextAlign.Start,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onBackground,
					style = MaterialTheme.typography.bodyMedium,
				)
			}

			Row(
				horizontalArrangement = Arrangement.spacedBy(8.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				Icon(
					painter = painterResource(id = R.drawable.cake_24px),
					contentDescription = "Season icon",
					tint = MaterialTheme.colorScheme.onBackground
				)

				Text(
					text = data.birthday.formatDate(),
					textAlign = TextAlign.Start,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onBackground,
					style = MaterialTheme.typography.bodyMedium,
				)
			}

			Row(
				horizontalArrangement = Arrangement.spacedBy(8.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				Icon(
					painter = painterResource(id = R.drawable.calendar_month_24px),
					contentDescription = "Register date icon",
					tint = MaterialTheme.colorScheme.onBackground
				)

				Text(
					text = data.joinedAt.formatToAbbreviatedDate(),
					textAlign = TextAlign.Start,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onSurface,
					style = MaterialTheme.typography.bodyMedium,
				)
			}
		}
	}
}

@Composable
fun AnimeStatisticsSection(data: User) {
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
				color = Color(0xFFFFD700),
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
				.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceEvenly,
			verticalAlignment = Alignment.CenterVertically
		) {
			DonutChart(chartData)

			Column {
				chartData.slices.forEach { slice ->
					AssistChip(
						onClick = { /*TODO*/ },
						label = {
							Text(
								text = "${slice.value.toInt()} ${slice.label}",
								style = MaterialTheme.typography.bodyMedium,
								color = MaterialTheme.colorScheme.onPrimary
							)
						},
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
		isClickOnSliceEnabled = true,
		labelType = PieChartConfig.LabelType.VALUE,
		labelColor = MaterialTheme.colorScheme.onBackground,
		isSumVisible = true,
		strokeWidth = 32f,
		chartPadding = 16,
		backgroundColor = MaterialTheme.colorScheme.background,
	)

	DonutPieChart(
		modifier = Modifier
			.height(175.dp),
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
		ProfileScreen()
	}
}