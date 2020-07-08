package com.doiliomatsinhe.dcvilains.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.doiliomatsinhe.dcvilains.utils.Converters

@Database(entities = [DatabaseVillain::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class VillainsDatabase : RoomDatabase() {
    abstract val villainsDao: VillainsDao

    companion object {

        @Volatile
        private lateinit var INSTANCE: VillainsDatabase

        fun getDatabase(context: Context): VillainsDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        VillainsDatabase::class.java,
                        "villains"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}