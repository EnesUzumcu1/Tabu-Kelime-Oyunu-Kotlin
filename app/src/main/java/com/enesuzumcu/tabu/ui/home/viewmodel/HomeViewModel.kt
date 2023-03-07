package com.enesuzumcu.tabu.ui.home.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.enesuzumcu.tabu.R
import com.enesuzumcu.tabu.data.model.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(@ApplicationContext context: Context): ViewModel() {

    init {
        checkSettings(context)
    }
    private fun checkSettings(context: Context) {
        if (Settings.settings == null) {
            Settings.settings = Settings(
                context.resources.getInteger(R.integer.time),
                context.resources.getInteger(R.integer.teamCount),
                context.resources.getInteger(R.integer.roundCount),
                context.resources.getInteger(R.integer.pass)
            )
        }
    }
}