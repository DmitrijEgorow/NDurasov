package com.dmitrij.viable.ndurasov.ui

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.dmitrij.viable.ndurasov.R
import com.dmitrij.viable.ndurasov.databinding.ActivityMainBinding
import com.dmitrij.viable.ndurasov.utils.PlayerHolder
import com.dmitrij.viable.ndurasov.utils.PlayerState
import com.dmitrij.viable.ndurasov.utils.mediaCatalog
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator

import dagger.hilt.android.AndroidEntryPoint

import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mediaSessionCompat: MediaSessionCompat by lazy { createMediaSession() }
    private val mediaSessionConnector: MediaSessionConnector by lazy {
        createMediaSessionConnector()
    }
    private val playerState by lazy { PlayerState() }
    private lateinit var playerHolder: PlayerHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        overridePendingTransition(R.anim.change_fade_fragment, R.anim.change_fade_out_fragment)

        val videoViewModel: VideoViewModel = ViewModelProvider(this)
            .get(VideoViewModel::class.java)
        videoViewModel.setProgress(0)

        binding.videoView.setVisibility(View.INVISIBLE)
        createMediaSession()
        createMediaSessionConnector()
        createPlayer()

        videoViewModel.getProgress()!!.observe(this) { activated ->
            if (activated.equals(0)) {
                binding.videoView.setVisibility(View.INVISIBLE)
                stopPlayer()
                deactivateMediaSession()
            }
            if (activated.equals(1)) {
                binding.videoView.setVisibility(View.VISIBLE)
                startPlayer()
                activateMediaSession()
            }
        }
    }

    // MediaSession related functions.
    private fun createMediaSession(): MediaSessionCompat = MediaSessionCompat(
        this,
        "com.dmitrij.viable.ddurasov"
    )

    private fun createMediaSessionConnector(): MediaSessionConnector =
        MediaSessionConnector(mediaSessionCompat).apply {
            setQueueNavigator(object : TimelineQueueNavigator(mediaSession) {

                override fun getMediaDescription(
                    player: Player,
                    windowIndex: Int
                ): MediaDescriptionCompat {
                    return mediaCatalog[windowIndex]
                }
            })
        }

    // MediaSession related functions.
    private fun activateMediaSession() {
        mediaSessionConnector.setPlayer(playerHolder.audioFocusPlayer)
        mediaSessionCompat.isActive = true
    }

    private fun deactivateMediaSession() {
        mediaSessionConnector.setPlayer(null)
        mediaSessionCompat.isActive = false
    }

    private fun releaseMediaSession() {
        mediaSessionCompat.release()
    }

    // ExoPlayer related functions.
    private fun createPlayer() {
        playerHolder = PlayerHolder(this, playerState, binding.exoplayerviewActivityVideo)
    }

    private fun startPlayer() {
        playerHolder.start()
    }

    private fun stopPlayer() {
        playerHolder.stop()
    }

    private fun releasePlayer() {
        playerHolder.release()
    }

    companion object {
        @JvmStatic
        fun setLocale(activity: FragmentActivity, languageCode: String) {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            val resources: Resources = activity.getResources()
            val config = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }
}
