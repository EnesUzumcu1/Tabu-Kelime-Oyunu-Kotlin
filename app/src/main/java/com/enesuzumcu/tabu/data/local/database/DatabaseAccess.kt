package com.enesuzumcu.tabu.data.local.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.enesuzumcu.tabu.data.model.Words
import com.enesuzumcu.tabu.utils.Constants

class DatabaseAccess private constructor(context: Context) {
    private val openHelper: SQLiteOpenHelper
    private var db: SQLiteDatabase? = null
    private var c: Cursor? = null

    init {
        openHelper = DatabaseOpenHelper(context)
    }

    companion object {
        private var instance: DatabaseAccess? = null
        fun getInstance(context: Context): DatabaseAccess {
            if (instance == null) {
                instance = DatabaseAccess(context)
            }
            return instance as DatabaseAccess
        }
    }

    private fun open() {
        db = openHelper.readableDatabase
    }

    private fun close() {
        if (db != null) {
            db!!.close()
        }
    }

    @SuppressLint("Range")
    fun getWords(id: Int): Words {
        open()
        c = db!!.rawQuery(
            "SELECT * FROM '" + Constants.TABLE_NAME + "' WHERE ID = '" + id + "'",
            arrayOf()
        )
        val words = Words()
        c?.let { c->
            if (c.moveToFirst()) {
                words.AnlatilanKelime = c.getString(c.getColumnIndex(Constants.COL2))
                words.Tabu1 = c.getString(c.getColumnIndex(Constants.COL3))
                words.Tabu2 = c.getString(c.getColumnIndex(Constants.COL4))
                words.Tabu3 = c.getString(c.getColumnIndex(Constants.COL5))
                words.Tabu4 = c.getString(c.getColumnIndex(Constants.COL6))
                words.Tabu5 = c.getString(c.getColumnIndex(Constants.COL7))
            }
            c.close()
        }
        close()
        return words
    }

    @SuppressLint("Range")
    fun databaseLength(): Int {
        open()
        c = db!!.rawQuery(
            "SELECT count(*) AS length FROM '" + Constants.TABLE_NAME + "' WHERE ID",
            arrayOf()
        )
        var length = 0
        c?.let { c->
            if (c.moveToFirst()) {
                length = c.getString(c.getColumnIndex("length")).toInt()
            }
            c.close()
        }
        close()
        return length
    }
}