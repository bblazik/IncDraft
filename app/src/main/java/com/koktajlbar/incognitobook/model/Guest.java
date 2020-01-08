package com.koktajlbar.incognitobook.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Guest implements Parcelable {

    @PrimaryKey @NotNull
    private UUID id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    String name;

    @ColumnInfo(name = "discount")
    @SerializedName("discount")
    float discount;

    @ColumnInfo(name = "notes")
    @SerializedName("notes")
    String notes;

    @Ignore
    @SerializedName("cocktailList")
    List<Cocktail> cocktailList = new ArrayList<>();

    public Guest(UUID id, String name, float discount, String notes, List<Cocktail> cocktailList) {
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.notes = notes;
        this.cocktailList = cocktailList;
    }

    @Ignore
    public Guest(String name) {
        id = UUID.randomUUID();
        this.name = name;
    }

    @Ignore
    public Guest(Guest guest) {
        this.id = guest.id;
        this.name = guest.name;
        this.discount = guest.discount;
        this.notes = guest.notes;
        this.cocktailList = guest.cocktailList;
    }

    public Guest(String name, float discount, String notes) {
        id = UUID.randomUUID();
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


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    protected Guest(Parcel in) {
        id = UUID.fromString(in.readString());
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
        parcel.writeString(id.toString());
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
