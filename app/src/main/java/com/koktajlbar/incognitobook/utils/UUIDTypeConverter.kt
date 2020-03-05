package com.koktajlbar.incognitobook.utils

import androidx.room.TypeConverter
import org.jetbrains.annotations.Contract
import java.util.*

object UUIDTypeConverter {
    @Contract("null -> null")
    @TypeConverter
    @JvmStatic
    fun toUUID(value: String?): UUID? {
        return if (value == null) null else UUID.fromString(value)
    }

    @Contract("null -> null")
    @TypeConverter
    @JvmStatic
    fun toString(value: UUID?): String? {
        return value?.toString()
    }
}