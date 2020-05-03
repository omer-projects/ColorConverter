package com.omeredut.colorconverter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by omeredut on 30/06/2017.
 */

public class HSVSeekBarPage extends Fragment {

    public static final int MAX_HUE_SEEK_BAR = 360;
    public static final int MAX_SATURATION_SEEK_BAR = 100;
    public static final int MAX_VALUE_SEEK_BAR = 100;
    public static final int HUE_COLOR = 0;
    public static final int SATURATION_COLOR = 1;
    public static final int VALUE_COLOR = 2;

    ViewGroup hsvPage;

    private TextView hTitleTextView;
    private TextView sTitleTextView;
    private TextView vTitleTextView;

    private SeekBar hSeekBar;
    private SeekBar sSeekBar;
    private SeekBar vSeekBar;

    private TextView hTextView;
    private TextView sTextView;
    private TextView vTextView;

    private float[] hsv = {(float) 0.0, (float) 0.0, (float) 50.0};
    private int hsvColor;

    private MainActivity hsvMainActivity;

    private ColorConverter hsvColorConverter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //generic listeners variable
        hsvMainActivity = (MainActivity) getActivity();
        hsvColorConverter = new ColorConverter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        hsvColor = hsvMainActivity.getColor();
        hsv = hsvColorConverter.color2HSV(hsvColor);

        //init the page
        hsvPage = (ViewGroup) inflater.inflate(R.layout.color_seek_bar_page, container, false);


        hTitleTextView = (TextView) hsvPage.findViewById(R.id.red_title_text_view);
        hTitleTextView.setText(getString(R.string.hue_title));

        sTitleTextView = (TextView) hsvPage.findViewById(R.id.green_title_text_view);
        sTitleTextView.setText(getString(R.string.saturation_title));

        vTitleTextView = (TextView) hsvPage.findViewById(R.id.blue_title_text_view);
        vTitleTextView.setText(getString(R.string.value_title));



        hSeekBar = (SeekBar) hsvPage.findViewById(R.id.r_color_seek_bar);
        hSeekBar.setMax(MAX_HUE_SEEK_BAR);
        sSeekBar = (SeekBar) hsvPage.findViewById(R.id.g_color_seek_bar);
        sSeekBar.setMax(MAX_SATURATION_SEEK_BAR);
        vSeekBar = (SeekBar) hsvPage.findViewById(R.id.b_color_seek_bar);
        vSeekBar.setMax(MAX_VALUE_SEEK_BAR);


        hTextView = (TextView) hsvPage.findViewById(R.id.r_color_text_view);
        sTextView = (TextView) hsvPage.findViewById(R.id.g_color_text_view);
        vTextView = (TextView) hsvPage.findViewById(R.id.b_color_text_view);


        hSeekBar.setProgress((int)hsv[HUE_COLOR]);
        sSeekBar.setProgress((int)hsv[SATURATION_COLOR]);
        vSeekBar.setProgress((int)hsv[VALUE_COLOR]);

        hTextView.setText(String.valueOf((int)hsv[HUE_COLOR]));
        sTextView.setText(String.valueOf((int)hsv[SATURATION_COLOR]));
        vTextView.setText(String.valueOf((int)hsv[VALUE_COLOR]));

        //updateHSVSeekBar(hsvColor);

        updateSeekBar(hSeekBar, hTextView, HUE_COLOR);
        updateSeekBar(sSeekBar, sTextView, SATURATION_COLOR);
        updateSeekBar(vSeekBar, vTextView, VALUE_COLOR);

        return hsvPage;
    }


    private void updateSeekBar(SeekBar seekBar, final TextView textView, final int whichColor){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (whichColor == HUE_COLOR || whichColor == SATURATION_COLOR || whichColor == VALUE_COLOR) {
                        if (whichColor == HUE_COLOR) {
                            hsv[whichColor] = (float) (progress + 0.0);
                        } else {
                            double val = progress / 100.0 + 0.0;
                            float v = (float) val;
                            hsv[whichColor] = (float) val;
                        }
                        textView.setText(String.valueOf(progress));
                        hsvColor = hsvColorConverter.HSV2Color(hsv);
                        hsvMainActivity.updateColor(hsvColor);

                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }





    public void updateHSVSeekBar(int color){
        hsv = hsvColorConverter.color2HSV(color);

        hSeekBar.setProgress((int)hsv[HUE_COLOR]);
        hTextView.setText(String.valueOf((int)hsv[HUE_COLOR]));
        sSeekBar.setProgress((int)(hsv[SATURATION_COLOR]*100));
        sTextView.setText(String.valueOf((int)(hsv[SATURATION_COLOR]*100)));
        vSeekBar.setProgress((int)(hsv[VALUE_COLOR]*100));
        vTextView.setText(String.valueOf((int)(hsv[VALUE_COLOR]*100)));
    }



    public String getStringColor(){
        return "HSV(" + (int)(hsv[HUE_COLOR]) + ", " + (int)(hsv[SATURATION_COLOR]*100) + ", " + (int)(hsv[VALUE_COLOR]*100) + ")";
    }


}
