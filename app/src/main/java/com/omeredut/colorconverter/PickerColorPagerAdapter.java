package com.omeredut.colorconverter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by omeredut on 30/06/2017.
 */

public class PickerColorPagerAdapter extends FragmentStatePagerAdapter {

    public static final int NUMBER_OF_PAGE_IN_VIEW_PAGER = 4;

    public PickerColorPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RGBSeekBarPage();
            case 1:
                return new HSVSeekBarPage();
            case 2:
                return new CMYKSeekBarPage();
            case 3:
                return new HEXSeekBarPage();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_PAGE_IN_VIEW_PAGER;
    }


}
