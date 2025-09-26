# Introduction

This SDK simplifies the integration process with Brightcove Player on Android, enabling the seamless collection of player analytics. It automatically tracks video performance metrics, making the data accessible on the FastPix Dashboard for monitoring and analysis.

# Key Features:

**Automatic Video Analytics:** Seamless integration with Brightcove Player events 
**Dynamic Beacon Domain:** Automatic beacon domain selection for staging and production environments 
**Easy Configuration:** Simple configuration objects for quick setup 
**Resource Management:** Automatic cleanup of analytics resources 
**User Engagement Metrics:** Capture detailed viewer interaction data 
**Real-time Performance Analysis:** Monitor playback quality and streaming diagnostics 
**Error Handling:** Robust error reporting and diagnostics 

# Prerequisites:
- Android Studio Arctic Fox or later
- Minimum Android SDK 21+
- Brightcove Player integrated into your project
- FastPix Data Brightcove Player SDK added as a dependency
- Generate a GitHub Personal Access Token (PAT) from Your GitHub account

## Getting started with FastPix:

To track and analyze video performance, initialize the FastPix Data SDK with your Workspace key:

1. **[Access the FastPix Dashboard](https://dashbord.fastpix.io)**: Log in and navigate to the Workspaces section.
2. **Locate Your Workspace Key**: Copy the Workspace Key for client-side monitoring. Included in your Android application's code wherever you want to track video performance and analytics.



# Installation:
Add our maven repository to your **settings.gradle**:
```groovy
repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven {
            url =uri("https://repo.brightcove.com/releases")
        }
        maven {
            url = uri("https://maven.pkg.github.com/FastPix/android-data-media3-player-sdk")
            url = uri("https://maven.pkg.github.com/FastPix/android-data-brightcove")
            credentials {
                username = "your_github_username"
                password = "your_personal_access_token"
            }

        }
    }
```
Add the FastPix Data Core SDK dependencie to your **build.gradle**:
```gradle
dependencies {
    //check for the latest versions
    implementation("io.fastpix.data:media3:1.0.0")
    implementation("com.brightcove.player:exoplayer2:10.2.3")
    implementation("io.fastpix.data:brightcoveplayer:1.0.0")
}
```

## Usage 

Make sure Brightcove Player is installed and integrated with your project as part of the FastPix data setup. You can initialize Brightcove with a PlayerView in your Android application to enable seamless functionality. 

### Import statements
 ``` kotlin
   import io.fastpix.data.brightcove.BrightcoveBase
   import io.fastpix.data.brightcove.FastPixConfig
 ```

Integrate the following Kotlin code into your application to configure Brightcove Player with FastPix.


# Basic example of Including Custom Data and metadata

**workspace_id** is the only mandatory field. Providing additional metadata can greatly enhance analytics and reporting.  
**CustomerData and CustomOptions :** Create the CustomerPlayerData and CustomerVideoData objects as appropriate for your current playback 
``` Kotlin
class MainActivity : BrightcoveBase() { 

    private lateinit var binding: ActivityMainBinding 

    override fun onCreate(savedInstanceState: Bundle?) { 

        binding = ActivityMainBinding.inflate(layoutInflater) 
        setContentView(binding.root) 
        brightcoveVideoView = binding.brightcoveVideoView 
        super.onCreate(savedInstanceState) 

        // Required for fastpix metrics
        initializeAnalytics() 

        val videoUrl = "Your Stream Url" 
        val video = Video.createVideo(videoUrl, DeliveryType.HLS) 

        // Configure your video data
        videoConfig.sourceUrl = videoUrl 
        videoConfig.videoTitle = "Brightcove Testing" 

        configure your player data 
        playerConfig.playerVersion = "2.1.0" 
        playerConfig.playerName = "Brightcove Player"

        //configure your view data 
       viewSessionId = UUID.randomUUID().toString()  
        viewDrmType = "Your DRM Type" 

        // Required beacon domain or else it will take "metrix.ws" by default
        customOptions.beaconDomain="metrix.ninja"

        brightcoveVideoView.add(video) 
        brightcoveVideoView.start() 

    } 
    // Required to call this funtion for the Fastpix analytics
    override fun getFastPixConfig() = FastPixConfig("Your Workspace Key") 

} 

```

### XML

Include the XML code below to integrate Brightcove with FastPix:

```xml
<com.brightcove.player.view.BrightcoveExoPlayerVideoView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/brightcove_video_view"
    android:layout_gravity="center" />

```
# Detailed Usage:

For more detailed steps and advanced usage, please refer to the official [FastPix Documentation](https://docs.fastpix.io/docs/monitor-brightcove-player-android).
