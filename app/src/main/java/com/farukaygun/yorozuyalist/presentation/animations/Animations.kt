package com.farukaygun.yorozuyalist.presentation.animations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically

class Animations {
	companion object {
		fun slideOutTopOperation(): ExitTransition {
			println("enter")
			return slideOutVertically(
				targetOffsetY = { -it },
				animationSpec = tween(300)
			)
		}

		fun slideInTopOperation(): EnterTransition {
			println("exit")
			return slideInVertically(
				initialOffsetY = { -it },
				animationSpec = tween(300)
			)
		}
	}
}