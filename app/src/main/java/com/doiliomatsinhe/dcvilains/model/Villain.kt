package com.doiliomatsinhe.dcvilains.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Villain(
    val id: Int,
    val name: String,
    val slug: String,
    val powerstats: Powerstats,
    val appearance: Appearance,
    val biography: Biography,
    val work: Work,
    val connections: Connections,
    val images: Images
) : Parcelable

@Parcelize
data class Work(
    val occupation: String,
    val base: String
) : Parcelable

@Parcelize
data class Powerstats(
    val intelligence: Int,
    val strength: Int,
    val speed: Int,
    val durability: Int,
    val power: Int,
    val combat: Int
) : Parcelable

@Parcelize
data class Images(
    val xs: String,
    val sm: String,
    val md: String,
    val lg: String
) : Parcelable

@Parcelize
data class Connections(
    val groupAffiliation: String,
    val relatives: String
) : Parcelable

@Parcelize
data class Biography(
    val fullName: String,
    val alterEgos: String,
    val aliases: List<String>,
    val placeOfBirth: String,
    val firstAppearance: String,
    val publisher: String,
    val alignment: String
) : Parcelable

@Parcelize
data class Appearance(
    val gender: String,
    //val race: String,
    val height: List<String>,
    val weight: List<String>,
    val eyeColor: String,
    val hairColor: String
) : Parcelable