package bb.incognito.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Guest implements Parcelable {

    @SerializedName("name")
    String name;
    @SerializedName("discount")
    float discount;
    @SerializedName("notes")
    String notes;
    @SerializedName("cocktailList")
    List<Cocktail> cocktailList = new ArrayList<>();

    public Guest(String name) {
        this.name = name;
    }

    public Guest(String name, float discount, String notes) {
        this.name = name;
        this.discount = discount;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Cocktail> getCocktailList() {
        return cocktailList;
    }

    public void setCocktailList(List<Cocktail> cocktailList) {
        this.cocktailList = cocktailList;
    }

    public void addCocktail(Cocktail cocktail)
    {
        cocktailList.add(cocktail);
    }

    protected Guest(Parcel in) {
        name = in.readString();
        discount = in.readFloat();
        notes = in.readString();
        cocktailList = in.readArrayList(Cocktail.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeFloat(discount);
        parcel.writeString(notes);
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
