# Just Use Fucking Kotlin

One codebase. Five platforms. Zero excuses.

[![Web](https://img.shields.io/badge/Web-Live-brightgreen)](https://justusefuckingkotlin.com)
[![Android](https://img.shields.io/badge/Android-Beta-yellow)](https://play.google.com/store/apps/details?id=com.ifochka.jufk)
[![iOS](https://img.shields.io/badge/iOS-TestFlight-blue)](https://testflight.apple.com/join/sENnMKjM)
[![Desktop](https://img.shields.io/badge/Desktop-Soon-lightgrey)]()
[![CLI](https://img.shields.io/badge/CLI-Soon-lightgrey)]()

## What Is This?

A proof that **Kotlin Multiplatform is production-ready**. One Kotlin codebase deployed to:

| Platform | Technology | Status | Try It |
|----------|------------|--------|--------|
| 🌐 Web | Kotlin/WASM + Compose Multiplatform | Live | [justusefuckingkotlin.com](https://justusefuckingkotlin.com) |
| 🤖 Android | Kotlin/JVM + Jetpack Compose | Beta | [Play Store](https://play.google.com/store/apps/details?id=com.ifochka.jufk) |
| 🍎 iOS | Kotlin/Native + Compose Multiplatform | TestFlight | [TestFlight](https://testflight.apple.com/join/sENnMKjM) |
| 🖥️ Desktop | Kotlin/JVM + Compose Desktop | Coming Soon | - |
| ⌨️ CLI | Kotlin/Native | Coming Soon | - |

## How It Was Made

This entire project was built live on YouTube. Every line of code, every deployment, every mistake.

**Watch the full series:** [JUFK Tutorial Playlist](https://www.youtube.com/playlist?list=PLGS6AZIpM4eHR6EWt6IZ8HeizdP8SOCxU)

| Session | What You'll Learn |
|---------|-------------------|
| 1 | Kotlin Multiplatform from scratch to production in 15 minutes |
| 2 | KMP plugin migration & Gradle build optimization |
| 3 | CI quality gates with ktlint, detekt & test/prod environments |
| 4.1 | Build-time version injection with BuildKonfig |
| 4.2 | Theme system, social links & footer components |
| 5 | iOS deployment to TestFlight with fastlane & GitHub Actions |
| 6 | Android deployment to Play Store + complete release pipeline |

## Tech Stack

- **Language:** Kotlin 2.3.0
- **UI Framework:** Compose Multiplatform 1.9.3
- **Build System:** Gradle with Version Catalogs
- **CI/CD:** GitHub Actions
- **Web Hosting:** Cloudflare Pages (WASM)
- **iOS Distribution:** fastlane + TestFlight
- **Android Distribution:** fastlane + Play Store
- **Code Quality:** ktlint + detekt

## Project Structure

```
├── composeApp/          # Shared Kotlin Multiplatform code
│   ├── commonMain/      # Shared UI and logic
│   ├── androidMain/     # Android-specific code
│   ├── iosMain/         # iOS-specific code
│   ├── wasmJsMain/      # Web-specific code
│   └── jvmMain/         # Desktop-specific code
├── androidApp/          # Android application module
├── iosApp/              # iOS Xcode project
├── fastlane/            # iOS & Android deployment automation
└── .github/workflows/   # CI/CD pipelines
```

## Local Development

```bash
# Clone the repository
git clone https://github.com/adjorno/JUFK.git
cd JUFK

# Run on Web (WASM)
./gradlew :composeApp:wasmJsBrowserRun

# Run on Android
./gradlew :androidApp:installDebug

# Run on Desktop
./gradlew :composeApp:run

# Run iOS (requires macOS + Xcode)
open iosApp/iosApp.xcodeproj
```

## CI/CD Pipeline

Every merge to `main`:
- Deploys Web to [test.justusefuckingkotlin.com](https://test.justusefuckingkotlin.com)
- Uploads iOS to TestFlight (internal)
- Uploads Android to Play Store (alpha track)

Every version tag (`v*`):
- Deploys Web to [justusefuckingkotlin.com](https://justusefuckingkotlin.com)
- Uploads iOS to App Store
- Uploads Android to Play Store (production)
- Creates GitHub Release

## Links

- 🌐 **Website:** [justusefuckingkotlin.com](https://justusefuckingkotlin.com)
- 📺 **YouTube:** [JUFK Tutorial Series](https://www.youtube.com/playlist?list=PLGS6AZIpM4eHR6EWt6IZ8HeizdP8SOCxU)
- 🐦 **Twitter/X:** [@adjorno](https://x.com/adjorno)
- 💼 **LinkedIn:** [Mykhailo Dorokhin](https://www.linkedin.com/in/mykhailo-dorokhin-0b99305a)

## Contributing

We're an open-source project and love hearing from the community! Feel free to:

- Open **issues** for bugs you find
- Submit **feature requests** for things you'd like to see
- Start **discussions** about Kotlin Multiplatform

Due to the rise of coding agents, we can't accept pull requests without prior verbal agreement. If you'd like to contribute code, please reach out first via [Twitter/X](https://x.com/adjorno) or open an issue to discuss.

## License

Apache 2.0

---

*Just use fucking Kotlin. Period.*
