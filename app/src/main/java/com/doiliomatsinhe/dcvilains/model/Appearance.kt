package com.doiliomatsinhe.dcvilains.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Appearance(
    val gender: String,
    val race: String,
    val height: List<String>,
    val weight: List<String>,
    val eyeColor: String,
    val hairColor: String
): Parcelable