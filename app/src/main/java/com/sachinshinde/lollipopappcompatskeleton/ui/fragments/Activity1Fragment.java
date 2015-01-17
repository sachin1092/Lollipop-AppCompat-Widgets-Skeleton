package com.sachinshinde.lollipopappcompatskeleton.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.CompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sachinshinde.lollipopappcompatskeleton.R;
import com.sachinshinde.lollipopappcompatskeleton.ui.activities.Activity1;
import com.sachinshinde.lollipopappcompatskeleton.ui.activities.BaseActivity;
import com.sachinshinde.lollipopappcompatskeleton.utils.UIUtils;

public class Activity1Fragment extends Fragment {
    private static final String KEY_POSITION = "position";

    public static Activity1Fragment newInstance(int position) {
        Activity1Fragment frag = new Activity1Fragment();
        Bundle args = new Bundle();

        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return (frag);
    }

    public static String getTitle(int position) {
        return (String.format("position at %d", position + 1));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_layout, null);
        CompatTextView textView = (CompatTextView) result.findViewById(R.id.tvSample);
        int position = getArguments().getInt(KEY_POSITION, -1);

        textView.setText(getTitle(position));

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
}