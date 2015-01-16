package com.sachinshinde.lollipopappcompatskeleton.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sachinshinde.lollipopappcompatskeleton.ui.fragments.Section4Fragment;

public class SampleAdapter extends FragmentPagerAdapter {
  Context ctxt=null;

  public SampleAdapter(Context ctxt, FragmentManager mgr) {
    super(mgr);
    this.ctxt=ctxt;
  }

  @Override
  public int getCount() {
    return(10);
  }

  @Override
  public Fragment getItem(int position) {
    return(Section4Fragment.newInstance(position));
  }

  @Override
  public String getPageTitle(int position) {
    return(Section4Fragment.getTitle(position));
  }
}