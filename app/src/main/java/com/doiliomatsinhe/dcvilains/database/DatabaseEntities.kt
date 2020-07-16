package com.doiliomatsinhe.dcvilains.database


import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.PagingData
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.doiliomatsinhe.dcvilains.model.*

@Entity(tableName = "characters")
data class DatabaseCharacter(
    @PrimaryKey
    val id: Int,
    val name: String,
    val slug: String,
    @Embedded val powerstats: Powerstats,
    @Embedded val appearance: Appearance,
    @Embedded val biography: Biography,
    @Embedded val work: Work,
    @Embedded val connections: Connections,
    @Embedded val images: Images
)

/**
 * Converts Database results to Domain Objects
 */
fun List<DatabaseCharacter>.asDomainModel(): List<Character> {

    return map {
        Character(
            id = it.id,
            name = it.name,
            slug = it.slug,
            powerstats = it.powerstats,
            appearance = it.appearance,
            biography = it.biography,
            work = it.work,
            connections = it.connections,
            images = it.images
        )
    }
}

/**
 * Converts Database results to Domain Objects
 */
fun LiveData<DatabaseCharacter>.asDomainModel(): LiveData<Character> {

    return map {
        Character(
            id = it.id,
            name = it.name,
            slug = it.slug,
            powerstats = it.powerstats,
            appearance = it.appearance,
            biography = it.biography,
            work = it.work,
            connections = it.connections,
            images = it.images
        )
    }
}

fun PagingData<DatabaseCharacter>.asDomainModel(): PagingData<Character> {

    return map {
        Character(
            id = it.id,
            name = it.name,
            slug = it.slug,
            powerstats = it.powerstats,
            appearance = it.appearance,
            biography = it.biography,
            work = it.work,
            connections = it.connections,
            images = it.images
        )
    }
}