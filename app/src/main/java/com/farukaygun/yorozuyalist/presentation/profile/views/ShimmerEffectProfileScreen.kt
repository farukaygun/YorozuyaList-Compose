package com.farukaygun.yorozuyalist.presentation.profile.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.farukaygun.yorozuyalist.data.di.apiServiceModule
import com.farukaygun.yorozuyalist.data.di.repositoryModule
import com.farukaygun.yorozuyalist.data.di.useCaseModule
import com.farukaygun.yorozuyalist.data.di.viewModelModule
import com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect.ShimmerEffect
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication

@Composable
fun ShimmerEffectProfileScreen() {
	Column(
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		ShimmerEffectUserInfoSection()

		HorizontalDivider(
			color = MaterialTheme.colorScheme.onBackground,
			thickness = 1.dp,
		)

		ShimmerEffectAnimeStatisticsSection()
	}
}

@Composable
fun ShimmerEffectUserInfoSection() {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(16.dp),
		horizontalArrangement = Arrangement.spacedBy(32.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		ShimmerEffect(
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(100.dp, 150.dp)
		)

		Column(
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			ShimmerEffect(
				modifier = Modifier
					.clip(RoundedCornerShape(10.dp))
					.size(150.dp, 24.dp)
			)

			Row(
				horizontalArrangement = Arrangement.spacedBy(8.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				ShimmerEffect(
					modifier = Modifier
						.clip(RoundedCornerShape(10.dp))
						.size(100.dp, 24.dp)
				)
			}

			Row(
				horizontalArrangement = Arrangement.spacedBy(8.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				ShimmerEffect(
					modifier = Modifier
						.clip(RoundedCornerShape(10.dp))
						.size(100.dp, 24.dp)
				)
			}

			Row(
				horizontalArrangement = Arrangement.spacedBy(8.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				ShimmerEffect(
					modifier = Modifier
						.clip(RoundedCornerShape(10.dp))
						.size(100.dp, 24.dp)
				)
			}
		}
	}
}

@Composable
fun ShimmerEffectAnimeStatisticsSection() {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 16.dp)
	) {
		Text(
			text = "Anime Stats",
			modifier = Modifier
				.padding(bottom = 24.dp)
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
			ShimmerEffect(
				modifier = Modifier
					.clip(RoundedCornerShape(10.dp))
					.size(175.dp, 175.dp)
			)

			Column(
				verticalArrangement = Arrangement.spacedBy(16.dp)
			) {
				for (i in 1..5) {
					ShimmerEffect(
						modifier = Modifier
							.clip(RoundedCornerShape(10.dp))
							.size(150.dp, 32.dp)
					)
				}
			}
		}
	}
}

@Composable
@Preview
fun ShimmerEffectPreviewProfileScreen() {
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