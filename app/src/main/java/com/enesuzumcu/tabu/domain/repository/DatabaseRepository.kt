package com.enesuzumcu.tabu.domain.repository

import com.enesuzumcu.tabu.data.local.database.DatabaseAccess
import com.enesuzumcu.tabu.data.model.Words
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val databaseAccess: DatabaseAccess){

    fun getWords(id: Int): Words {
        return databaseAccess.getWords(id)
    }

    fun databaseLength(): Int {
        return databaseAccess.databaseLength()
    }
}