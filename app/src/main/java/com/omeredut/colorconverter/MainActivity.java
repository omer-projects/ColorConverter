package com.omeredut.colorconverter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //static variables of the number pages ViewPager
    private final static int RGB_NUMBER_PAGE = 0;
    private final static int HSV_NUMBER_PAGE = 1;
    private final static int CMYK_NUMBER_PAGE = 2;
    private final static int HEX_NUMBER_PAGE = 3;

    //variable of the color
    private ColorConverter colorConverter;
    private int color;
    private int currentPage;

    //clipboard variables
    private ClipboardManager clipboardManager;
    private ClipData clipData;

    //main container variable
    private LinearLayout mainLayout;


    //toolbar variables
    private Toolbar toolbar;

    //tabs variables
    private TabLayout tabLayout;

    //viewPager variables
    private ViewPager colorPickerViewPager;
    private PagerAdapter colorPickerAdapter;

    //pointers for the colorPickerViewPager pages
    private RGBSeekBarPage rgbPage;
    private HSVSeekBarPage hsvPage;
    private HEXSeekBarPage hexPage;
    private CMYKSeekBarPage cmykPage;


    //alpha variable
    private SeekBar alphaSeekBar;
    private TextView colorPickerSurface;
    private TextView alphaPresentTextView;
    private float alpha;


    //adView variables
    /*private AdView adView;
    private AdRequest adRequest = null;*/

    //Spinner spinner;
    ArrayList<ImageView> imageViewArrayList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init color variables
        colorConverter = new ColorConverter();
        color = colorConverter.RGB2Color(128, 128, 128);
        currentPage = 0;

        //init clipboard variables;
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        //init main container
        mainLayout = (LinearLayout) findViewById(R.id.activity_main);

        //init toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //init tabLayout
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.rgb_title)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.hsv_title)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.cmyk_title)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.hex_title)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //adView
        /*adView = (AdView) findViewById(R.id.ad_view);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (adRequest != null){
                    adView.loadAd(adRequest);
                }
            }
        }.execute();*/




        //viewPager controllers
        colorPickerViewPager = (ViewPager) findViewById(R.id.container_rgb);
        colorPickerAdapter = new PickerColorPagerAdapter(getSupportFragmentManager());
        colorPickerViewPager.setAdapter(colorPickerAdapter);
        colorPickerViewPager.setOffscreenPageLimit(4);
        colorPickerViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPage = tab.getPosition();
                colorPickerViewPager.setCurrentItem(currentPage);

                switch (tab.getPosition()){
                    case 0:
                        rgbPage = (RGBSeekBarPage) colorPickerAdapter.instantiateItem(colorPickerViewPager, RGB_NUMBER_PAGE);
                        rgbPage.updateRGBSeekBar(color);
                        break;
                    case 1:
                        hsvPage = (HSVSeekBarPage) colorPickerAdapter.instantiateItem(colorPickerViewPager, HSV_NUMBER_PAGE);
                        hsvPage.updateHSVSeekBar(color);
                        break;
                    case 2:
                        cmykPage = (CMYKSeekBarPage) colorPickerAdapter.instantiateItem(colorPickerViewPager, CMYK_NUMBER_PAGE);
                        cmykPage.updateCMYKSeekBar(color);
                        break;
                    case 3:
                        hexPage = (HEXSeekBarPage) colorPickerAdapter.instantiateItem(colorPickerViewPager, HEX_NUMBER_PAGE);
                        hexPage.setText(color);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        rgbPage = null;
                        break;
                    case 1:
                        hsvPage = null;
                        break;
                    case 2:
                        cmykPage = null;
                        break;
                    case 3:
                        hexPage = null;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


        //alpha bar action
        initAlphaBar();
        alphaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alpha = (float)(progress/100.0);
                colorPickerSurface.setAlpha(alpha);
                alphaPresentTextView.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        updateColor(color);


    }



    //methods

    //toolbar methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.actions_for_action_bar, menu);

        //spinner = (Spinner) findViewById(R.id.spinner);
        ImagesSpinnerAdapter imagesSpinnerAdapter = new ImagesSpinnerAdapter();

        //ArrayAdapter<ImageView> adapter = new ArrayAdapter<>(this, R.layout.icons_for_spinner, R.id.group_icons_for_spinner);
        //ArrayAdapter<ImageView> adapter = new ArrayAdapter<ImageView>(this, R.layout.icons_for_spinner, imageViewArrayList);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //imagesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);
        //spinner.setAdapter(imagesSpinnerAdapter);

        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about_action_bar:
                showAboutFragment(item);
                break;
            case R.id.copy_action_bar:
                copyCurrentColorFormat();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    //private methods

    //method for initialize the alphaBar
    private void initAlphaBar(){

        alphaSeekBar = (SeekBar) findViewById(R.id.alpha_seek_bar);
        colorPickerSurface = (TextView) findViewById(R.id.color_picker_surface);
        alphaPresentTextView = (TextView) findViewById(R.id.alpha_present_text_view);

        alpha = 1;
        alphaSeekBar.setProgress((int)alpha*100);
        alphaPresentTextView.setText((int)alpha*100 + "%");
        colorPickerSurface.setAlpha(alpha);
    }


    //public methods

    public void setColor(int color){
        this.color = color;
    }

    public int getColor(){
        return color;
    }

    //method for update the surfaceColor
    public void updateColor(int colorForUpdate) {

        color = colorForUpdate;
        new AsyncTask<Integer, Void, Integer>() {
            @Override
            protected Integer doInBackground(Integer... params) {
                return params[0];
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                colorPickerSurface.setBackgroundColor(integer);
            }
        }.execute(color);
    }





    private void showAboutFragment(MenuItem item){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(item.getTitle()).setMessage(getString(R.string.generic_about_dialog) + "\n\n" + getString(R.string.rgb_about_dialog) + "\n\n" +
                getString(R.string.hsv_about_dialog) + "\n\n" + getString(R.string.cmyk_about_dialog) + "\n\n" + getString(R.string.hex_about_dialog)).create();
        alertDialogBuilder.show();
    }


    private void copyCurrentColorFormat(){
        switch (currentPage){
            case RGB_NUMBER_PAGE:
                clipData = ClipData.newPlainText(getString(R.string.copy_lable), rgbPage.getStringColor());
                break;
            case HSV_NUMBER_PAGE:
                clipData = ClipData.newPlainText(getString(R.string.copy_lable), hsvPage.getStringColor());
                break;
            case HEX_NUMBER_PAGE:
                clipData = ClipData.newPlainText(getString(R.string.copy_lable), hexPage.getStringColor());
                break;
            case CMYK_NUMBER_PAGE:
                clipData = ClipData.newPlainText(getString(R.string.copy_lable), cmykPage.getStringColor());
                break;
        }
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, getString(R.string.color_copied_toast), Toast.LENGTH_SHORT).show();
    }
}
