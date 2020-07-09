package com.doiliomatsinhe.dcvilains.di

import android.content.Context
import androidx.room.Room
import com.doiliomatsinhe.dcvilains.database.VillainsDao
import com.doiliomatsinhe.dcvilains.database.VillainsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideVillainsDao(database: VillainsDatabase): VillainsDao {
        return database.villainsDao
    }

    @Provides
    @Singleton
    fun provideVillainsDatabase(@ApplicationContext appContext: Context): VillainsDatabase {
        return Room.databaseBuilder(
            appContext,
            VillainsDatabase::class.java,
            "villains"
        ).build()
    }

}