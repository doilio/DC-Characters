package com.doiliomatsinhe.dcvilains.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Biography(
    val fullName: String,
    val alterEgos: String,
    val aliases: List<String>,
    val placeOfBirth: String,
    val firstAppearance: String,
    val publisher: String,
    val alignment: String
): Parcelable