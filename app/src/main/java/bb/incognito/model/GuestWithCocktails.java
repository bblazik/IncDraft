package bb.incognito.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GuestWithCocktails implements Parcelable {
    @Embedded public Guest guest;
    @Relation(parentColumn = "id", entityColumn = "guest_id", entity = Cocktail.class)
    public List<Cocktail> cocktailList;

    public GuestWithCocktails(Guest guest) {
        this.guest = guest;
    }

    public String getName() { return guest.getName(); };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(guest.getId());
        parcel.writeString(guest.getName());
        parcel.writeFloat(guest.getDiscount());
        parcel.writeString(guest.getNotes());
        parcel.writeList(cocktailList);
    }

    public static final Parcelable.Creator<Guest> CREATOR
            = new Parcelable.Creator<Guest>() {
        public Guest createFromParcel(Parcel in) {
            return new Guest(in);
        }

        public Guest[] newArray(int size) {
            return new Guest[size];
        }
    };
}
