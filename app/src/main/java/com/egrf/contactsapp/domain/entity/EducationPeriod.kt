package com.egrf.contactsapp.domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.OffsetDateTime

data class EducationPeriod(
    @SerializedName("start")
    val start: OffsetDateTime = OffsetDateTime.now(),
    @SerializedName("end")
    val end: OffsetDateTime = OffsetDateTime.now(),
) : Serializable
