///*
//package bb.incognito.viewModel
//
//import android.arch.lifecycle.LiveData
//import android.databinding.BaseObservable
//import android.view.View
//import android.widget.Toast
//
//import java.util.ArrayList
//import java.util.Arrays
//
//import bb.incognito.model.Cocktail
//import bb.incognito.model.Guest
//import bb.incognito.repositories.CocktailRepository
//import bb.incognito.view.AddCocktailFragment
//
//class AddCocktailVM(internal var df: AddCocktailFragment, editCocktail: Boolean, pos: Int,
//                    internal var cocktailRepository: CocktailRepository, internal var guest: Guest)
//    : BaseObservable()
//{
//    fragmentAddCocktailBinding
//
//    var name = ""
//    var tags: List<String> = ArrayList()
//    var notes = ""
//    var others = ""
//
//    var acceptButton: View.OnClickListener = View.OnClickListener { v ->
//        if (fieldsNotEmpty()) {
//            val newCocktail = Cocktail(name, guest.id)
//            guest.addCocktail(newCocktail)
//            cocktailRepository.insert(newCocktail)
//            notifyChange()
//            df.dismiss()
//        } else {
//            Toast.makeText(v.context, "Name cannot be empty", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    var closeButton: View.OnClickListener = View.OnClickListener { df.dismiss() }
//
//    fun getTags(): String {
//        return tags.toString().replace("\\[?\\]?,?".toRegex(), "")
//    }
//
//    fun setTags(tags: String) {
//        this.tags = Arrays.asList(*tags.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
//    }
//
//    fun fieldsNotEmpty(): Boolean {
//        return name != ""
//    }
//}
//*/
