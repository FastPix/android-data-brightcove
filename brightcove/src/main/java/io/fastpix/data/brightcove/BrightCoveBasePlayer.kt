package io.fastpix.data.brightcove

import android.content.pm.ActivityInfo
import androidx.media3.exoplayer.ExoPlayer
import com.brightcove.player.display.ExoPlayerVideoDisplayComponent
import com.brightcove.player.event.Event
import com.brightcove.player.event.EventListener
import com.brightcove.player.event.EventType
import com.brightcove.player.view.BrightcoveExoPlayerVideoView
import com.brightcove.player.view.BrightcovePlayer
import io.fastpix.data.entity.CustomerDataEntity
import io.fastpix.data.entity.CustomerPlayerDataEntity
import io.fastpix.data.entity.CustomerVideoDataEntity
import io.fastpix.data.entity.CustomerViewDataEntity
import io.fastpix.data.exo.FastPixBaseMedia3Player
import io.fastpix.data.request.CustomOptions
import io.fastpix.data.request.MediaPresentation
import java.util.UUID

abstract class BrightcoveBase : BrightcovePlayer(), EventListener {

    protected lateinit var brightcoveVideoView: BrightcoveExoPlayerVideoView

    private var fastpixStatsExoPlayer: FastPixBaseMedia3Player? = null

    protected var customOptions: CustomOptions = CustomOptions()

    companion object {
        var videoConfig: VideoConfig = VideoConfig()
        var playerConfig: PlayerConfig = PlayerConfig()
        var viewConfig: ViewConfig = ViewConfig()

    }

    fun initializeAnalytics() {
        brightcoveVideoView.eventEmitter.on(EventType.DID_SET_VIDEO, this)
        brightcoveVideoView.eventEmitter.on(EventType.ENTER_FULL_SCREEN, this)
        brightcoveVideoView.eventEmitter.on(EventType.EXIT_FULL_SCREEN, this)
        brightcoveVideoView.eventEmitter.on(EventType.COMPLETED, object : EventListener {
            override fun processEvent(event: Event) {
                finish()
            }
        })
        brightcoveVideoView.eventEmitter.on(EventType.DID_SET_VIDEO, object : EventListener {
            override fun processEvent(event: Event) {
                brightcoveVideoView.brightcoveMediaController.hide()
            }
        })
        brightcoveVideoView.eventEmitter.on(EventType.PLAY, object : EventListener {
            override fun processEvent(event: Event) {
                brightcoveVideoView.brightcoveMediaController.show()
            }
        })
        brightcoveVideoView.eventEmitter.on(EventType.SET_VIDEO_STILL, object : EventListener {
            override fun processEvent(event: Event) {
                event.preventDefault()
                event.stopPropagation()
            }
        })
    }

    override fun processEvent(event: Event) {
        when (event.type) {
            EventType.DID_SET_VIDEO -> {
                try {
                    val videoDisplayComponent =
                        brightcoveVideoView.videoDisplay as ExoPlayerVideoDisplayComponent
                    val video = brightcoveVideoView.currentVideo
                    val exoPlayer: ExoPlayer = videoDisplayComponent.exoPlayer

                    setupFastPixAnalytics(exoPlayer)

                } catch (e: Exception) {
                }
            }

            EventType.ENTER_FULL_SCREEN -> {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

                fastpixStatsExoPlayer?.presentationChange(MediaPresentation.FULLSCREEN)
            }

            EventType.PLAY -> {
            }

            EventType.EXIT_FULL_SCREEN -> {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                fastpixStatsExoPlayer?.presentationChange(MediaPresentation.NORMAL)
            }
        }
    }

