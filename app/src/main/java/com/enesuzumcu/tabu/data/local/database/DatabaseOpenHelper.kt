package com.enesuzumcu.tabu.data.local.database

import android.content.Context
import com.enesuzumcu.tabu.utils.Constants
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class DatabaseOpenHelper(context: Context?) :
    SQLiteAssetHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION)