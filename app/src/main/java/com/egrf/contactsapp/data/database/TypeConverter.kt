package com.egrf.contactsapp.data.database

import androidx.room.TypeConverter
import com.egrf.contactsapp.domain.entity.EducationPeriod
import com.google.gson.Gson
import java.sql.Date
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class TypeConverter {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun listToJson(value: MutableList<String>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun stringToPeriod(data: String?): EducationPeriod {
        return Gson().fromJson(data, EducationPeriod::class.java)
    }

    @TypeConverter
    fun periodToString(educationPeriod: EducationPeriod): String {
        return Gson().toJson(educationPeriod)
    }

    @TypeConverter
    fun toOffsetDateTime(value: String?): OffsetDateTime {
            return formatter.parse(value, OffsetDateTime::from)
    }

    @TypeConverter
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }
}