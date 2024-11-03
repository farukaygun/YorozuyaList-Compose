
plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("org.jetbrains.kotlin.plugin.compose")
	id("com.google.devtools.ksp")
}

android {
	namespace = "com.farukaygun.yorozuyalist"
	compileSdk = 35

	defaultConfig {
		applicationId = "com.farukaygun.yorozuyalist"
		minSdk = 26
		targetSdk = 35
		versionCode = 14
		versionName = "2.0-rc"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
	}

	tasks.register("printVersionName") {
		println(android.defaultConfig.versionName)
	}

	buildTypes {
		release {
			isMinifyEnabled = true
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_19
		targetCompatibility = JavaVersion.VERSION_19
	}
	kotlinOptions {
		jvmTarget = "19"
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.15"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
			excludes += "/META-INF/versions/**"
		}
	}
}

dependencies {
	implementation("androidx.test.services:storage:1.5.0")
	val ktorVersion = "3.0.0"
	val koinVersion="4.0.0"
	val coilVersion = "2.7.0"


	implementation("com.google.android.engage:engage-core:1.5.5")
	implementation("androidx.core:core-ktx:1.15.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
	implementation("androidx.activity:activity-compose:1.9.3")
	implementation(platform("androidx.compose:compose-bom:2024.10.01"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3:1.3.1")

	// navigation
	implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
	implementation("androidx.navigation:navigation-compose:2.8.3")

	// Ktor
	implementation("io.ktor:ktor-client-core:$ktorVersion")
	implementation("io.ktor:ktor-client-android:$ktorVersion")
	implementation("io.ktor:ktor-client-logging:$ktorVersion")
	implementation("io.ktor:ktor-client-json:$ktorVersion")
	implementation("io.ktor:ktor-serialization-gson:$ktorVersion")
	implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

	//Koin
	implementation ("io.insert-koin:koin-android:$koinVersion")
	implementation ("io.insert-koin:koin-androidx-compose:$koinVersion")
	implementation ("io.insert-koin:koin-ktor:$koinVersion")
	implementation ("io.insert-koin:koin-logger-slf4j:$koinVersion")

	// Coil
	implementation("io.coil-kt:coil-compose:$coilVersion")
	// Custom Tabs
	implementation("androidx.browser:browser:1.8.0")
	implementation("androidx.preference:preference-ktx:1.2.1")

	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
	implementation("co.yml:ycharts:2.1.0")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.2.1")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
	androidTestImplementation(platform("androidx.compose:compose-bom:2024.10.01"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest:1.7.5")
}