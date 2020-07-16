package com.doiliomatsinhe.dcvilains.repository


import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.doiliomatsinhe.dcvilains.database.DatabaseCharacter
import com.doiliomatsinhe.dcvilains.database.CharacterDao
import com.doiliomatsinhe.dcvilains.database.asDomainModel
import com.doiliomatsinhe.dcvilains.model.Filters
import com.doiliomatsinhe.dcvilains.model.Character
import com.doiliomatsinhe.dcvilains.network.ApiService
import com.doiliomatsinhe.dcvilains.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val service: ApiService,
    private val database: CharacterDao
) {

    fun getCharacters(filters: Filters): PagingSource<Int, DatabaseCharacter> {

        val builtQuery = if (filters.gender.isNotEmpty() && filters.race.isNotEmpty()) {
            SimpleSQLiteQuery(
                "SELECT * FROM characters WHERE publisher ='DC Comics' AND gender = ? AND race = ?",
                arrayOf(filters.gender, filters.race)
            )
        } else if (filters.gender.isEmpty() && filters.race.isNotEmpty()) {
            SimpleSQLiteQuery(
                "SELECT * FROM characters WHERE publisher ='DC Comics' AND race = ?",
                arrayOf(filters.race)
            )
        } else if (filters.race.isEmpty() && filters.gender.isNotEmpty()) {
            SimpleSQLiteQuery(
                "SELECT * FROM characters WHERE publisher ='DC Comics' AND gender = ?",
                arrayOf(filters.gender)
            )
        } else {
            SimpleSQLiteQuery("SELECT * FROM characters WHERE publisher ='DC Comics'")
        }

        return database.getRawListOfCharacters(builtQuery)

    }

    suspend fun refreshCharacters() {
        withContext(Dispatchers.IO) {
            try {
                val listOfVillains = service.getCharacters()
                database.insertAllCharacters(*listOfVillains.asDatabaseModel())
            } catch (e: Exception) {
                Timber.d("Error: ${e.message}")
            }
        }
    }

    suspend fun getCharacterById(id: Int): LiveData<Character> {
        return withContext(Dispatchers.IO) {
            val villain = database.getCharactersById(id)

            villain.asDomainModel()
        }
    }
}

