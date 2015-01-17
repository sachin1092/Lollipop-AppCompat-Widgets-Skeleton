package com.sachinshinde.lollipopappcompatskeleton.ui.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.sachinshinde.lollipopappcompatskeleton.R;
import com.sachinshinde.lollipopappcompatskeleton.ui.adapters.SampleAdapter;
import com.sachinshinde.lollipopappcompatskeleton.ui.views.CallbackFragment;
import com.sachinshinde.lollipopappcompatskeleton.ui.views.DrawShadowFrameLayout;
import com.sachinshinde.lollipopappcompatskeleton.ui.views.SlidingTabLayout;
import com.sachinshinde.lollipopappcompatskeleton.utils.UIUtils;


public class Activity1 extends BaseActivity implements
        CallbackFragment.Callbacks  {


    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

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
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(buildAdapter());
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
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

    public void showButterBar(String messageText, String actionText, long timeout,
                              View.OnClickListener listener){
        UIUtils.setUpButterBar(findViewById(R.id.butter_bar), messageText, actionText, timeout, listener);
    }

    public void hideButterBar(){
        findViewById(R.id.butter_bar).setVisibility(View.GONE);
    }


    @Override
    protected void goToNavDrawerItem(int item) {
    }


    @Override
    protected void onActionBarAutoShowOrHide(boolean shown) {
        super.onActionBarAutoShowOrHide(shown);
        mDrawShadowFrameLayout.setShadowVisible(shown, shown);
    }


    @Override
    public boolean canSwipeRefreshChildScrollUp() {
//        if (mEventsFrag != null) {
//            return true;
//        }
        return super.canSwipeRefreshChildScrollUp();
    }

    private DrawShadowFrameLayout mDrawShadowFrameLayout;

    @Override protected int getLayoutResource() {
        return R.layout.tabs_layout;
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return BaseActivity.NAVDRAWER_ITEM_ACTIVITY1;
    }
}
