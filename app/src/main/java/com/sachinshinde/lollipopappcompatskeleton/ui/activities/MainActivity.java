package com.sachinshinde.lollipopappcompatskeleton.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sachinshinde.lollipopappcompatskeleton.R;
import com.sachinshinde.lollipopappcompatskeleton.ui.fragments.Section1Fragment;
import com.sachinshinde.lollipopappcompatskeleton.ui.fragments.Section2Fragment;
import com.sachinshinde.lollipopappcompatskeleton.ui.fragments.Section3Fragment;
import com.sachinshinde.lollipopappcompatskeleton.ui.views.CallbackFragment;
import com.sachinshinde.lollipopappcompatskeleton.ui.views.DrawShadowFrameLayout;


public class MainActivity extends BaseActivity implements
        CallbackFragment.Callbacks  {

    Section1Fragment mEventsFrag;

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
//        if (getSupportFragmentManager().findFragmentById(R.id.sessions_fragment) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.sessions_fragment,
                            new Section1Fragment()).commit();
//        }
    }

    @Override
    protected void goToNavDrawerItem(int item) {
        FragmentManager fm;
        ft = getSupportFragmentManager().beginTransaction();
//        ft.disallowAddToBackStack();
        switch (item) {
            case NAVDRAWER_ITEM_SECTION1:
                fm = MainActivity.this.getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
//                ft.add(R.id.sessions_fragment, new Section1Fragment());
                ft.replace(R.id.sessions_fragment, new Section1Fragment());
                break;
            case NAVDRAWER_ITEM_SECTION2:
                fm = MainActivity.this.getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                ft.replace(R.id.sessions_fragment, new Section2Fragment());
                break;
            case NAVDRAWER_ITEM_SECTION3:
                fm = MainActivity.this.getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                ft.replace(R.id.sessions_fragment, new Section3Fragment());
                break;
        }
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Converts an intent into a {@link android.os.Bundle} suitable for use as fragment arguments.
     */
    public static Bundle intentToFragmentArguments(Intent intent) {
        Bundle arguments = new Bundle();
        if (intent == null) {
            return arguments;
        }

        final Uri data = intent.getData();
        if (data != null) {
            arguments.putParcelable("_uri", data);
        }

        final Bundle extras = intent.getExtras();
        if (extras != null) {
            arguments.putAll(intent.getExtras());
        }

        return arguments;
    }


    @Override
    protected void onActionBarAutoShowOrHide(boolean shown) {
        super.onActionBarAutoShowOrHide(shown);
        mDrawShadowFrameLayout.setShadowVisible(shown, shown);
    }

    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        if (mEventsFrag != null) {
            return true;
        }
        return super.canSwipeRefreshChildScrollUp();
    }
    private DrawShadowFrameLayout mDrawShadowFrameLayout;

    @Override protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return BaseActivity.NAVDRAWER_ITEM_SECTION1;
    }
}
