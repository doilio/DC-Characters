package com.doiliomatsinhe.dcvilains.repository


import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.doiliomatsinhe.dcvilains.database.DatabaseVillain
import com.doiliomatsinhe.dcvilains.database.VillainsDao
import com.doiliomatsinhe.dcvilains.database.asDomainModel
import com.doiliomatsinhe.dcvilains.model.Filters
import com.doiliomatsinhe.dcvilains.model.Villain
import com.doiliomatsinhe.dcvilains.network.ApiService
import com.doiliomatsinhe.dcvilains.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class VillainRepository @Inject constructor(
    private val service: ApiService,
    private val database: VillainsDao
) {

    fun getVillains(filters: Filters): PagingSource<Int, DatabaseVillain> {

        val builtQuery = if (filters.gender.isNotEmpty() && filters.race.isNotEmpty()) {
            SimpleSQLiteQuery(
                "SELECT * FROM databasevillain WHERE publisher ='DC Comics' AND gender = ? AND race = ?",
                arrayOf(filters.gender, filters.race)
            )
        } else if (filters.gender.isEmpty() && filters.race.isNotEmpty()) {
            SimpleSQLiteQuery(
                "SELECT * FROM databasevillain WHERE publisher ='DC Comics' AND race = ?",
                arrayOf(filters.race)
            )
        } else if (filters.race.isEmpty() && filters.gender.isNotEmpty()) {
            SimpleSQLiteQuery(
                "SELECT * FROM databasevillain WHERE publisher ='DC Comics' AND gender = ?",
                arrayOf(filters.gender)
            )
        } else {
            SimpleSQLiteQuery("SELECT * FROM databasevillain WHERE publisher ='DC Comics'")
        }

        return database.getRawListOfVillains(builtQuery)

    }

    suspend fun refreshVillains() {
        withContext(Dispatchers.IO) {
            try {
                val listOfVillains = service.getVillains()
                database.insertAllVillains(*listOfVillains.asDatabaseModel())
            } catch (e: Exception) {
                Timber.d("Error: ${e.message}")
            }
        }
    }

    suspend fun getVillainById(id: Int): LiveData<Villain> {
        return withContext(Dispatchers.IO) {
            val villain = database.getVillainById(id)

            villain.asDomainModel()
        }
    }
}

