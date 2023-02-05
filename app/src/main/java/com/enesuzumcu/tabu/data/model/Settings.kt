package com.enesuzumcu.tabu.data.model

data class Settings(
    private var time: Int,
    private val teamCount: Int,
    private val round: Int,
    private val pass: Int
) {
    companion object {
        var settings: Settings? = null
    }
}