package bb.incognito.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import bb.incognito.utils.UUIDTypeConverter;

@Entity
public class Cocktail implements Parcelable {

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @ColumnInfo(name = "cocktail_id")
    @PrimaryKey
    @NotNull
    @SerializedName("uuid")
    private UUID id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    String name;

    @Ignore
    @SerializedName("tags")
    List<String> tags = new ArrayList<>();

    @ColumnInfo(name = "notes")
    @SerializedName("notes")
    @Ignore
    String notes;

    @ColumnInfo(name = "other")
    @SerializedName("other")
    @Ignore
    String other;

    @SerializedName("ingredients")
    @Ignore
    String ingredients;

    @SerializedName("glassware")
    @Ignore
    String glassware;

    @SerializedName("technique")
    @Ignore
    String technique;

    @SerializedName("signature")
    @Ignore
    Boolean signature;

    @SerializedName("created_at")
    @Ignore
    String created_at;

    @SerializedName("updated_at")
    @Ignore
    String updated_at;

    @SerializedName("url")
    @Ignore
    String url;

    @SerializedName("id")
    @Ignore
    Integer cocktail_id;

    @Ignore
    public Cocktail(String name, List<String> tags, String notes, String other) {
        this.name = name;
        this.tags = tags;
        this.notes = notes;
        this.other = other;
    }

    @Ignore
    public Cocktail(@NotNull UUID id, String name, String ingredients, String glassware, String technique, Boolean signature, String created_at, String updated_at, String url) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.glassware = glassware;
        this.technique = technique;
        this.signature = signature;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.url = url;
    }

    @Ignore
    public Cocktail(String id, String name, String ingredients, String glassware, String technique, Boolean signature, String created_at, String updated_at, String url) {
        this.id = UUID.fromString(id);
        this.name = name;
        this.ingredients = ingredients;
        this.glassware = glassware;
        this.technique = technique;
        this.signature = signature;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.url = url;
    }

    @Ignore
    public Cocktail(UUID id)
    {
        this.id = id;
    }

    public Cocktail(String name)
    {
        id = UUID.randomUUID();
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
