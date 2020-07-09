package com.doiliomatsinhe.dcvilains.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.doiliomatsinhe.dcvilains.utils.Converters

@Database(entities = [DatabaseVillain::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class VillainsDatabase : RoomDatabase() {
    abstract val villainsDao: VillainsDao

}