package com.enesuzumcu.tabu.data.model

data class Game(
    private var score: Int,
    private val pass: Int
) {
    companion object {
        var gameStatus : Game = Game(0, 5)
    }
}