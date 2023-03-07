package com.enesuzumcu.tabu.ui.game.viewmodel

import androidx.lifecycle.ViewModel
import com.enesuzumcu.tabu.data.model.Game
import com.enesuzumcu.tabu.data.model.Settings
import com.enesuzumcu.tabu.data.model.Teams
import com.enesuzumcu.tabu.data.model.Words
import com.enesuzumcu.tabu.domain.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val repository: DatabaseRepository) : ViewModel() {

    private val randomNumbers = ArrayList<Int>()
    private val _random = Random()
    private var databaseLength = 0
    var turn = 0
    var maxTurn = 0
    var second = 0
    var gameStatus: Game? = null
    var inTheGame = false
    var words: Words? = null

    init {
        gameStatus = Game.gameStatus
        databaseLength = getLength()
        maxTurn = Settings.settings!!.teamCount * Settings.settings!!.round - 1
        second = Settings.settings!!.time
        getWords()
    }

    fun getWords() {
        words = repository.getWords(randomNumberGenerator(databaseLength))
    }

    fun getLength(): Int {
        return repository.databaseLength()
    }

    fun increaseScore() {
        gameStatus!!.score = gameStatus!!.score + 1
    }

    fun decreaseScore() {
        gameStatus!!.score = gameStatus!!.score - 1
    }

    fun decreasePass() {
        if (gameStatus!!.pass > 0) gameStatus!!.pass = gameStatus!!.pass - 1
    }

    fun decreaseSecond() {
        second--
        inTheGame = true
    }

    fun resetState() {
        gameStatus!!.score = 0
        gameStatus!!.pass = Settings.settings!!.pass
        second = Settings.settings!!.time
        inTheGame = false
        getWords()
    }

    fun increaseTurn() {
        turn++
    }

    fun getTeamName(): String {
        return getTeam().teamName
    }

    fun getTeam(): Teams {
        return when (turn % Settings.settings!!.teamCount) {
            0 -> Teams.team1!!
            1 -> Teams.team2!!
            2 -> Teams.team3!!
            3 -> Teams.team4!!
            else -> Teams("", 0)
        }
    }

    fun updateScore(): Int {
        val team: Teams = getTeam()
        team.teamScore = (gameStatus!!.score + team.teamScore)
        return team.teamScore
    }

    fun winner(): String? {
        when (Settings.settings!!.teamCount) {
            2 -> {
                if (Teams.team1?.teamScore!! > Teams.team2?.teamScore!!) {
                    return java.lang.String.format(
                        "Kazanan <%s> Tebrikler!",
                        Teams.team1?.teamName
                    )
                }
                if (Teams.team2?.teamScore!! > Teams.team1?.teamScore!!) {
                    return java.lang.String.format(
                        "Kazanan <%s> Tebrikler!",
                        Teams.team2?.teamName
                    )
                }
            }
            3 -> {
                if (Teams.team1?.teamScore!! > Teams.team2?.teamScore!!
                    && Teams.team1?.teamScore!! > Teams.team3?.teamScore!!
                ) {
                    return java.lang.String.format(
                        "Kazanan <%s> Tebrikler!",
                        Teams.team1?.teamName
                    )
                }
                if (Teams.team2?.teamScore!! > Teams.team1?.teamScore!!
                    && Teams.team2?.teamScore!! > Teams.team3?.teamScore!!
                ) {
                    return java.lang.String.format(
                        "Kazanan <%s> Tebrikler!",
                        Teams.team2?.teamName
                    )
                }
                if (Teams.team3?.teamScore!! > Teams.team1?.teamScore!!
                    && Teams.team3?.teamScore!! > Teams.team2?.teamScore!!
                ) {
                    return java.lang.String.format(
                        "Kazanan <%s> Tebrikler!",
                        Teams.team3?.teamName
                    )
                }
            }
            4 -> {
                if (Teams.team1?.teamScore!! > Teams.team2?.teamScore!!
                    && Teams.team1?.teamScore!! > Teams.team3?.teamScore!!
                    && Teams.team1?.teamScore!! > Teams.team4?.teamScore!!) {
                    return java.lang.String.format(
                        "Kazanan <%s> Tebrikler!",
                        Teams.team1?.teamName
                    )
                }
                if (Teams.team2?.teamScore!! > Teams.team1?.teamScore!!
                    && Teams.team2?.teamScore!! > Teams.team3?.teamScore!!
                    && Teams.team2?.teamScore!! > Teams.team4?.teamScore!!) {
                    return java.lang.String.format(
                        "Kazanan <%s> Tebrikler!",
                        Teams.team2?.teamName
                    )
                }
                if (Teams.team3?.teamScore!! > Teams.team1?.teamScore!!
                    && Teams.team3?.teamScore!! > Teams.team2?.teamScore!!
                    && Teams.team3?.teamScore!! > Teams.team4?.teamScore!!) {
                    return java.lang.String.format(
                        "Kazanan <%s> Tebrikler!",
                        Teams.team3?.teamName
                    )
                }
                if (Teams.team4?.teamScore!! > Teams.team1?.teamScore!!
                    && Teams.team4?.teamScore!! > Teams.team2?.teamScore!!
                    && Teams.team4?.teamScore!! > Teams.team3?.teamScore!!
                ) {
                    return java.lang.String.format(
                        "Kazanan <%s> Tebrikler!",
                        Teams.team4?.teamName
                    )
                }
            }
        }
        return "Kazanan Yok."
    }

    fun randomNumberGenerator(length: Int): Int {
        var loopControl = true
        var randomNumber = _random.nextInt(length + 1)
        if (!randomNumbers.contains(randomNumber)) {
            randomNumbers.add(randomNumber)
            loopControl = false
        }
        while (loopControl) {
            randomNumber = _random.nextInt(length + 1)
            if (!randomNumbers.contains(randomNumber)) {
                randomNumbers.add(randomNumber)
                loopControl = false
                break
            }
        }
        return randomNumber
    }

}