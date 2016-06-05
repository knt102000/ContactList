package com.trial.chiutsui.contactlist;

import android.app.Fragment;
import android.content.Context;

/**
 * Created by chiutsui on 6/5/16.
 */
public class ContractFragment<T> extends Fragment {

    private T mContract;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mContract = (T) getActivity();
        } catch (ClassCastException e) {
            throw new IllegalStateException("Activity does not implement contract");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mContract=null;
    }

    public T getContract() {
        return mContract;
    }

}
