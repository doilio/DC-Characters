package com.doiliomatsinhe.dcvilains.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Connections(
    val groupAffiliation: String,
    val relatives: String
) : Parcelable