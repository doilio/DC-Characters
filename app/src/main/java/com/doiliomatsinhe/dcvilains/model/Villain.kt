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