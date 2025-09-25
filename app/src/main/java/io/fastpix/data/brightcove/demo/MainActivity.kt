package io.fastpix.data.brightcove.demo

import android.os.Bundle
import com.brightcove.player.model.DeliveryType
import com.brightcove.player.model.Video
import io.fastpix.data.brightcove.BrightcoveBase
import io.fastpix.data.brightcove.FastPixConfig
import io.fastpix.data.brightcove.demo.databinding.ActivityMainBinding

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