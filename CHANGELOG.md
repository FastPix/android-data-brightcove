# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2026-04-20

### Added

- Initial public release of the **FastPix Data — Brightcove** Android sample application.
- Demo integration that wires **Brightcove Player** (`BrightcoveVideoView`) to **FastPix Data** via `FastPixBaseMedia3Player`, using the underlying **ExoPlayer** instance from `ExoPlayerVideoDisplayComponent`.
- Example configuration for `VideoDataDetails`, optional `PlayerDataDetails`, and optional `CustomDataDetails`, plus `release()` in `onDestroy()` for resource cleanup.
- Project setup: Kotlin, View Binding / Data Binding, Material components, `compileSdk` / `targetSdk` 36, `minSdk` 24, Java 11 bytecode.
- Dependencies aligned for this demo: Brightcove `exoplayer2` artifact **10.2.2**, FastPix `io.fastpix.data:media3` **1.2.4**, and AndroidX libraries as defined in `gradle/libs.versions.toml`.
- Documentation in `README.md` for prerequisites, installation (GitHub Packages Maven), and usage entry point at `MainActivity.kt`.

[1.0.0]: https://github.com/FastPix/android-data-brightcove
