package com.doiliomatsinhe.dcvilains.network


import com.doiliomatsinhe.dcvilains.database.DatabaseCharacter
import com.doiliomatsinhe.dcvilains.model.*


data class NetworkCharacter(
    val id: Int,
    val name: String,
    val slug: String,
    val powerstats: Powerstats,
    val appearance: Appearance,
    val biography: Biography,
    val work: Work,
    val connections: Connections,
    val images: Images
)

/**
 * Converts Network results to Domain Objects
 */
fun List<NetworkCharacter>.asDomainModel(): List<Character> {
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
 * Converts Network results to Database Objects
 */
fun List<NetworkCharacter>.asDatabaseModel(): Array<DatabaseCharacter> {
    return map {
        DatabaseCharacter(
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
    }.toTypedArray()

}
