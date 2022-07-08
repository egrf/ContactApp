package com.egrf.contactsapp.data.database

import androidx.room.TypeConverter
import com.egrf.contactsapp.domain.entity.EducationPeriod
import org.json.JSONObject
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class TypeConverter {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun stringToPeriod(data: String?): EducationPeriod {
        val json = JSONObject(data)
        return EducationPeriod(
            toOffsetDateTime(json.getString(EducationPeriod.FIELD_START)),
            toOffsetDateTime(json.getString(EducationPeriod.FIELD_END))
        )
    }

    @TypeConverter
    fun periodToString(educationPeriod: EducationPeriod): String {
        return JSONObject().apply {
            put(EducationPeriod.FIELD_START, fromOffsetDateTime(educationPeriod.start))
            put(EducationPeriod.FIELD_END, fromOffsetDateTime(educationPeriod.end))
        }.toString()
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