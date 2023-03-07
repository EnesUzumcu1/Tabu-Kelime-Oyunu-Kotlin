package com.enesuzumcu.tabu.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import com.enesuzumcu.tabu.data.model.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private var tempTeamCount: Int = 0
    private var tempTime: Int = 0
    private var tempRoundCount: Int = 0
    private var tempPass: Int = 0

    fun setTeamCount(teamCount: Int) {
        tempTeamCount = teamCount
    }

    fun setTime(time: Int) {
        tempTime = time
    }

    fun setRoundCount(roundCount: Int) {
        tempRoundCount = roundCount
    }

    fun setPass(pass: Int) {
        tempPass = pass
    }

    fun save() {
        Settings.settings?.let { safeSettings ->
            safeSettings.time = tempTime
            safeSettings.teamCount = tempTeamCount
            safeSettings.round = tempRoundCount
            safeSettings.pass = tempPass
        }?: kotlin.run {
            Settings.settings = Settings(tempTime, tempTeamCount, tempRoundCount, tempPass)
        }
    }
}