---
name: Bug Report
about: Create a report to help us improve
title: '[BUG] '
labels: bug
assignees: ''
---

## Bug Description

A clear and concise description of what the bug is.

## Reproduction Steps

1. **Setup Environment**

```groovy
dependencies {
    implementation("io.fastpix.data:media3:X.X.X")
}
```

2. **Code To Reproduce**

```kotlin
class MainActivity : BrightcoveBase() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        brightcoveVideoView = binding.brightcoveVideoView;
        super.onCreate(savedInstanceState)
        initializeAnalytics()

        val videoUrl = "enter_your_stream_url"
        val video = Video.createVideo(
            videoUrl,
            DeliveryType.HLS // Mention your type of delivery here
        )

        videoConfig.videoTitle = "Your Video Title"
        playerConfig.playerVersion = "Your Player Version"
        playerConfig.playerName = "Brightcove Player"
        customOptions.beaconDomain = "mention your beacon domain here"
        videoConfig.videoId = "Your Video Id"
        brightcoveVideoView.add(video)
        brightcoveVideoView.start()

    }

    override fun getFastPixConfig() = FastPixConfig("your_workspace_id")
}
```

3. **Expected Behavior**
```
<!-- A clear and concise description of what you expected to happen.  -->
```

4. **Actual Behavior**
```
<!-- A clear and concise description of what actually happened. -->
```

5. **Environment**

- **SDK Version**: [e.g., 1.2.2]
- **Android Version**: [e.g., Android 12]
- **Min SDK Version**: [e.g., 24]
- **Target SDK Version**: [e.g., 35]
- **Device/Emulator**: [e.g., Pixel 5, Android Emulator]
- **Player**: [e.g., ExoPlayer 2.19.0, VideoView, etc.]
- **Kotlin Version**: [e.g., 2.0.21]

## Code Sample

```kotlin
// Please provide a minimal code sample that reproduces the issue
class MainActivity : BrightcoveBase() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        brightcoveVideoView = binding.brightcoveVideoView;
        super.onCreate(savedInstanceState)
        initializeAnalytics()

        val videoUrl = "enter_your_stream_url"
        val video = Video.createVideo(
            videoUrl,
            DeliveryType.HLS // Mention your type of delivery here
        )

        videoConfig.videoTitle = "Your Video Title"
        playerConfig.playerVersion = "Your Player Version"
        playerConfig.playerName = "Brightcove Player"
        customOptions.beaconDomain = "mention your beacon domain here"
        videoConfig.videoId = "Your Video Id"
        brightcoveVideoView.add(video)
        brightcoveVideoView.start()

    }

    override fun getFastPixConfig() = FastPixConfig("your_workspace_id")
}
```

## Logs/Stack Trace

```
Paste relevant logs or stack traces here
```

## Additional Context

Add any other context about the problem here.

## Screenshots

If applicable, add screenshots to help explain your problem.

