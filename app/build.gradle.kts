
plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("com.google.dagger.hilt.android")
	id("com.google.devtools.ksp")
}

android {
	namespace = "com.farukaygun.yorozuyalist"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.farukaygun.yorozuyalist"
		minSdk = 26
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.3"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {
	implementation("com.google.android.engage:engage-core:1.4.0")
	val hiltVersion = "2.51.1"
	val ktorVersion = "2.3.5"
	val coilVersion = "2.4.0"

	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
	implementation("androidx.activity:activity-compose:1.8.2")
	implementation(platform("androidx.compose:compose-bom:2024.04.00"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3:1.2.1")

	// navigation
	implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
	implementation("androidx.navigation:navigation-compose:2.7.7")

	//Dagger - Hilt
	implementation("com.google.dagger:hilt-android:$hiltVersion")
	ksp("com.google.dagger:hilt-android-compiler:$hiltVersion")
	ksp("androidx.hilt:hilt-compiler:1.2.0")
	implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
	implementation("androidx.hilt:hilt-work:1.2.0")
	implementation("androidx.work:work-runtime-ktx:2.9.0")

	// Ktor
	implementation("io.ktor:ktor-client-core:$ktorVersion")
	implementation("io.ktor:ktor-client-android:$ktorVersion")
	implementation("io.ktor:ktor-client-logging:$ktorVersion")
	implementation("io.ktor:ktor-client-json:$ktorVersion")
	implementation("io.ktor:ktor-serialization-gson:$ktorVersion")
	implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

	// Coil
	implementation("io.coil-kt:coil-compose:$coilVersion")
	// Custom Tabs
	implementation("androidx.browser:browser:1.8.0")
	implementation("androidx.preference:preference-ktx:1.2.1")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	androidTestImplementation(platform("androidx.compose:compose-bom:2024.04.00"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.5")
}