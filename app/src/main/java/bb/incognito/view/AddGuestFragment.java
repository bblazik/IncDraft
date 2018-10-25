package bb.incognito.view;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import bb.incognito.R;
import bb.incognito.databinding.FragmentAddGuestBinding;
import bb.incognito.repositories.GuestRepository;
import bb.incognito.viewModel.AddGuestVM;

public class AddGuestFragment extends DialogFragment {

    FragmentAddGuestBinding fragmentAddGuestBinding;
    static GuestRepository guestRepository;
    public AddGuestFragment(){}

    public static AddGuestFragment newInstance(boolean edit, int pos, GuestRepository gr){
        AddGuestFragment addGuestFragment = new AddGuestFragment();
        Bundle args = new Bundle();
        args.putBoolean("edit", edit);
        args.putInt("pos", pos);
        addGuestFragment.setArguments(args);
        guestRepository = gr;
        return addGuestFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentAddGuestBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_add_guest, container, false);

        int pos = getArguments().getInt("pos");
        boolean edit = getArguments().getBoolean("edit");

        fragmentAddGuestBinding.setViewModel(new AddGuestVM(this,edit, pos, guestRepository));
        return fragmentAddGuestBinding.getRoot();
    }

    @Override
    public void onResume(){
        super.onResume();

        fragmentAddGuestBinding.newName.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void dismiss(){
        ((InputMethodManager) getDialog().getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getView().getWindowToken(), 0);
        super.dismiss();
    }
}
