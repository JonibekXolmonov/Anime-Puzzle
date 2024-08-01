package com.animeworld.puzzlecore.ui.viewmodel

import android.app.Application
import android.content.Intent
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import com.animeworld.puzzlecore.R
import com.animeworld.puzzlecore.service.MusicService


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private var mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.tap_sound)

    fun startService() {
        Intent(context, MusicService::class.java).also { intent ->
            intent.action = MusicService.START_ACTION
            context.startService(intent)
        }
    }

    fun stopService() {
        Intent(context, MusicService::class.java).also { intent ->
            intent.action = MusicService.STOP_ACTION
            context.stopService(intent)
        }
    }

    fun resumeService() {
        Intent(context, MusicService::class.java).also { intent ->
            intent.action = MusicService.RESUME_ACTION
            context.startService(intent)
        }
    }

    fun pauseService() {
        Intent(context, MusicService::class.java).also { intent ->
            intent.action = MusicService.PAUSE_ACTION
            context.startService(intent)
        }
    }

    fun clickSound() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            mediaPlayer.seekTo(0) // Rewind to avoid starting from halfway next time
        }
        mediaPlayer.start()
    }
}