
plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("com.google.dagger.hilt.android")
	id("kotlin-kapt")
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
		jvmTarget = "1.8"
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.4.3"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {
	val hiltVersion = "2.48"
	val ktorVersion = "2.3.4"
	val coilVersion = "2.4.0"

	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
	implementation("androidx.activity:activity-compose:1.7.2")
	implementation(platform("androidx.compose:compose-bom:2023.09.01"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3:1.1.2")

	//Dagger - Hilt
	implementation("com.google.dagger:hilt-android:$hiltVersion")
	kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
	kapt("androidx.hilt:hilt-compiler:1.0.0")
	implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
	implementation("androidx.hilt:hilt-work:1.0.0")
	implementation("androidx.work:work-runtime-ktx:2.8.1")

	// Ktor
	implementation("io.ktor:ktor-client-core:$ktorVersion")
	implementation("io.ktor:ktor-client-android:$ktorVersion")
	implementation("io.ktor:ktor-client-logging:$ktorVersion")
	implementation("io.ktor:ktor-client-json:$ktorVersion")
	implementation("io.ktor:ktor-serialization-gson:$ktorVersion")
	implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

	// Coil
	implementation("io.coil-kt:coil-compose:$coilVersion")

	// Browser
	implementation("androidx.browser:browser:1.6.0")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	androidTestImplementation(platform("androidx.compose:compose-bom:2023.09.01"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.1")
}