package bb.incognito.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Ignore
import android.os.Parcel
import android.os.Parcelable
import java.util.*

class GuestWithCocktails : Parcelable {
    @Embedded
    var guest: Guest
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
        guest = Guest(UUID.fromString(`in`.readString()), `in`.readString(), `in`.readFloat(), `in`.readString(), null)
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(guest.id.toString())
        parcel.writeString(guest.getName())
        parcel.writeFloat(guest.getDiscount())
        parcel.writeString(guest.getNotes())
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