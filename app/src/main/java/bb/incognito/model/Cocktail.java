package bb.incognito.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cocktail implements Parcelable{

    @SerializedName("name")
    String name;
    @SerializedName("tags")
    List<String> tags;
    @SerializedName("notes")
    String notes;
    @SerializedName("other")
    String other;

    public Cocktail(String name, List<String> tags, String notes, String other) {
        this.name = name;
        this.tags = tags;
        this.notes = notes;
        this.other = other;
    }

    public Cocktail(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }


    protected Cocktail(Parcel in) {
        name = in.readString();
        tags = in.readArrayList(Cocktail.class.getClassLoader());
        notes = in.readString();
        other = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeList(tags);
        parcel.writeString(notes);
        parcel.writeString(other);
    }

    public static final Parcelable.Creator<Cocktail> CREATOR
            = new Parcelable.Creator<Cocktail>() {
        public Cocktail createFromParcel(Parcel in) {
            return new Cocktail(in);
        }

        public Cocktail[] newArray(int size) {
            return new Cocktail[size];
        }
    };
}
