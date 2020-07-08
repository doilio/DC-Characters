package com.doiliomatsinhe.dcvilains.network


import com.doiliomatsinhe.dcvilains.database.DatabaseVillain
import com.doiliomatsinhe.dcvilains.model.*


data class NetworkVillain(
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
fun List<NetworkVillain>.asDomainModel(): List<Villain> {
    return map {
        Villain(
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
fun List<NetworkVillain>.asDatabaseModel(): Array<DatabaseVillain> {
    return map {
        DatabaseVillain(
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
