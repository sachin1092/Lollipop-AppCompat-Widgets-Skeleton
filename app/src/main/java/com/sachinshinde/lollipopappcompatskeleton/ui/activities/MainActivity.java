package com.sachinshinde.lollipopappcompatskeleton.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.sachinshinde.lollipopappcompatskeleton.R;
import com.sachinshinde.lollipopappcompatskeleton.ui.fragments.Section1Fragment;
import com.sachinshinde.lollipopappcompatskeleton.ui.fragments.Section2Fragment;
import com.sachinshinde.lollipopappcompatskeleton.ui.fragments.Section3Fragment;
import com.sachinshinde.lollipopappcompatskeleton.ui.views.CallbackFragment;
import com.sachinshinde.lollipopappcompatskeleton.ui.views.DrawShadowFrameLayout;
import com.sachinshinde.lollipopappcompatskeleton.utils.UIUtils;


public class MainActivity extends BaseActivity implements
        CallbackFragment.Callbacks  {

    int currFrag = BaseActivity.NAVDRAWER_ITEM_SECTION1;

    @Override
    public void onItemSelected(long id) {

    }

    @Override
    public int getSelectedFragment() {
        return BaseActivity.NAVDRAWER_ITEM_SECTION1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentTransaction ft;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        s1 = new Section1Fragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.sessions_fragment,
                            s1).commit();


    }

    public void showButterBar(String messageText, String actionText, long timeout,
                              View.OnClickListener listener){
        UIUtils.setUpButterBar(findViewById(R.id.butter_bar), messageText, actionText, timeout, listener);
    }

    public void hideButterBar(){
        findViewById(R.id.butter_bar).setVisibility(View.GONE);
    }

    Section1Fragment s1;
    Section2Fragment s2;
    Section3Fragment s3;

    @Override
    protected void goToNavDrawerItem(int item) {
        currFrag = item;
        FragmentManager fm;
        ft = getSupportFragmentManager().beginTransaction();
        switch (item) {
            case NAVDRAWER_ITEM_SECTION1:
                fm = MainActivity.this.getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                ft.replace(R.id.sessions_fragment, s1 = new Section1Fragment());
                break;
            case NAVDRAWER_ITEM_SECTION2:
                fm = MainActivity.this.getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                ft.replace(R.id.sessions_fragment, s2 = new Section2Fragment());
                break;
            case NAVDRAWER_ITEM_SECTION3:
                fm = MainActivity.this.getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                ft.replace(R.id.sessions_fragment, s3 = new Section3Fragment());
                break;
        }
        ft.addToBackStack(null);
        ft.commit();
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
    }    private DrawShadowFrameLayout mDrawShadowFrameLayout;

    @Override protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return currFrag;
    }
}
