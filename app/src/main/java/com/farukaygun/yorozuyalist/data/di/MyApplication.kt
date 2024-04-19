package com.farukaygun.yorozuyalist.data.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidContext(this@MyApplication)
			androidLogger()
			modules(
				apiServiceModule,
				repositoryModule,
				useCaseModule,
				viewModelModule
			)
		}
	}
}