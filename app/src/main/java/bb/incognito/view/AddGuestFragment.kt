package bb.incognito.view

import android.support.v4.app.DialogFragment
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

import bb.incognito.R
import bb.incognito.databinding.FragmentAddGuestBinding
import bb.incognito.repositories.GuestRepository
import bb.incognito.viewModel.AddGuestVM

class AddGuestFragment : DialogFragment() {

    internal lateinit var fragmentAddGuestBinding: FragmentAddGuestBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fragmentAddGuestBinding = DataBindingUtil.inflate(inflater!!,
                R.layout.fragment_add_guest, container, false)

        val pos = arguments.getInt("pos")
        val edit = arguments.getBoolean("edit")

        fragmentAddGuestBinding.viewModel = AddGuestVM(this, edit, pos, guestRepository)
        return fragmentAddGuestBinding.root
    }

    override fun onResume() {
        super.onResume()

        fragmentAddGuestBinding.newName.requestFocus()
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    override fun dismiss() {
        (dialog.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(view!!.windowToken, 0)
        super.dismiss()
    }

    companion object {
        internal lateinit var guestRepository: GuestRepository

        fun newInstance(edit: Boolean, pos: Int, gr: GuestRepository): AddGuestFragment {
            val addGuestFragment = AddGuestFragment()
            val args = Bundle()
            args.putBoolean("edit", edit)
            args.putInt("pos", pos)
            addGuestFragment.arguments = args
            guestRepository = gr
            return addGuestFragment
        }
    }
}
