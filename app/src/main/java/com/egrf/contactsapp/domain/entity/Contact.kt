package com.egrf.contactsapp.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.egrf.contactsapp.ui.extensions.EMPTY
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey
    val id: String = String.EMPTY,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("height")
    val height: Float? = null,
    @SerializedName("biography")
    val biography: String? = null,
    @SerializedName("temperament")
    val temperament: TemperamentType = TemperamentType.NO_INFO,
    @SerializedName("educationPeriod")
    val educationPeriod: EducationPeriod = EducationPeriod()
) : Serializable
