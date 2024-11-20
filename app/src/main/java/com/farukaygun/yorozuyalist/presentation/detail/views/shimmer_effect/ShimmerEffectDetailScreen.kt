package com.farukaygun.yorozuyalist.presentation.detail.views.shimmer_effect

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.farukaygun.yorozuyalist.data.di.apiServiceModule
import com.farukaygun.yorozuyalist.data.di.repositoryModule
import com.farukaygun.yorozuyalist.data.di.useCaseModule
import com.farukaygun.yorozuyalist.data.di.viewModelModule
import com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect.ShimmerEffect
import com.farukaygun.yorozuyalist.presentation.detail.views.DetailScreen
import com.farukaygun.yorozuyalist.util.enums.ScreenType
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication

@Composable
fun ShimmerEffectDetailScreen() {
	Column(
		modifier = Modifier
			.padding(16.dp)
			.verticalScroll(rememberScrollState()),
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		ShimmerEffectMediaInfo()
		ShimmerEffectGenresSection()
		ShimmerEffectSynopsisSection()
		ShimmerEffectStatisticsSection()
		ShimmerEffectMoreInfoSection()
		ShimmerEffectRelatedSection()
		ShimmerEffectRecommendationSection()
	}
}

@Composable
private fun ShimmerEffectMediaInfo() {
	Row(
		modifier = Modifier.padding(bottom = 8.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(16.dp)
	) {
		ShimmerEffect(
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(100.dp, 150.dp)
		)

		Column(
			verticalArrangement = Arrangement.spacedBy(space = 8.dp)
		) {

			ShimmerEffect(
				modifier = Modifier
					.clip(RoundedCornerShape(10.dp))
					.size(200.dp, 24.dp)
			)

			ShimmerEffect(
				modifier = Modifier
					.clip(RoundedCornerShape(10.dp))
					.size(144.dp, 24.dp)
			)

			ShimmerEffect(
				modifier = Modifier
					.clip(RoundedCornerShape(10.dp))
					.size(144.dp, 24.dp)
			)

			ShimmerEffect(
				modifier = Modifier
					.clip(RoundedCornerShape(10.dp))
					.size(144.dp, 24.dp)
			)
		}
	}
}

@Composable
fun ShimmerEffectGenresSection() {
	Row(
		modifier = Modifier
			.horizontalScroll(rememberScrollState()),
		horizontalArrangement = Arrangement.spacedBy(8.dp)
	) {
        (1..5).forEach { i ->
            ShimmerEffect(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .size(72.dp, 32.dp)
            )
        }
	}
}

@Composable
fun ShimmerEffectSynopsisSection() {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 16.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		Column(
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
            (1..5).forEach { i ->
                ShimmerEffect(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(400.dp, 12.dp)
                )
            }
		}

		ShimmerEffect(
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(96.dp, 24.dp)
				.align(Alignment.End)
		)
	}
}

@Composable
fun ShimmerEffectStatisticsSection() {
	Column(
		modifier = Modifier.padding(vertical = 16.dp)
	) {
		Text(
			text = "Statistics",
			style = MaterialTheme.typography.titleMedium,
			color = MaterialTheme.colorScheme.onBackground,
			modifier = Modifier.padding(bottom = 8.dp)
		)
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceEvenly,
			verticalAlignment = Alignment.CenterVertically
		) {

			ShimmerEffectTextIconVertical()

			VerticalDivider(modifier = Modifier.height(32.dp))

			ShimmerEffectTextIconVertical()

			VerticalDivider(modifier = Modifier.height(32.dp))

			ShimmerEffectTextIconVertical()

			VerticalDivider(modifier = Modifier.height(32.dp))

			ShimmerEffectTextIconVertical()
		}
	}
}

@Composable
fun ShimmerEffectMoreInfoSection() {
	Column(
		modifier = Modifier.padding(vertical = 8.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		Text(
			text = "More Info",
			style = MaterialTheme.typography.titleMedium,
			color = MaterialTheme.colorScheme.onBackground
		)

        (1..6).forEach { i ->
            ShimmerEffectMediaInfoView()
        }

		HorizontalDivider()

		ShimmerEffectMediaInfoView()

		HorizontalDivider()

        (1..3).forEach { i ->
            ShimmerEffectMediaInfoView()
        }
	}
}

@Composable
fun ShimmerEffectRelatedSection() {
	Column(
		verticalArrangement = Arrangement.spacedBy(16.dp),
	) {
		ShimmerEffectMediaListView(title = "Related Anime")

		ShimmerEffectMediaListView(title = "Related Manga")
	}
}

@Composable
fun ShimmerEffectRecommendationSection() {
	Column(
		modifier = Modifier.padding(bottom = 16.dp)
	) {
		ShimmerEffectMediaListView(title = "Suggestions")
	}
}

@Preview
@Composable
fun ShimmerEffectDetailScreenPreview() {
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
		DetailScreen(
			navController = rememberNavController(),
			type = ScreenType.ANIME.name
		)
	}
}
