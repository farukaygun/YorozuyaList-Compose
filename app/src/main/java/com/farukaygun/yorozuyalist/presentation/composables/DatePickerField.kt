package com.farukaygun.yorozuyalist.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.util.Extensions.CustomExtensions.formatDate
import com.farukaygun.yorozuyalist.util.Extensions.CustomExtensions.formatToAbbreviatedDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun DatePickerField(
	selectedDate: String?,
	label: String,
	onDateSelected: (String) -> Unit,
	onClear: () -> Unit,
	modifier: Modifier = Modifier
) {
	var date by remember { mutableStateOf(selectedDate) }
	var showDatePicker by remember { mutableStateOf(false) }

	OutlinedTextField(
		value = selectedDate ?: "",
		onValueChange = { if (it.isNotEmpty()) date = it },
		label = { Text(text = label) },
		trailingIcon = {
			if (!selectedDate.isNullOrEmpty()) {
				IconButton(onClick = onClear) {
					Icon(
						painter = painterResource(id = R.drawable.close_16px),
						contentDescription = "Clear selected date button",
					)
				}
			}
		},
		singleLine = true,
		enabled = false,
		readOnly = true,
		colors = OutlinedTextFieldDefaults.colors(
			unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
			disabledTextColor = MaterialTheme.colorScheme.onSurface,
			unfocusedBorderColor = MaterialTheme.colorScheme.outline,
			disabledBorderColor = MaterialTheme.colorScheme.outline
		),
		modifier = modifier.clickable { showDatePicker = true }
	)

	if (showDatePicker) {
		DatePickerModal(
			onDateSelected = {
				onDateSelected(
					it?.formatToAbbreviatedDate() ?: kotlin.time.Clock.System.now().toLocalDateTime(
						TimeZone.currentSystemDefault()
					).date.toString().formatDate()
				)
				showDatePicker = false
			},
			onDismiss = { showDatePicker = false }
		)
	}
}