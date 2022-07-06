package com.egrf.contactsapp.domain.entity

import com.google.gson.annotations.SerializedName

enum class TemperamentType(private val value: String) {
    @SerializedName("melancholic")
    MELANCHOLIC("Melancholic"),

    @SerializedName("phlegmatic")
    PHLEGMATIC("Phlegmatic"),

    @SerializedName("sanguine")
    SANGUINE("Sanguine"),

    @SerializedName("choleric")
    CHOLERIC("Choleric"),

    @SerializedName("noInfo")
    NO_INFO("NoInfo");

    override fun toString(): String {
        return value
    }
}
