package com.animeworld.puzzlecore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.animeworld.puzzlecore.ui.screen.NavGraphs
import com.animeworld.puzzlecore.ui.theme.AnimePuzzleTheme
import com.animeworld.puzzlecore.ui.viewmodel.MainActivityViewModel
import com.animeworld.puzzlecore.util.SharedPref
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimePuzzleTheme {
                val navController = rememberNavController()

                val navHostEngine = rememberNavHostEngine()

                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    navController = navController,
                    engine = navHostEngine
                )
            }
        }

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        sharedPref = SharedPref(this)

        if (sharedPref.sound) {
            viewModel.startService()
        }
    }

    fun startBackgroundMusicService() {
        viewModel.startService()
    }

    fun stopBackgroundMusicService() {
        viewModel.stopService()
    }

    fun clickSound() {
        if (sharedPref.music)
            viewModel.clickSound()
    }

    override fun onStart() {
        if (sharedPref.sound) {
            viewModel.resumeService()
        }
        super.onStart()
    }

    override fun onPause() {
        if (sharedPref.sound) {
            viewModel.pauseService()
        }
        super.onPause()
    }
}