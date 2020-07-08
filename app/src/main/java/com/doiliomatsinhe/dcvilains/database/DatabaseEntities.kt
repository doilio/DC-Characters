package com.doiliomatsinhe.dcvilains.database


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.doiliomatsinhe.dcvilains.model.*

@Entity
data class DatabaseVillain(
    @PrimaryKey
    val id: Int,
    val name: String ,
    val slug: String ,
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

