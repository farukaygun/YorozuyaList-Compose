package com.farukaygun.yorozuyalist.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat

object CustomExtensions {
    /** Open link in Chrome Custom Tabs */
    fun Context.openCustomTab(url: String) {
        CustomTabsIntent.Builder()
            .build().apply {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                launchUrl(this@openCustomTab, Uri.parse(url))
            }
    }
}