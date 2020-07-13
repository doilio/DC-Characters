package com.doiliomatsinhe.dcvilains.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VillainsDao {

    @Query("SELECT * FROM databasevillain")
    fun getVillainsList(): LiveData<List<DatabaseVillain>>

    @Query("SELECT * FROM databasevillain ORDER BY intelligence DESC")
    fun getVillainListByIntelligence(): LiveData<List<DatabaseVillain>>

    @Query("SELECT * FROM databasevillain ORDER BY strength DESC")
    fun getVillainListByStrength(): LiveData<List<DatabaseVillain>>

    @Query("SELECT * FROM databasevillain ORDER BY speed DESC")
    fun getVillainListBySpeed(): LiveData<List<DatabaseVillain>>

    @Query("SELECT * FROM databasevillain ORDER BY durability DESC")
    fun getVillainListByDurability(): LiveData<List<DatabaseVillain>>

    @Query("SELECT * FROM databasevillain ORDER BY power DESC")
    fun getVillainListByPower(): LiveData<List<DatabaseVillain>>

    @Query("SELECT * FROM databasevillain ORDER BY combat DESC")
    fun getVillainListByCombatSkill(): LiveData<List<DatabaseVillain>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllVillains(vararg villains: DatabaseVillain)

    @Query("SELECT * FROM databasevillain WHERE id= :id")
    fun getVillainById(id: Int): LiveData<DatabaseVillain>

}