    private fun setupFastPixAnalytics(exoPlayer: ExoPlayer) {
        val fastPixConfig = getFastPixConfig() ?: return

        val fastPixPlayerData = CustomerPlayerDataEntity().apply {
            workspaceKey = fastPixConfig.workspaceKey
            playerConfig.playerName?.let { playerName = it }
            playerConfig.playerVersion?.let { playerVersion = it }
            playerConfig.experimentName?.let { experimentName = it }
            playerConfig.pageType?.let { pageType = it }
            playerConfig.playerInitTime?.let { playerInitTime = it }
            playerConfig.subPropertyId?.let { subPropertyId = it }
            playerConfig.viewerUserId?.let { viewerUserId = it }
            playerConfig.playerAutoplayOn?.let { isAutoPlayOn = it }

        }
        val fastPixVideoData = CustomerVideoDataEntity().apply {
            videoTitle = videoConfig.videoTitle ?: "Unknown Video"
            videoConfig.videoCdn?.let { videoCdn = it }
            videoConfig.videoContentType?.let { videoContentType = it }
            videoConfig.videoEncodingVariant?.let { videoEncodingVariant = it }
            videoConfig.videoId?.let { videoId = it }
            videoConfig.videoIsLive?.let { videoIsLive = it }
            videoConfig.videoLanguageCode?.let { videoLanguageCode = it }
            videoConfig.videoProducer?.let { videoProducer = it }
            videoConfig.videoSeries?.let { videoSeries = it }
            videoConfig.videoStreamType?.let { videoStreamType = it }
            videoConfig.videoVariantId?.let { videoVariantId = it }
            videoConfig.videoVariantName?.let { videoVariantName = it }
            videoConfig.videoExperiments?.let { videoExperiments = it }
            videoConfig.sourceUrl?.let { videoSourceUrl = it }
        }
        val fastpixViewData = CustomerViewDataEntity().apply {
            viewSessionId = viewConfig.viewSessionId ?: UUID.randomUUID().toString()
            viewConfig.viewDrmType?.let { viewDrmType = it }
            viewConfig.internalViewSessionId?.let { internalViewSessionId = it }
            viewConfig.internalVideoExperiments?.let { internalVideoExperiments = it }
        }
        val fastPixCustomerData =
            CustomerDataEntity(fastPixPlayerData, fastPixVideoData, fastpixViewData, null)

        val customOptions = CustomOptions().apply {
            beaconDomain = customOptions.beaconDomain ?: "metrix.ws"
        }

        fastpixStatsExoPlayer?.release()
        fastpixStatsExoPlayer = FastPixBaseMedia3Player(
            this,
            exoPlayer,
            fastPixCustomerData,
            customOptions
        )
        fastpixStatsExoPlayer?.setPlayerView(brightcoveVideoView)
    }


    override fun onDestroy() {
        super.onDestroy()
        fastpixStatsExoPlayer?.release()
        fastpixStatsExoPlayer = null
    }

    abstract fun getFastPixConfig(): FastPixConfig?

}

data class VideoConfig(
    var videoTitle: String? = null,
    var sourceUrl: String? = null,
    var videoCdn: String? = null,
    var videoContentType: String? = null,
    var videoDuration: Long? = null,
    var videoEncodingVariant: String? = null,
    var videoId: String? = null,
    var videoIsLive: Boolean? = null,
    var videoLanguageCode: String? = null,
    var videoProducer: String? = null,
    var videoSeries: String? = null,
    var videoStreamType: String? = null,
    var videoVariantId: String? = null,
    var videoVariantName: String? = null,
    var videoExperiments: String? = null
)

data class PlayerConfig(
    var playerName: String? = null,
    var playerVersion: String? = "1.0.0",
    var experimentName: String? = null,
    var pageType: String? = null,
    var playerInitTime: Long? = null,
    var playerNameKey: String? = null,
    var playerVersionKey: String? = null,
    var subPropertyId: String? = null,
    var viewerUserId: String? = null,
    var playerAutoplayOn: Boolean? = null
)

data class ViewConfig(
    var viewSessionId: String? = null,
    var viewDrmType: String? = null,
    var viewDrmTypeKey: String? = null,
    var viewSessionIdKey: String? = null,
    var internalViewSessionId: String? = null,
    var internalVideoExperiments: String? = null
)

data class FastPixConfig(val workspaceKey: String)


