package com.enesuzumcu.tabu.data.model

data class Teams(
    private var teamName : String,
    private var teamScore: Int
) {
    companion object{
        var team1 : Teams? = null
        var team2 : Teams? = null
        var team3 : Teams? = null
        var team4 : Teams? = null
    }
}