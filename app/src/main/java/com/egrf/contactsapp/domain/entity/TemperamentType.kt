package com.egrf.contactsapp.domain.entity

import com.google.gson.annotations.SerializedName

enum class TemperamentType(val value: String) {
    @SerializedName("melancholic")
    MELANCHOLIC("melancholic"),

    @SerializedName("phlegmatic")
    PHLEGMATIC("phlegmatic"),

    @SerializedName("sanguine")
    SANGUINE("sanguine"),

    @SerializedName("choleric")
    CHOLERIC("choleric"),

    @SerializedName("noInfo")
    NO_INFO("noInfo")
}