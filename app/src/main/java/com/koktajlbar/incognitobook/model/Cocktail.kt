package com.koktajlbar.incognitobook.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity
@Parcelize
data class Cocktail(
        @PrimaryKey
        @NotNull
        @ColumnInfo(name = "cocktail_id")
        @SerializedName("uuid")
        val id: UUID,

        @ColumnInfo(name = "name")
        @SerializedName("name")
        val name: String,

        @ColumnInfo(name = "ingredients")
        @SerializedName("ingredients")
        val ingredients: String,

        @ColumnInfo(name = "glassware")
        @SerializedName("glassware")
        val glassware: String,

        @ColumnInfo(name = "technique")
        @SerializedName("technique")
        val technique: String,

        @ColumnInfo(name = "garnish")
        @SerializedName("garnish")
        val garnish: String,

        @ColumnInfo(name = "category")
        @SerializedName("category")
        val category: String,

        @ColumnInfo(name = "youtube_link")
        @SerializedName("youtube_link")
        val youtubeLink: String,

        @ColumnInfo(name = "video_id")
        @SerializedName("video_id")
        val videoId: String,

        @ColumnInfo(name = "signature")
        @SerializedName("signature")
        val signature: Boolean,

        @ColumnInfo(name = "menu")
        @SerializedName("menu")
        val menu: Boolean
) : Parcelable