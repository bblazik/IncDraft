package bb.incognito.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Guest implements Parcelable {

    @SerializedName("name")
    String name;
    @SerializedName("discount")
    float discount;
    @SerializedName("notes")
    String notes;
    @SerializedName("coctailList")
    List<Coctail> coctailList;

    public Guest(String name) {
        this.name = name;
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

    public List<Coctail> getCocktailList() {
        return coctailList;
    }

    public void setCoctailList(List<Coctail> coctailList) {
        this.coctailList = coctailList;
    }

    protected Guest(Parcel in) {
        name = in.readString();
        discount = in.readFloat();
        notes = in.readString();
        coctailList = in.readArrayList(Coctail.class.getClassLoader());
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
        parcel.writeList(coctailList);
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
