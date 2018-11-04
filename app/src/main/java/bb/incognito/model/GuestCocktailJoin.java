package bb.incognito.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "guest_cocktail_join",
        primaryKeys = { "guestId", "cocktailId" },
        foreignKeys = {
                @ForeignKey(entity = Guest.class,
                        parentColumns = "id",
                        childColumns = "guestId"),
                @ForeignKey(entity = Cocktail.class,
                        parentColumns = "cocktail_id",
                        childColumns = "cocktailId")
        })
public class GuestCocktailJoin {
    public final int guestId;
    public final int cocktailId;

    public GuestCocktailJoin(int guestId, int cocktailId) {
        this.guestId = guestId;
        this.cocktailId = cocktailId;
    }
}