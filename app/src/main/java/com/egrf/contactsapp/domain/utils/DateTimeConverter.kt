package com.egrf.contactsapp.domain.utils

import com.google.gson.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.lang.reflect.Type


class DateTimeConverter : JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

    private var dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ")

    override fun serialize(
        src: DateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(dateTimeFormatter.print(src))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DateTime? {
        return if (json.asString == null || json.asString.isEmpty()) {
            null
        } else dateTimeFormatter.parseDateTime(json.asString)
    }
}