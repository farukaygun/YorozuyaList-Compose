# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Unofficial MyAnimeList Android client. Single Gradle module (`:app`), Kotlin + Jetpack Compose (Material 3 Expressive), min SDK 26 / target 37, Java 21.

## Commands

Windows shell — use `.\gradlew`, not `./gradlew`.

```powershell
.\gradlew assembleDebug          # build debug APK
.\gradlew installDebug           # build + install on connected device
.\gradlew test                   # JVM unit tests (app/src/test)
.\gradlew connectedAndroidTest   # instrumented tests (app/src/androidTest)
.\gradlew testDebugUnitTest --tests "com.farukaygun.yorozuyalist.ExampleUnitTest"   # single test class
.\gradlew lint                   # Android Lint -> app/build/reports/lint-results-debug.html
.\gradlew -q printVersionName    # custom task used by CI
```

No ktlint/detekt/spotless is configured. Test coverage is currently only the AGP template tests.

`app/src/main/java/.../util/Private.kt` holds the MAL OAuth `CLIENT_ID` and **is committed** — don't add secrets there and don't rewrite it.

## Architecture

Clean-architecture layering under `app/src/main/java/com/farukaygun/yorozuyalist/`:

```
data/remote      APIService (interface) + APIServiceImpl — every MAL HTTP call, Ktor
data/remote/dto  *Dto data classes + `fun XDto.toX()` mappers in the SAME file
data/repository  *RepositoryImpl — thin passthrough to APIService, returns DTOs
data/di          Koin modules (Modules.kt) + Ktor client setup (AppModule.kt)
domain/models    domain models (DTO-free), `enums/` carry an `apiName` for wire values
domain/repository  repository interfaces (return DTOs, not domain models)
domain/use_case  one class per operation, `operator fun invoke(...): Flow<Resource<T>>`
presentation/<feature>  ViewModel + State + Event, screens in `views/`
presentation/composables  shared UI; `shimmer_effect/` = loading placeholders
util             Resource, AppError, Constants, SharedPrefsHelper, extensions
```

Data flow: Screen → `viewModel.onEvent(Event)` → UseCase → Repository → APIService → DTO → `.toX()` mapper → domain model → `Resource` → State.

**Adding a feature** touches this whole chain plus registration in `data/di/Modules.kt` (use case as `single`, view model as `viewModel`) and a `Screen` object + `composable` route in `MainActivity`.

### Key conventions

- **`Resource<T>`** (`util/Resource.kt`) — `Success` / `Error(AppError)` / `Loading`. Use cases wrap the call in `flow { emit(Loading); try { emit(Success(repo…toX())) } catch { emit(Error(e.toAppError())) } }`.
- **`AppError`** (`util/AppError.kt`) — sealed error type; `Throwable.toAppError()` maps Ktor/network exceptions. Never surface raw exceptions in state.
- **`BaseViewModel<T>`** (`presentation/base/`) — owns `_state: MutableStateFlow<T>` / exposed `state: StateFlow<T>`, a `jobs` list, and `Flow<Resource<T>>.handleResource(onSuccess, onError, onLoading)` which collects into `viewModelScope`. New view models extend it and override `_state`; call chains are `useCase(...).flowOn(Dispatchers.IO).handleResource(...)` appended to `jobs`.
- **Events** — each feature has a sealed `XEvent`; the view model exposes a single `onEvent(event)` with an exhaustive `when`.
- **Pagination** — MAL returns `paging.next` as a full URL. Use cases/repositories/APIService therefore expose *overloads*: parameterized (first page) and `(url: String)` (next page). `loadMore()` merges with `distinctBy { it.node.id }`.
- **Navigation** — routes live in `presentation/Screen.kt`; nav args are string params like `SCREEN_TYPE_PARAM` / `MEDIA_ID_PARAM`, read via `SavedStateHandle` in the view model. `Screen.isBottomAppBarVisible` drives the bottom bar; `MainActivity` owns the `NestedScrollConnection` that hides bars on scroll.
- **DI** — Koin, no annotations/KSP codegen for DI. Constructor injection everywhere; `by inject()` only in `MainActivity`.
- **Auth** — MAL OAuth2 PKCE. Login opens a Custom Tab and returns via deep link `app://com.farukaygun.yorozuyalist` (`MainActivity.onNewIntent` → `LoginEvent.ParseIntentData`). Tokens live in SharedPreferences (`SharedPrefsHelper`, `PrefKeys`) and are refreshed transparently by the Ktor `Auth`/`bearer` plugin in `AppModule.provideKtorClient` — do not hand-roll token handling in API calls.
- Serialization is **Gson** (`@SerializedName`) via Ktor content negotiation, not kotlinx.serialization.
- Source formatting is tabs; some inline comments are in Turkish.

### Where to look first

| Task | File |
|---|---|
| Add/change an endpoint | `data/remote/APIService.kt` + `APIServiceImpl.kt` |
| Change requested MAL fields | the `parameter("fields", …)` string in `APIServiceImpl` |
| Routes / nav graph / bar visibility | `presentation/MainActivity.kt`, `presentation/Screen.kt` |
| Wire a new class into DI | `data/di/Modules.kt` |
| HTTP client, auth, token refresh | `data/di/AppModule.kt` |
| Base URLs, preview sample data | `util/Constants.kt` |
| Theme / colors / typography | `ui/theme/` |

## CI

`.github/workflows/dev build.yml` — on push to `dev` and on published releases: builds `assembleDebug assembleRelease` on `windows-latest`, tags `v<versionName>.<run_number>d`, and publishes the debug APK as a prerelease. Bumping `versionCode`/`versionName` in `app/build.gradle.kts` changes the release tag.
