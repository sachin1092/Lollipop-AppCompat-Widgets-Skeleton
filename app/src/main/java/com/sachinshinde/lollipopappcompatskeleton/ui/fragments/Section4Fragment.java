package com.sachinshinde.lollipopappcompatskeleton.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Section4Fragment extends Fragment {
  private static final String KEY_POSITION="position";

  public static Section4Fragment newInstance(int position) {
    Section4Fragment frag=new Section4Fragment();
    Bundle args=new Bundle();

    args.putInt(KEY_POSITION, position);
    frag.setArguments(args);

    return(frag);
  }

  public static String getTitle(int position) {
    return(String.format("position at %d", position + 1));
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {
    View result=new TextView(getActivity());
      TextView editor=(TextView)result;
    int position=getArguments().getInt(KEY_POSITION, -1);

    editor.setText(getTitle(position));

    return(result);
  }
}