package com.omeredut.colorconverter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SpinnerAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by omeredut on 21/08/2017.
 */

public class ImagesSpinnerAdapter extends BaseAdapter {

    private static Integer[] iconsSpinnerRes = {R.mipmap.ic_content_copy_white_18dp, R.mipmap.ic_content_copy_white_18dp, R.mipmap.ic_content_copy_white_18dp};
    private ImageView iconImageView;


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = new ImageView(parent.getContext());
        imageView.setImageResource(iconsSpinnerRes[position]);
        return imageView;

    }
}
