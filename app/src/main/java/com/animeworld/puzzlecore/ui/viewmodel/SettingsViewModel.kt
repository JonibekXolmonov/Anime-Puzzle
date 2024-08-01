package com.animeworld.puzzlecore.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.animeworld.puzzlecore.util.SharedPref
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsUiState(
    val isSoundEnabled: Boolean = false,
    val isMusicEnabled: Boolean = false
)

class SettingsViewModel(private val sharedPref: SharedPref) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState get() = _uiState.asStateFlow()

    fun toggleSound() {
        sharedPref.sound = !sharedPref.sound
        _uiState.update {
            it.copy(isSoundEnabled = sharedPref.sound)
        }
    }

    fun toggleMusic() {
        sharedPref.music = !sharedPref.music
        _uiState.update {
            it.copy(isMusicEnabled = sharedPref.music)
        }
    }

    init {
        _uiState.update {
            it.copy(
                isSoundEnabled = sharedPref.sound,
                isMusicEnabled = sharedPref.music
            )
        }
    }
}

@Suppress("UNCHECKED_CAST")
class SettingsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(SharedPref(context)) as T
    }
}