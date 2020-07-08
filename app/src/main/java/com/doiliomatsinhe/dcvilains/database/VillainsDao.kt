package com.doiliomatsinhe.dcvilains.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VillainsDao {

    @Query("SELECT * FROM databasevillain")
    fun getVillainsList(): LiveData<List<DatabaseVillain>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllVillains(vararg villains: DatabaseVillain)

}