package com.sachinshinde.lollipopappcompatskeleton.ui.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.sachinshinde.lollipopappcompatskeleton.R;
import com.sachinshinde.lollipopappcompatskeleton.ui.adapters.SampleAdapter;
import com.sachinshinde.lollipopappcompatskeleton.ui.widgets.CallbackFragment;
import com.sachinshinde.lollipopappcompatskeleton.ui.widgets.SlidingTabLayout;


public class Activity1 extends BaseActivity implements
        CallbackFragment.Callbacks  {


    @Override
    public void onItemSelected(long id) {

    }

    @Override
    public int getSelectedFragment() {
        return BaseActivity.NAVDRAWER_ITEM_ACTIVITY1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(buildAdapter());
        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
        Resources res = getResources();
        mSlidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.tab_selected_strip));
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);

    }

    private PagerAdapter buildAdapter() {
        return(new SampleAdapter(this, getSupportFragmentManager()));
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }

    @Override
    protected void onActionBarAutoShowOrHide(boolean shown) {
        super.onActionBarAutoShowOrHide(shown);
    }


    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return super.canSwipeRefreshChildScrollUp();
    }


    @Override protected int getLayoutResource() {
        return R.layout.tabs_layout;
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return BaseActivity.NAVDRAWER_ITEM_ACTIVITY1;
    }
}
