
plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("org.jetbrains.kotlin.plugin.compose")
	id("com.google.devtools.ksp")
}

android {
	namespace = "com.farukaygun.yorozuyalist"
    compileSdk = 36

    defaultConfig {
		applicationId = "com.farukaygun.yorozuyalist"
		minSdk = 26
		targetSdk = 36
		versionCode = 22
		versionName = "2.2.2"

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
		sourceCompatibility = JavaVersion.VERSION_21
		targetCompatibility = JavaVersion.VERSION_21
	}
	buildFeatures {
		compose = true
	}

	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
			excludes += "/META-INF/versions/**"
		}
	}
}

dependencies {
	val ktorVersion = "3.3.3"
	val koinVersion="4.1.1"
	val coilVersion = "3.3.0"

	implementation("com.google.android.engage:engage-core:1.5.11")
	implementation("androidx.core:core-ktx:1.17.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")
	implementation("androidx.activity:activity-compose:1.12.2")
	implementation(platform("androidx.compose:compose-bom:2025.12.01"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3-android:1.4.0")

	// navigation
	implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
	implementation("androidx.navigation:navigation-compose:2.9.6")

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
	implementation("io.coil-kt.coil3:coil-compose:$coilVersion")
	implementation("io.coil-kt.coil3:coil-network-ktor3:$coilVersion")

	// Custom Tabs
	implementation("androidx.browser:browser:1.9.0")
	implementation("androidx.preference:preference-ktx:1.2.1")

	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
	implementation("co.yml:ycharts:2.1.0")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.3.0")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
	androidTestImplementation(platform("androidx.compose:compose-bom:2025.12.01"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest:1.10.0")
}