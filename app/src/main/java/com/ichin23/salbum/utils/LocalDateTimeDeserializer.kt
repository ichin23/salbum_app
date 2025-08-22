package com.ichin23.salbum.utils;

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeParseException
import com.google.gson.JsonParseException
import java.time.LocalDate

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime> {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: com.google.gson.JsonDeserializationContext
    ): LocalDateTime {
        try {
            // Pega a string do JSON e a sanitiza (remove aspas, se houver)
            val jsonString = json.asString

            if(jsonString.length==10){
                val localDate = LocalDate.parse(jsonString)
                return localDate.atStartOfDay()
            }

            // Primeiro, parse para OffsetDateTime para lidar com o fuso horário
            val offsetDateTime = OffsetDateTime.parse(jsonString)

            // Converte para LocalDateTime
            return offsetDateTime.toLocalDateTime()

        } catch (e: DateTimeParseException) {
            // Lida com erros de formato de data
            throw JsonParseException("Could not parse LocalDateTime: " + json.asString, e)
        }
    }
}