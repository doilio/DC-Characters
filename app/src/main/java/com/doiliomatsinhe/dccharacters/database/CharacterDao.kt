package com.doiliomatsinhe.dccharacters.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface CharacterDao {

    @RawQuery(observedEntities = [DatabaseCharacter::class])
    fun getRawListOfCharacters(query: SupportSQLiteQuery): PagingSource<Int, DatabaseCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCharacters(vararg characters: DatabaseCharacter)

    @Query("SELECT * FROM characters WHERE id= :id")
    fun getCharactersById(id: Int): LiveData<DatabaseCharacter>

}