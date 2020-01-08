package com.koktajlbar.incognitobook.model;


import androidx.room.Entity;
import androidx.room.ForeignKey;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "guest_cocktail_join",
        primaryKeys = { "guestId", "cocktailId" },
        foreignKeys = {
                @ForeignKey(entity = Guest.class,
                        parentColumns = "id",
                        childColumns = "guestId",
                        onDelete = CASCADE),
                @ForeignKey(entity = Cocktail.class,
                        parentColumns = "cocktail_id",
                        childColumns = "cocktailId")
        })
public class GuestCocktailJoin {
    @NotNull
    public final UUID guestId;
    @NotNull
    public final UUID cocktailId;

    public GuestCocktailJoin(UUID guestId, UUID cocktailId) {
        this.guestId = guestId;
        this.cocktailId = cocktailId;
    }
}
