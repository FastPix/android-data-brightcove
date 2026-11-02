[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android-brightgreen.svg)](https://developer.android.com)
[![Language](https://img.shields.io/badge/language-Kotlin-7F52FF.svg)](https://kotlinlang.org)

# FastPix Video Data SDK for Brightcove Player - monitor playback performance and QoE on Android

The **FastPix Video Data SDK for Brightcove Player** is a third-party QoE (quality-of-experience) monitoring SDK that tracks performance, quality of experience, and user interactions for the **Brightcove Player on Android**. It attaches to the `ExoPlayer` inside Brightcove's player and reports viewer engagement and playback metrics - startup time (time to stream start), rebuffering, error rates, average bitrate, and stall rates - to the [FastPix dashboard](https://dashboard.fastpix.com) in real time.

It is **not a video player** and it does not replace Brightcove Player. It is the monitoring layer you add alongside Brightcove's player, the same way you would add Mux Data - a third-party Video Data SDK that complements (and goes beyond) Brightcove's built-in analytics module.

**Works with:** Brightcove Player for Android · ExoPlayer / AndroidX Media3 · Android 7.0+ (API 24) · Kotlin and Java · `io.fastpix.data:media3`

[FastPix documentation](https://fastpix.com/docs/video-data/overview) · [Create a free account](https://dashboard.fastpix.com/signup?utm_source=github&utm_medium=referral&utm_campaign=github_signup)

## Start here

Follow these in order:

1. [Why FastPix for Brightcove monitoring](#why-fastpix)
2. [Built-in Brightcove analytics vs FastPix](#built-in-brightcove-analytics-vs-fastpix)
3. [What you can track](#what-you-can-track)
4. [Who it's for](#who-its-for)
5. [Prerequisites](#prerequisites)
6. [Get your FastPix workspace key](#get-your-fastpix-workspace-key)
7. [Install the SDK](#installation)
8. [Attach analytics to your Brightcove player](#usage)
9. [Which FastPix SDK for which Android player](#which-fastpix-sdk-for-which-android-player)
10. [FAQ](#faq)

## Why FastPix

Brightcove Player exposes playback events, and Brightcove's Studio module gives you built-in QoE analytics - but if you want your Brightcove playback data in the same place as your web, iOS, and other players, with your own metadata and custom dimensions, a third-party Video Data SDK is the way to get it. FastPix:

- **Monitors Brightcove playback in real time.** Startup time, rebuffering, error rates, bitrate, and stall rates as they happen on real devices.
- **Attaches to Brightcove's own ExoPlayer.** The SDK reads the `ExoPlayer` that Brightcove Player already runs, so you keep Brightcove's player and add monitoring with a few lines.
- **One data model across platforms.** The same FastPix Video Data metrics come from web, iOS, Android, and other player integrations, so you compare experiences like for like.
- **Your metadata, your dashboard.** Tag views with video, player, and custom fields and analyze them in the FastPix dashboard.

## Built-in Brightcove analytics vs FastPix

Brightcove has a built-in Analytics and QoE module in Video Cloud Studio that tracks views, engagement, and quality of experience (startup time, rebuffering, error rates) at no extra charge. FastPix is a **third-party monitoring SDK** you run alongside it when you want:

- Brightcove playback data unified with your other FastPix-monitored players (web, iOS, ExoPlayer, Media3, and more).
- Custom dimensions and your own metadata on every view.
- One dashboard, API, and alerting surface across every platform you ship.

Use Brightcove's built-in module, FastPix, or both - they are not mutually exclusive.

## What you can track

- **Automatic video analytics** - integrates with Brightcove Player events.
- **Dynamic beacon domain** - automatic beacon domain selection for staging and production environments.
- **Easy configuration** - simple configuration objects for quick setup.
- **Resource management** - automatic cleanup of analytics resources.
- **User engagement metrics** - capture detailed viewer interaction data.
- **Real-time performance analysis** - monitor playback quality and streaming diagnostics.
- **Error handling** - robust error reporting and diagnostics.

These map to the core QoE questions teams ask about Brightcove playback: how fast it starts, how often and how long it rebuffers, which bitrates play, how many stalls occur, and which errors viewers hit.

## Who it's for

- **Teams running Brightcove Player on Android** who want production QoE monitoring without building an analytics pipeline.
- **Broadcasters and OTT/media apps on Brightcove** that need to catch rebuffering, slow startup, and playback errors before users churn.
- **Anyone standardizing playback analytics across platforms** who wants Brightcove data in the same FastPix dashboard as their web and iOS players.

## Prerequisites

- Android Studio Arctic Fox or later
- Minimum Android SDK 21+
- **Brightcove Player integrated into your project** (including Brightcove's own Maven repository, `https://repo.brightcove.com/releases`) - this SDK attaches to it
- A GitHub Personal Access Token (PAT) with the `read:packages` scope, to pull the SDK from GitHub Packages
- A FastPix account and workspace key (see below)

## Get your FastPix workspace key

To track and analyze video performance, initialize the FastPix Data SDK with your workspace key:

1. **[Open the FastPix Dashboard](https://dashboard.fastpix.com)** and go to the Workspaces section (or [set up a workspace](https://fastpix.com/docs/getting-started/set-up-a-workspace) if you do not have one, and [create a free account](https://dashboard.fastpix.com/signup?utm_source=github&utm_medium=referral&utm_campaign=github_signup) first if needed).
2. **Copy your workspace key** for client-side monitoring. You will pass it into the SDK wherever you want to track video performance and analytics.

## Installation

Add our maven repository to your **settings.gradle**:
```groovy
repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/FastPix/android-data-androidXmedia3")
            credentials {
                username = "your_github_username"
                password = "your_personal_access_token"
            }

        }
    }
```

> **Why `android-data-androidXmedia3` and `io.fastpix.data:media3`?** Brightcove Player on Android runs on ExoPlayer / AndroidX Media3 under the hood, so the Brightcove integration is delivered through FastPix's Media3 Video Data SDK. That is expected - you pull the `media3` package even though you are monitoring Brightcove.

Add the FastPix Data Core SDK dependencie to your **build.gradle**:
To know the current FastPix Media3 Version. Visit this link: https://github.com/FastPix/android-data-androidXmedia3
```gradle
dependencies {
    //check for the latest versions
    implementation("io.fastpix.data:media3:current_version")
}
```

Replace `current_version` with the latest version listed on the [android-data-androidXmedia3](https://github.com/FastPix/android-data-androidXmedia3) repository.

## Usage

The integration flow is: get the `ExoPlayer` that Brightcove Player is using, then hand it to FastPix. In the sample, Brightcove's `BrightcoveVideoView` exposes its ExoPlayer through `ExoPlayerVideoDisplayComponent`, and that player (plus your workspace key and optional metadata) is passed to `FastPixBaseMedia3Player`. Call `release()` on the SDK when the player is torn down.

For a complete, runnable example, see the [MainActivity.kt](app/src/main/java/io/fastpix/data/brightcove/demo/MainActivity.kt) sample in this repository - it shows creating a Brightcove video, reading its ExoPlayer, and starting FastPix analytics with `VideoDataDetails`, `PlayerDataDetails`, and `CustomDataDetails`.

## Which FastPix SDK for which Android player

FastPix ships a Video Data SDK per player. Pick the one that matches the player your app ships:

| Your Android player | Use this SDK |
| --- | --- |
| **Brightcove Player** | **FastPix/android-data-brightcove** (this repository) |
| **AndroidX Media3** (the current ExoPlayer) | [FastPix/android-data-androidXmedia3](https://github.com/FastPix/android-data-androidXmedia3) (also the package this SDK uses) |
| **Google ExoPlayer** (standalone / legacy) | [FastPix/android-data-exoplayer-sdk](https://github.com/FastPix/android-data-exoplayer-sdk) |
| Any player, shared core layer | [FastPix/android-core-data-sdk](https://github.com/FastPix/android-core-data-sdk) |

## FAQ

**How do I monitor Brightcove Player performance on Android?**
Add the FastPix Video Data SDK, get the `ExoPlayer` that Brightcove Player runs, and pass it to `FastPixBaseMedia3Player` with your workspace key. It tracks startup time, rebuffering, bitrate, stall rates, and errors and reports them to the FastPix dashboard. See [Install the SDK](#installation) and [Attach analytics to your Brightcove player](#usage).

**Does Brightcove have built-in analytics? How is this different?**
Yes - Brightcove has a built-in Analytics and QoE module in Video Cloud Studio. FastPix is a third-party SDK you run alongside it to unify Brightcove playback data with your other players, add custom dimensions, and use one dashboard and API across platforms. See [Built-in Brightcove analytics vs FastPix](#built-in-brightcove-analytics-vs-fastpix).

**What metrics does it track?**
Quality-of-experience and engagement signals: startup time (time to stream start), rebuffering frequency and duration, error rates, average bitrate, stall rates, and user interactions.

**Is this a video player?**
No. It does not play or render video. It is a monitoring wrapper that observes the Brightcove Player you already use.

**Why does a Brightcove SDK install the `media3` package?**
Brightcove Player on Android is built on ExoPlayer / AndroidX Media3, so FastPix monitors it through the Media3 Video Data SDK (`io.fastpix.data:media3`). See the note under [Install the SDK](#installation).

**How does it compare to Mux Data for Brightcove?**
Both are third-party Video Data SDKs for Brightcove Player. FastPix reports the same class of QoE metrics and unifies them with your other FastPix-monitored players in one dashboard.

**Do I need a FastPix account?**
Yes. You need a workspace key from your FastPix account to report data. [Create a free account](https://dashboard.fastpix.com/signup?utm_source=github&utm_medium=referral&utm_campaign=github_signup).

## Documentation

- [Monitor Brightcove Player - FastPix docs](https://fastpix.com/docs/video-data/monitors/brightcove-player)
- [FastPix Video Data overview](https://fastpix.com/docs/video-data/overview)
- [Use custom dimensions](https://fastpix.com/docs/video-data/use-custom-dimensions)
- [Brightcove Player developer docs](https://player.support.brightcove.com/)

## Support

For questions, issues, or feature requests:

- **Email**: support@fastpix.com
- **Documentation**: [FastPix Documentation](https://fastpix.com/docs)
- **SDK Issues**: [GitHub Issues](https://github.com/FastPix/android-data-brightcove/issues)

## License

This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for details.
