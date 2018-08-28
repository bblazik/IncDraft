package bb.incognito.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Guest.class,
                parentColumns = "id",
                childColumns = "guest_id",
                onDelete = CASCADE
        ),
        indices = {@Index("guest_id")}
)
public class Cocktail implements Parcelable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "guest_id")
    public int guestId;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    String name;

    @Ignore
    @SerializedName("tags")
    List<String> tags = new ArrayList<>();

    @ColumnInfo(name = "notes")
    @SerializedName("notes")
    String notes;

    @ColumnInfo(name = "other")
    @SerializedName("other")
    String other;

    @Ignore
    public Cocktail(String name, List<String> tags, String notes, String other) {
        this.name = name;
        this.tags = tags;
        this.notes = notes;
        this.other = other;
    }

    public Cocktail(String name, Integer guestId) {
        this.name = name;
        this.guestId = guestId;
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
