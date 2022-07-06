package com.egrf.contactsapp.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime
import org.joda.time.Years
import java.io.Serializable

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey
    val id: String = "-1",
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
    val educationPeriod: EducationPeriod = EducationPeriod(
        DateTime.now().minus(Years.ONE),
        DateTime.now()
    )
) : Serializable
