package com.farukaygun.yorozuyalist.presentation.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farukaygun.yorozuyalist.presentation.login.LoginViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Text(text = "Home Screen",
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    )
}