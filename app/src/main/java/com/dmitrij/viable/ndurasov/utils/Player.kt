package com.dmitrij.viable.ndurasov.utils

import android.content.Context
import android.media.AudioManager
import android.net.Uri
import android.support.v4.media.MediaDescriptionCompat
import androidx.media.AudioAttributesCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

data class PlayerState(var window: Int = 0,
                       var position: Long = 0,
                       var whenReady: Boolean = true)

class PlayerHolder(private val context: Context,
                   private val playerState: PlayerState,
                   private val playerView: PlayerView) : AnkoLogger {
    val audioFocusPlayer: ExoPlayer

    // Create the player instance.
    init {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val audioAttributes = AudioAttributesCompat.Builder()
            .setContentType(AudioAttributesCompat.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributesCompat.USAGE_MEDIA)
            .build()
        audioFocusPlayer = AudioFocusWrapper(
            audioAttributes,
            audioManager,
            ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector())
                .also { playerView.player = it }
        )
        info { "SimpleExoPlayer created" }
    }

    private fun buildMediaSource(): MediaSource {
        val uriList = mutableListOf<MediaSource>()
        mediaCatalog.forEach {
            uriList.add(createExtractorMediaSource(it.mediaUri!!))
        }
        return ConcatenatingMediaSource(*uriList.toTypedArray())
    }

    private fun createExtractorMediaSource(uri: Uri): MediaSource {
        return ExtractorMediaSource.Factory(
            DefaultDataSourceFactory(context, "exoplayer-learning"))
            .createMediaSource(uri)
    }

    // Prepare playback.
    fun start() {
        // Load media.
        audioFocusPlayer.prepare(buildMediaSource())
        // Restore state (after onResume()/onStart())
        with(playerState) {
            // Start playback when media has buffered enough
            // (whenReady is true by default).
            audioFocusPlayer.playWhenReady = whenReady
            audioFocusPlayer.seekTo(window, position)
            // Add logging.
            attachLogging(audioFocusPlayer)
        }
        info { "SimpleExoPlayer is started" }
    }

    // Stop playback and release resources, but re-use the player instance.
    fun stop() {
        with(audioFocusPlayer) {
            // Save state
            with(playerState) {
                position = currentPosition
                window = currentWindowIndex
                whenReady = playWhenReady
            }
            // Stop the player (and release it's resources). The player instance can be reused.
            stop(true)
        }
        info { "SimpleExoPlayer is stopped" }
    }

    // Destroy the player instance.
    fun release() {
        audioFocusPlayer.release() // player instance can't be used again.
        info { "SimpleExoPlayer is released" }
    }

    /**
     * For more info on ExoPlayer logging, please review this
     * [codelab](https://codelabs.developers.google.com/codelabs/exoplayer-intro/#5).
     */
    private fun attachLogging(exoPlayer: ExoPlayer) {
        // Show toasts on state changes.
        exoPlayer.addListener(object : Player.DefaultEventListener() {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_ENDED -> {
                    }
                    Player.STATE_READY -> when (playWhenReady) {
                        true -> {

                        }
                    }
                }
            }
        })
        // Write to log on state changes.
        exoPlayer.addListener(object : Player.DefaultEventListener() {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                info { "playerStateChanged: ${getStateString(playbackState)}, $playWhenReady" }
            }

            /*override fun onPlayerError(error: ExoPlaybackException?) {
                info { "playerError: $error" }
            }*/

            fun getStateString(state: Int): String {
                return when (state) {
                    Player.STATE_BUFFERING -> "STATE_BUFFERING"
                    Player.STATE_ENDED -> "STATE_ENDED"
                    Player.STATE_IDLE -> "STATE_IDLE"
                    Player.STATE_READY -> "STATE_READY"
                    else -> "?"
                }
            }
        })

    }

}

val mediaCatalog: List<MediaDescriptionCompat> = listOf(
    with(MediaDescriptionCompat.Builder()) {
        setDescription("Portico")
        setMediaId("1")
        //setMediaUri(Uri.parse("file:///android_asset/portico.mp4"))
        setMediaUri(Uri.parse("https://gitlab.com/tegorkrsk/sharedtempfiles/-/raw/main/portico.mp4"))
        setTitle("Portico")
        setSubtitle("3D model")
        build()
    }
)
