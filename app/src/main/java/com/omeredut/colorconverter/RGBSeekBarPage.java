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

public class RGBSeekBarPage extends Fragment {


    private static final int MAX_RED_SEEK_BAR = 255;
    private static final int MAX_GREEn_SEEK_BAR = 255;
    private static final int MAX_BLUE_SEEK_BAR = 255;
    public static final int RED_COLOR = 0;
    public static final int GREEN_COLOR = 1;
    public static final int BLUE_COLOR = 2;

    private ViewGroup rgbPage;


    //Colors variables
    private int[] rgb;
    private int rgbColor;
    private ColorConverter rgbColorConverter;

    //Red variables bar
    private TextView rTitleTextView;
    private SeekBar rSeekBar;
    private TextView rTextView;

    //Green variables bar
    private TextView gTitleTextView;
    private SeekBar gSeekBar;
    private TextView gTextView;

    //Blue variables bar
    private TextView bTitleTextView;
    private SeekBar bSeekBar;
    private TextView bTextView;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //generic listeners variable
        rgbColorConverter = new ColorConverter();
        rgbMainActivity = (MainActivity) getActivity();


        rgb = new int[3];

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rgbColor = rgbMainActivity.getColor();
        rgb = rgbColorConverter.color2RGB(rgbColor);

        rgbPage = (ViewGroup) inflater.inflate(R.layout.color_seek_bar_page, container, false);

        //init the page
        rTitleTextView = (TextView) rgbPage.findViewById(R.id.red_title_text_view);
        rTitleTextView.setText(getString(R.string.red_title));

        gTitleTextView = (TextView) rgbPage.findViewById(R.id.green_title_text_view);
        gTitleTextView.setText(getString(R.string.green_title));

        bTitleTextView = (TextView) rgbPage.findViewById(R.id.blue_title_text_view);
        bTitleTextView.setText(getString(R.string.blue_title));



        rSeekBar = (SeekBar) rgbPage.findViewById(R.id.r_color_seek_bar);
        rSeekBar.setMax(MAX_RED_SEEK_BAR);
        gSeekBar = (SeekBar) rgbPage.findViewById(R.id.g_color_seek_bar);
        gSeekBar.setMax(MAX_GREEn_SEEK_BAR);
        bSeekBar = (SeekBar) rgbPage.findViewById(R.id.b_color_seek_bar);
        bSeekBar.setMax(MAX_BLUE_SEEK_BAR);

        rSeekBar.setProgress(rgb[RED_COLOR]);
        gSeekBar.setProgress(rgb[GREEN_COLOR]);
        bSeekBar.setProgress(rgb[BLUE_COLOR]);


        rTextView = (TextView) rgbPage.findViewById(R.id.r_color_text_view);
        gTextView = (TextView) rgbPage.findViewById(R.id.g_color_text_view);
        bTextView = (TextView) rgbPage.findViewById(R.id.b_color_text_view);

        rTextView.setText(String.valueOf(rgb[RED_COLOR]));
        gTextView.setText(String.valueOf(rgb[GREEN_COLOR]));
        bTextView.setText(String.valueOf(rgb[BLUE_COLOR]));

        updateSeekBar(rSeekBar, rTextView, RED_COLOR);
        updateSeekBar(gSeekBar, gTextView, GREEN_COLOR);
        updateSeekBar(bSeekBar, bTextView, BLUE_COLOR);

        return rgbPage;
    }



    public void updateSeekBar(SeekBar seekBar, final TextView textView, final int witchColor){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (witchColor == RED_COLOR || witchColor == GREEN_COLOR || witchColor == BLUE_COLOR) {
                        rgb[witchColor] = progress;
                        rgbColor = rgbColorConverter.RGB2Color(rgb[RED_COLOR], rgb[GREEN_COLOR], rgb[BLUE_COLOR]);
                        textView.setText(String.valueOf(progress));
                        rgbMainActivity.updateColor(rgbColor);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }




    //listeners methods and variables
    private MainActivity rgbMainActivity;


    public void updateRGBSeekBar(final int color) {
        rgb = rgbColorConverter.color2RGB(color);
        rSeekBar.setProgress(rgb[RED_COLOR]);
        gSeekBar.setProgress(rgb[GREEN_COLOR]);
        bSeekBar.setProgress(rgb[BLUE_COLOR]);
        rTextView.setText(String.valueOf(rgb[RED_COLOR]));
        gTextView.setText(String.valueOf(rgb[GREEN_COLOR]));
        bTextView.setText(String.valueOf(rgb[BLUE_COLOR]));
    }

    private void setColor(int color){
        rTitleTextView.setTextColor(color);
        rTextView.setTextColor(color);
        gTitleTextView.setTextColor(color);
        gTextView.setTextColor(color);
        bTitleTextView.setTextColor(color);
        bTextView.setTextColor(color);
    }



    public String getStringColor(){
        return "RGB(" + rgb[RED_COLOR] + ", " + rgb[GREEN_COLOR] + ", " + rgb[BLUE_COLOR] + ")";
    }
}
