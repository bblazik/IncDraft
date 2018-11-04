package bb.incognito.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Relation
import android.os.Parcel
import android.os.Parcelable

class GuestWithCocktails : Parcelable {
    @Embedded
    var guest: Guest
    //@Relation(parentColumn = "id", entityColumn = "cocktail_id", entity = Cocktail.class)
    @Ignore
    var cocktailList: LiveData<MutableList<Cocktail>>? = null

    var name: String
        get() = guest.getName()
        set(s) = guest.setName(s)

    constructor(guest: Guest) {
        this.guest = guest
    }

    override fun describeContents(): Int {
        return 0
    }

    protected constructor(`in`: Parcel) {
        guest = Guest(`in`.readInt(), `in`.readString(), `in`.readFloat(), `in`.readString(), null)
        //cocktailList = in.readArrayList(Cocktail.class.getClassLoader());
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeInt(guest.id)
        parcel.writeString(guest.getName())
        parcel.writeFloat(guest.getDiscount())
        parcel.writeString(guest.getNotes())
        //parcel.writeList(cocktailList);
    }

    companion object CREATOR : Parcelable.Creator<GuestWithCocktails> {
        override fun createFromParcel(parcel: Parcel): GuestWithCocktails {
            return GuestWithCocktails(parcel)
        }

        override fun newArray(size: Int): Array<GuestWithCocktails?> {
            return arrayOfNulls(size)
        }
    }
}