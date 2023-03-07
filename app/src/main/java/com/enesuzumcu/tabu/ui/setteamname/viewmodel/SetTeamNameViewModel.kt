package com.enesuzumcu.tabu.ui.setteamname.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.enesuzumcu.tabu.R
import com.enesuzumcu.tabu.data.model.Teams
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SetTeamNameViewModel @Inject constructor(@ApplicationContext context: Context): ViewModel()  {

    init {
        setTeams(context)
    }

    private fun setTeams(context: Context) {
        Teams.team1 = Teams(context.getString(R.string.team1DefaultName), 0)
        Teams.team2 = Teams(context.getString(R.string.team2DefaultName), 0)
        Teams.team3 = Teams(context.getString(R.string.team3DefaultName), 0)
        Teams.team4 = Teams(context.getString(R.string.team4DefaultName), 0)
    }

    fun setTeam1andTeam2Names(team1Name: String, team2Name: String) {
        Teams.team1?.teamName = team1Name
        Teams.team2?.teamName = team2Name
    }

    fun setTeam3Name(team3Name: String) {
        Teams.team3?.teamName = team3Name
    }

    fun setTeam4Name(team4Name: String) {
        Teams.team4?.teamName = team4Name
    }
}