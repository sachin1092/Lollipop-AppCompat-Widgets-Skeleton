package com.sachinshinde.lollipopappcompatskeleton.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.internal.widget.CompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sachinshinde.lollipopappcompatskeleton.R;
import com.sachinshinde.lollipopappcompatskeleton.ui.activities.BaseActivity;
import com.sachinshinde.lollipopappcompatskeleton.ui.widgets.CallbackFragment;
import com.sachinshinde.lollipopappcompatskeleton.utils.UIUtils;

public class OtherFragment extends CallbackFragment {

    /**
     * The fragment's current callback object.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(long id) {
        }

        @Override
        public int getSelectedFragment() {
            return 0;
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OtherFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_layout, null);
        CompatTextView textView = (CompatTextView) result.findViewById(R.id.tvSample);

        textView.setText(getArguments().getString("Title"));

        result.findViewById(R.id.bButterBar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((BaseActivity)getActivity()).showButterBar("Android is awesome", "I know", 3000, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Yo!!", Toast.LENGTH_LONG).show();
                        ((BaseActivity)getActivity()).hideButterBar();
                    }
                });
            }
        });

        result.findViewById(R.id.bProgressDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtils.getProgressDialog(getActivity());
            }
        });

        return (result);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

}