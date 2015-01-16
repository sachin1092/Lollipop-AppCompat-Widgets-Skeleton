/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sachinshinde.lollipopappcompatskeleton.utils;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sachinshinde.lollipopappcompatskeleton.R;


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class UIUtils {
    private static Typeface sMediumTypeface;

    protected ActionBarActivity mActivity;
    private Handler mHandler = new Handler();

    private UIUtils(ActionBarActivity activity) {
        mActivity = activity;
    }

    public static UIUtils getInstance(ActionBarActivity activity) {
        return new UIUtils(activity);
    }

    private static boolean hasL() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public void startActivityWithTransition(Intent intent, final View clickedView,
                                            final String transitionName) {
        ActivityOptions options = null;
        if (hasL() && clickedView != null && !TextUtils.isEmpty(transitionName)) {
            options = ActivityOptions.makeSceneTransitionAnimation(
                    mActivity, clickedView, transitionName);
        }

        mActivity.startActivity(intent, (options != null) ? options.toBundle() : null);
    }

    public void setMediumTypeface(TextView textView) {
        if (hasL()) {
            if (sMediumTypeface == null) {
                sMediumTypeface = Typeface.create("sans-serif-medium", Typeface.NORMAL);
            }

            textView.setTypeface(sMediumTypeface);
        } else {
            textView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        }
    }

    public int getStatusBarColor() {
        if (!hasL()) {
            // On pre-L devices, you can have any status bar color so long as it's black.
            return Color.BLACK;
        }

        return mActivity.getWindow().getStatusBarColor();
    }

    public void setStatusBarColor(int color) {
        if (!hasL()) {
            return;
        }

        mActivity.getWindow().setStatusBarColor(color);
    }

    /**
     * Set up and show butter bar
     * @param butterBar
     * @param messageText
     * @param actionText
     * @param timeout time in milliseconds, pass 0 for never timeout
     * @param listener
     */

    public static void setUpButterBar(final View butterBar, String messageText, String actionText, long timeout,
                                      View.OnClickListener listener) {
        if (butterBar == null) {
            Log.e("ButterBar", "Failed to set up butter bar: it's null.");
            return;
        }

        TextView textView = (TextView) butterBar.findViewById(R.id.butter_bar_text);
        if (textView != null) {
            textView.setText(messageText);
        }

        Button button = (Button) butterBar.findViewById(R.id.butter_bar_button);
        if (button != null) {
            button.setText(actionText == null ? "" : actionText);
            button.setVisibility(!TextUtils.isEmpty(actionText) ? View.VISIBLE : View.GONE);
        }

        button.setOnClickListener(listener);
        butterBar.setVisibility(View.VISIBLE);

        Animator anim = AnimatorInflater.loadAnimator(butterBar.getContext(), R.animator.fade_in_animator);
        anim.setDuration(800);
        anim.setTarget(butterBar);
        anim.start();

        if(timeout != 0)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    butterBar.setVisibility(View.GONE);
                    Animator anim = AnimatorInflater.loadAnimator(butterBar.getContext(), R.animator.fade_out_animator);
                    anim.setDuration(800);
                    anim.setTarget(butterBar);
                    anim.start();

                }
            }, timeout);
    }

    public static AlertDialog getProgressDialog(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View view = LayoutInflater.from(activity).inflate(
                R.layout.progress_dialog, null);
        View img1 = view.findViewById(R.id.pd_circle1);
        View img2 = view.findViewById(R.id.pd_circle2);
        View img3 = view.findViewById(R.id.pd_circle3);
        int ANIMATION_DURATION = 400;
        Animator anim1 = setRepeatableAnim(activity, img1, ANIMATION_DURATION, R.animator.growndisappear);
        Animator anim2 = setRepeatableAnim(activity, img2, ANIMATION_DURATION, R.animator.growndisappear);
        Animator anim3 = setRepeatableAnim(activity, img3, ANIMATION_DURATION, R.animator.growndisappear);
        setListeners(img1, anim1, anim2, ANIMATION_DURATION);
        setListeners(img2, anim2, anim3, ANIMATION_DURATION);
        setListeners(img3, anim3, anim1, ANIMATION_DURATION);
        anim1.start();
        builder.setView(view);
        AlertDialog ad = builder.create();
        ad.setCanceledOnTouchOutside(false);
        ad.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        ad.show();
        ad.getWindow().setLayout(dpToPx(200, activity), dpToPx(125, activity));
        return ad;
    }

    /**
     * Convert dp to px
     *
     * @author Sachin
     * @param i
     * @param mContext
     * @return
     */

    public static int dpToPx(int i, Context mContext) {

        DisplayMetrics displayMetrics = mContext.getResources()
                .getDisplayMetrics();
        return (int) ((i * displayMetrics.density) + 0.5);

    }


    private static Animator setRepeatableAnim(Activity activity, View target, final int duration, int animRes){
        final Animator anim = AnimatorInflater.loadAnimator(activity, animRes);
        anim.setDuration(duration);
        anim.setTarget(target);
        return anim;
    }

    private static void setListeners(final View target, Animator anim, final Animator animator, final int duration){
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animat) {
                if(target.getVisibility() == View.INVISIBLE){
                    target.setVisibility(View.VISIBLE);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animator.start();
                    }
                }, duration - 100);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
