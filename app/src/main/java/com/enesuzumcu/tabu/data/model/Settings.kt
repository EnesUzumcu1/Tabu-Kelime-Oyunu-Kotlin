package com.enesuzumcu.tabu.data.model

data class Settings(
    var time: Int,
    var teamCount: Int,
    var round: Int,
    var pass: Int
) {
    companion object {
        var settings: Settings? = null
    }
}