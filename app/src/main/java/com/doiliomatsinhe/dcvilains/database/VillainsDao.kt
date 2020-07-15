package com.doiliomatsinhe.dcvilains.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface VillainsDao {

    @Query("SELECT * FROM databasevillain WHERE publisher ='DC Comics'")
    fun getVillainsList(): LiveData<List<DatabaseVillain>>

    @RawQuery(observedEntities = [DatabaseVillain::class])
    fun getRawListOfVillains(query: SupportSQLiteQuery): LiveData<List<DatabaseVillain>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllVillains(vararg villains: DatabaseVillain)

    @Query("SELECT * FROM databasevillain WHERE id= :id")
    fun getVillainById(id: Int): LiveData<DatabaseVillain>

}