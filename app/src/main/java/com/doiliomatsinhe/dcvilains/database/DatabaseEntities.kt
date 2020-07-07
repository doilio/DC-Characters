package com.doiliomatsinhe.dcvilains.database


import com.doiliomatsinhe.dcvilains.model.*

data class DatabaseVillain(
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
 * Converts Database results to Domain Objects
 */
fun List<DatabaseVillain>.asDomainModel(): List<Villain> {

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

