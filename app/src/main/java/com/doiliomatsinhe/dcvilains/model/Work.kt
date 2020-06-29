package com.doiliomatsinhe.dcvilains.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Work(
    val occupation: String,
    val base: String
): Parcelable