package com.enesuzumcu.tabu.data.di

import android.content.Context
import com.enesuzumcu.tabu.data.local.database.DatabaseAccess
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabaseAccess(@ApplicationContext appContext: Context): DatabaseAccess =
        DatabaseAccess.getInstance(appContext)
}