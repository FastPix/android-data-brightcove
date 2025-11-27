# FastPix Resumable Uploads SDK - Documentation PR

## Documentation Changes

### What Changed
- [ ] New documentation added
- [ ] Existing documentation updated
- [ ] Documentation errors fixed
- [ ] Code examples updated
- [ ] Links and references updated

### Files Modified
- [ ] README.md
- [ ] docs/ files
- [ ] USAGE.md
- [ ] CONTRIBUTING.md
- [ ] Other: _______________

### Summary
**Brief description of changes:**

<!-- What documentation was added, updated, or fixed? -->

### Code Examples
```kotlin 
// If you added/updated code examples, include them here
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

### Testing
- [ ] All code examples tested
- [ ] Links verified
- [ ] Grammar checked
- [ ] Formatting consistent

### Review Checklist
- [ ] Content is accurate
- [ ] Code examples work
- [ ] Links are working
- [ ] Grammar is correct
- [ ] Formatting is consistent

---

**Ready for review!**
