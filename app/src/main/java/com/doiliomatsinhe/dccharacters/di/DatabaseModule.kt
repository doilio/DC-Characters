package com.doiliomatsinhe.dccharacters.di

import android.content.Context
import androidx.room.Room
import com.doiliomatsinhe.dccharacters.database.CharacterDao
import com.doiliomatsinhe.dccharacters.database.CharactersDatabase
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
    fun provideCharactersDao(database: CharactersDatabase): CharacterDao {
        return database.characterDao
    }

    @Provides
    @Singleton
    fun provideCharactersDatabase(@ApplicationContext appContext: Context): CharactersDatabase {
        return Room.databaseBuilder(
            appContext,
            CharactersDatabase::class.java,
            "charactersDB"
        ).fallbackToDestructiveMigration()
            .build()
    }

}