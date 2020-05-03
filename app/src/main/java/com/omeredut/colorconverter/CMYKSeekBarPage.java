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
 * Created by omeredut on 01/08/2017.
 */

public class CMYKSeekBarPage extends Fragment {

    public static final int MAX_CYAN_SEEK_BAR = 100;
    public static final int MAX_MAGENTA_SEEK_BAR = 100;
    public static final int MAX_YELLOW_SEEK_BAR = 100;
    public static final int MAX_KEY_SEEK_BAR = 100;
    public static final int CYAN_COLOR = 0;
    public static final int MAGENTA_COLOR = 1;
    public static final int YELLOW_COLOR = 2;
    public static final int KEY_COLOR = 3;

    private ViewGroup cmykPage;

    private TextView cTitleTextView;
    private TextView mTitleTextView;
    private TextView yTitleTextView;
    private TextView kTitleTextView;

    private SeekBar cSeekBar;
    private SeekBar mSeekBar;
    private SeekBar ySeekBar;
    private SeekBar kSeekBar;

    private TextView cTextView;
    private TextView mTextView;
    private TextView yTextView;
    private TextView kTextView;

    private int[] cmyk;
    private int cmykColor;

    private MainActivity cmykMainActivity;

    private ColorConverter cmykColorConverter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //generic listeners variable
        cmykColorConverter = new ColorConverter();
        cmykMainActivity = (MainActivity) getActivity();
        cmyk = new int[4];
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        cmykColor = cmykMainActivity.getColor();
        cmyk = cmykColorConverter.color2Cmyk(cmykColor);

        //init the page
        cmykPage = (ViewGroup) inflater.inflate(R.layout.cmyk_color_page, container, false);


        cTitleTextView = (TextView) cmykPage.findViewById(R.id.c_title_text_view);
        cTitleTextView.setText(getString(R.string.cyan_title));

        mTitleTextView = (TextView) cmykPage.findViewById(R.id.m_title_text_view);
        mTitleTextView.setText(getString(R.string.magenta_title));

        yTitleTextView = (TextView) cmykPage.findViewById(R.id.y_title_text_view);
        yTitleTextView.setText(getString(R.string.yellow_title));

        kTitleTextView = (TextView) cmykPage.findViewById(R.id.k_title_text_view);
        kTitleTextView.setText(getString(R.string.key_title));



        cSeekBar = (SeekBar) cmykPage.findViewById(R.id.c_color_seek_bar);
        cSeekBar.setMax(MAX_CYAN_SEEK_BAR);
        mSeekBar = (SeekBar) cmykPage.findViewById(R.id.m_color_seek_bar);
        mSeekBar.setMax(MAX_MAGENTA_SEEK_BAR);
        ySeekBar = (SeekBar) cmykPage.findViewById(R.id.y_color_seek_bar);
        ySeekBar.setMax(MAX_YELLOW_SEEK_BAR);
        kSeekBar = (SeekBar) cmykPage.findViewById(R.id.k_color_seek_bar);
        kSeekBar.setMax(MAX_KEY_SEEK_BAR);


        cTextView = (TextView) cmykPage.findViewById(R.id.c_color_text_view);
        mTextView = (TextView) cmykPage.findViewById(R.id.m_color_text_view);
        yTextView = (TextView) cmykPage.findViewById(R.id.y_color_text_view);
        kTextView = (TextView) cmykPage.findViewById(R.id.k_color_text_view);


        cSeekBar.setProgress((int)cmyk[CYAN_COLOR]);
        mSeekBar.setProgress((int)cmyk[MAGENTA_COLOR]);
        ySeekBar.setProgress((int)cmyk[YELLOW_COLOR]);
        kSeekBar.setProgress((int)cmyk[KEY_COLOR]);

        cTextView.setText(String.valueOf((int)cmyk[CYAN_COLOR]) + "%");
        mTextView.setText(String.valueOf((int)cmyk[MAGENTA_COLOR]) + "%");
        yTextView.setText(String.valueOf((int)cmyk[YELLOW_COLOR]) + "%");
        kTextView.setText(String.valueOf((int)cmyk[KEY_COLOR]) + "%");


        updateSeekBar(cSeekBar, cTextView, CYAN_COLOR);
        updateSeekBar(mSeekBar, mTextView, MAGENTA_COLOR);
        updateSeekBar(ySeekBar, yTextView, YELLOW_COLOR);
        updateSeekBar(kSeekBar, kTextView, KEY_COLOR);

        return cmykPage;
    }


    private void updateSeekBar(SeekBar seekBar, final TextView textView, final int whichColor){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (whichColor == CYAN_COLOR || whichColor == MAGENTA_COLOR || whichColor == YELLOW_COLOR || whichColor == KEY_COLOR) {

                        cmyk[whichColor] = progress;

                        textView.setText(String.valueOf(progress) + "%");
                        cmykColor = cmykColorConverter.cmyk2Color(cmyk);
                        cmykMainActivity.updateColor(cmykColor);

                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }





    public void updateCMYKSeekBar(int color){
        cmyk = cmykColorConverter.color2Cmyk(color);

        cSeekBar.setProgress(cmyk[CYAN_COLOR]);
        cTextView.setText(String.valueOf(cmyk[CYAN_COLOR]) + "%");
        mSeekBar.setProgress(cmyk[MAGENTA_COLOR]);
        mTextView.setText(String.valueOf(cmyk[MAGENTA_COLOR]) + "%");
        ySeekBar.setProgress(cmyk[YELLOW_COLOR]);
        yTextView.setText(String.valueOf(cmyk[YELLOW_COLOR]) + "%");
        kSeekBar.setProgress(cmyk[KEY_COLOR]);
        kTextView.setText(String.valueOf(cmyk[KEY_COLOR]) + "%");
    }

    public String getStringColor(){
        return "CMYK(" + cmyk[CYAN_COLOR] + ", " + cmyk[MAGENTA_COLOR] + ", " + cmyk[YELLOW_COLOR] + ", " + cmyk[KEY_COLOR] + ")";
    }


}
