package com.animeworld.puzzlecore.util

import android.content.Context
import com.animeworld.puzzlecore.util.Constants.MUSIC
import com.animeworld.puzzlecore.util.Constants.SHARED_PREF
import com.animeworld.puzzlecore.util.Constants.SOUND

class SharedPref(context: Context) {

    private val sharedPref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

    var sound: Boolean
        set(value) {
            sharedPref.edit().putBoolean(SOUND, value).apply()
        }
        get() = sharedPref.getBoolean(SOUND, false)

    var music: Boolean
        set(value) {
            sharedPref.edit().putBoolean(MUSIC, value).apply()
        }
        get() = sharedPref.getBoolean(MUSIC, false)
}