package com.koktajlbar.incognitobook.utils;


import androidx.room.TypeConverter;

import org.jetbrains.annotations.Contract;

import java.util.UUID;

public class UUIDTypeConverter
{
    @Contract("null -> null")
    @TypeConverter
    public static UUID toUUID(String value)
    {
        return value == null ? null : UUID.fromString(value);
    }
    @Contract("null -> null")
    @TypeConverter
    public static String toString(UUID value)
    {
        return value == null ? null : value.toString();
    }
}
