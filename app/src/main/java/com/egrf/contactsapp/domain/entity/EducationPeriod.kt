package com.egrf.contactsapp.domain.entity

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime
import java.io.Serializable

data class EducationPeriod(
    @SerializedName("start")
    val start: DateTime,
    @SerializedName("end")
    val end: DateTime,
) : Serializable
