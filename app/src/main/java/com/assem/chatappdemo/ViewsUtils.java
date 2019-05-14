package com.assem.chatappdemo;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


public class ViewsUtils {

    private static final String TAG = ViewsUtils.class.getSimpleName();

    public void setupLinearHorizontalRecView(Context context, RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setupLinearVerticalRecView(Context context, RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    public void setupGridRecView(Context context, RecyclerView recyclerView, int spanCount) {
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
    }


    public void setupTabLayout(
            ViewPager viewPager,
            ViewPagerAdapter viewPagerAdapter,
            final TabLayout tabLayout,
            int startTabIndex) {

        try {
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    try {
                        tab.getCustomView().animate().alpha(1).setDuration(300).setInterpolator(new AccelerateInterpolator(3));
                    } catch (Exception e) {
                        Log.e("tabLayout", e.toString());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    try {
                        tab.getCustomView().animate().alpha(0.9f).setDuration(100).setInterpolator(new DecelerateInterpolator(2));
                    } catch (Exception e) {
                        Log.e("tabLayout", e.toString());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });

//             Make margin between taps
            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < tabLayout.getTabCount(); i++) {
                        View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
                        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
                        p.setMargins(20, 0, 20, 0);
                        tab.requestLayout();
                    }
                }
            });

            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setCurrentItem(startTabIndex, false);

        } catch (Exception e) {
            Log.d(TAG, "Exception : " + e.toString());
        }

    }

/*

    public void setupViewPagerWithIcons(ViewPagerAdapter viewPagerAdapter, final TabLayout tabLayout, ViewPager viewPager, Fragment fragments[], final String fragNames[], final int fragIcons[], int currentItem) {

        try {

            for (int i = 0; i < fragments.length; i++) {
                viewPagerAdapter.addFragment(fragments[i]);
            }

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    try {
                        tab.getCustomView().animate().alpha(1).setDuration(300).setInterpolator(new AccelerateInterpolator(3));
                    } catch (Exception e) {
                        Log.e("tabLayout", e.toString());
                        e.printStackTrace();
                    }

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    try {
                        tab.getCustomView().animate().alpha(0.9f).setDuration(100).setInterpolator(new DecelerateInterpolator(2));
                    } catch (Exception e) {
                        Log.e("tabLayout", e.toString());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < fragNames.length; i++) {
                        tabLayout.getTabAt(i).setText(fragNames[i]);
                    }

                    for (int i = 0; i < fragIcons.length; i++) {
                        tabLayout.getTabAt(i).setIcon(fragIcons[i]);
                    }
                }
            });

            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setCurrentItem(currentItem, false);

        } catch (Exception e) {
            Log.d(TAG, "Exception : " + e.toString());
        }

    }
 */

}
