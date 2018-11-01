package bb.incognito.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GuestWithCocktails implements Parcelable {
    @Embedded public Guest guest;
    @Relation(parentColumn = "id", entityColumn = "cocktail_id", entity = Cocktail.class)
    public List<Cocktail> cocktailList;

    public GuestWithCocktails(Guest guest) {
        this.guest = guest;
    }

    public String getName() { return guest.getName(); }

    public void setName(String s){guest.setName(s);}

    @Override
    public int describeContents() {
        return 0;
    }

    protected GuestWithCocktails(Parcel in) {
        guest = new Guest(in.readInt(), in.readString(), in.readFloat(), in.readString(), in.readArrayList(Cocktail.class.getClassLoader()));
        cocktailList = in.readArrayList(Cocktail.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(guest.getId());
        parcel.writeString(guest.getName());
        parcel.writeFloat(guest.getDiscount());
        parcel.writeString(guest.getNotes());
        parcel.writeList(cocktailList);
    }

    public static final Parcelable.Creator<GuestWithCocktails> CREATOR
            = new Parcelable.Creator<GuestWithCocktails>() {
        public GuestWithCocktails createFromParcel(Parcel in) {
            return new GuestWithCocktails(in);
        }

        public GuestWithCocktails[] newArray(int size) {
            return new GuestWithCocktails[size];
        }
    };
}