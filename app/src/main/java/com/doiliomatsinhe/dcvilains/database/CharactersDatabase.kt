package com.doiliomatsinhe.dcvilains.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.doiliomatsinhe.dcvilains.utils.Converters

@Database(entities = [DatabaseCharacter::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CharactersDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao

}