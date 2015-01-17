package com.sachinshinde.lollipopappcompatskeleton.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sachinshinde.lollipopappcompatskeleton.ui.fragments.Activity1Fragment;

public class SampleAdapter extends FragmentPagerAdapter {
  Context ctxt=null;

  public SampleAdapter(Context ctxt, FragmentManager mgr) {
    super(mgr);
    this.ctxt=ctxt;
  }

  @Override
  public int getCount() {
    return(3);
  }

  @Override
  public Fragment getItem(int position) {
    return(Activity1Fragment.newInstance(position));
  }

  @Override
  public String getPageTitle(int position) {
    return(Activity1Fragment.getTitle(position));
  }
}