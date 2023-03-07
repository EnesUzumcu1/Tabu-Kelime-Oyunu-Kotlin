package com.enesuzumcu.tabu.data.model

data class Game(
    var score: Int,
    var pass: Int
) {
    companion object {
        var gameStatus : Game = Game(0, 5)
    }
}