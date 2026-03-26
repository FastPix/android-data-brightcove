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
        maven {
            url = uri("https://maven.pkg.github.com/FastPix/android-data-androidXmedia3")
            credentials {
                username = "your_github_username"
                password = "your_personal_access_token"
            }

        }
    }
```
Add the FastPix Data Core SDK dependencie to your **build.gradle**:
To know the current FastPix Media3 Version. Visit this link: https://github.com/FastPix/android-data-androidXmedia3
```gradle
dependencies {
    //check for the latest versions
    implementation("io.fastpix.data:media3:current_version")
}
```

## Usage 
For usage do check [MainActivity.kt](app/src/main/java/io/fastpix/data/brightcove/demo/MainActivity.kt) module of this project.
