import com.android.build.api.dsl.ApplicationExtension

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.plugin.compose")
	id("com.google.devtools.ksp")
}

extensions.configure<ApplicationExtension> {
	namespace = "com.farukaygun.yorozuyalist"
    compileSdk = 37

    defaultConfig {
		applicationId = "com.farukaygun.yorozuyalist"
		minSdk = 26
		targetSdk = 37
		versionCode = 23
		versionName = "2.3"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
	}

	tasks.register("printVersionName") {
		doLast {
			val android = project.extensions.getByType<ApplicationExtension>()
			println(android.defaultConfig.versionName)
		}
	}

	buildTypes {
		release {
			isMinifyEnabled = true
   			isShrinkResources = true
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
		buildConfig = true
	}

	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
			excludes += "/META-INF/versions/**"
		}
	}
}

dependencies {
	implementation("androidx.compose.material3:material3:1.4.0")
	val ktorVersion = "3.5.0"
	val koinVersion="4.2.1"
	val coilVersion = "3.4.0"

	implementation("com.google.android.engage:engage-core:1.5.12")
	implementation("androidx.core:core-ktx:1.18.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")
	implementation("androidx.lifecycle:lifecycle-runtime-compose:2.10.0")
	implementation("androidx.activity:activity-compose:1.13.0")
	implementation(platform("androidx.compose:compose-bom:2026.05.00"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3:1.5.0-alpha19")
	implementation("androidx.compose.material3:material3-window-size-class:1.5.0-alpha19")
	implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.5.0-alpha19")

	// navigation
	implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
	implementation("androidx.navigation:navigation-compose:2.9.8")

	// Ktor
	implementation("io.ktor:ktor-client-core:$ktorVersion")
	implementation("io.ktor:ktor-client-android:$ktorVersion")
	implementation("io.ktor:ktor-client-logging:$ktorVersion")
	implementation("io.ktor:ktor-client-json:$ktorVersion")
	implementation("io.ktor:ktor-serialization-gson:$ktorVersion")
	implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
	implementation("io.ktor:ktor-client-auth:$ktorVersion")

	//Koin
	implementation ("io.insert-koin:koin-android:$koinVersion")
	implementation ("io.insert-koin:koin-androidx-compose:$koinVersion")
	implementation ("io.insert-koin:koin-ktor:$koinVersion")
	implementation ("io.insert-koin:koin-logger-slf4j:$koinVersion")

	// Coil
	implementation("io.coil-kt.coil3:coil-compose:$coilVersion")
	implementation("io.coil-kt.coil3:coil-network-ktor3:$coilVersion")

	// Custom Tabs
	implementation("androidx.browser:browser:1.10.0")
	implementation("androidx.preference:preference-ktx:1.2.1")

	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.8.0")
	implementation("co.yml:ycharts:2.1.0")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.3.0")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
	androidTestImplementation(platform("androidx.compose:compose-bom:2026.05.00"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest:1.11.1")
}