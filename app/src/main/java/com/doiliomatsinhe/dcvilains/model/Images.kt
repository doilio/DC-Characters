package com.doiliomatsinhe.dcvilains.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Images(
    val xs: String,
    val sm: String,
    val md: String,
    val lg: String
): Parcelable