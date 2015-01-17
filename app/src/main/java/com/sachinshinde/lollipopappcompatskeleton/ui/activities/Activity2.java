package com.sachinshinde.lollipopappcompatskeleton.ui.activities;

import android.os.Bundle;

import com.sachinshinde.lollipopappcompatskeleton.R;
import com.sachinshinde.lollipopappcompatskeleton.ui.fragments.OtherFragment;
import com.sachinshinde.lollipopappcompatskeleton.ui.widgets.CallbackFragment;

/**
 * Created by sachin on 17/1/15.
 */
public class Activity2 extends BaseActivity implements
        CallbackFragment.Callbacks  {

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        bundle.putString("Title", "Activity 2");

        OtherFragment of = new OtherFragment();
        of.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.sessions_fragment, of).commit();

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void onItemSelected(long l) {

    }

    @Override
    public int getSelectedFragment() {
        return BaseActivity.NAVDRAWER_ITEM_ACTIVITY2;
    }

    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_ACTIVITY2;
    }
}
