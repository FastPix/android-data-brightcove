package io.fastpix.data.brightcove.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.brightcove.player.display.ExoPlayerVideoDisplayComponent
import com.brightcove.player.model.DeliveryType
import com.brightcove.player.model.Video
import io.fastpix.data.brightcove.demo.databinding.ActivityMainBinding
import io.fastpix.data.domain.model.CustomDataDetails
import io.fastpix.data.domain.model.PlayerDataDetails
import io.fastpix.data.domain.model.VideoDataDetails
import io.fastpix.data.exo.FastPixBaseMedia3Player
import java.util.UUID

@UnstableApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var fastPixDataSDK: FastPixBaseMedia3Player? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        val videoUrl = "https://stream.fastpix.io/ca854fd4-a3d0-4525-bd43-80de50887e1a.m3u8"
        val video = Video.createVideo(
            videoUrl,
            DeliveryType.HLS // Mention your type of delivery here
        )
        binding.brightcoveVideoView.add(video)
        binding.brightcoveVideoView.start()
        val videoDisplayComponent =
            binding.brightcoveVideoView.videoDisplay as ExoPlayerVideoDisplayComponent
        val exoPlayer: ExoPlayer = videoDisplayComponent.exoPlayer
        setupFastPixAnalytics(exoPlayer)

    }

    private fun setupFastPixAnalytics(exoPlayer: ExoPlayer) {
        val videoDataDetails = VideoDataDetails(
            videoId = UUID.randomUUID().toString(),
            videoTitle = "My Video"
        ).apply {
            videoSeries = "Demo Series"
            videoProducer = "Demo Producer"
            videoContentType = "VOD"
            // ..etc
        }
        // Optional
        val playerDataDetails = PlayerDataDetails(
            playerName = "media3",
            playerVersion = "latest-version"
        )
        // Optional
        val customDataDetails = CustomDataDetails().apply {
            customField1 = "Custom Value 1"
            customField2 = "Custom Value 2"
            // ..etc
        }

        fastPixDataSDK = FastPixBaseMedia3Player(
            context = this,
            playerView = binding.brightcoveVideoView,
            exoPlayer = exoPlayer,
            workSpaceId = "1109888358169935873",
            playerDataDetails = playerDataDetails,
            videoDataDetails = videoDataDetails,
            customDataDetails = customDataDetails
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        fastPixDataSDK?.release()
    }

}