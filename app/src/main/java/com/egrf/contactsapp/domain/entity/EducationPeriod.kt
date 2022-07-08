package com.egrf.contactsapp.domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.OffsetDateTime

data class EducationPeriod(
    @SerializedName(FIELD_START)
    val start: OffsetDateTime = OffsetDateTime.now(),
    @SerializedName(FIELD_END)
    val end: OffsetDateTime = OffsetDateTime.now(),
) : Serializable {

    companion object {
        const val FIELD_START = "start"
        const val FIELD_END = "end"
    }

}
