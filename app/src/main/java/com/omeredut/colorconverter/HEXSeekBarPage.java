package com.omeredut.colorconverter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by omeredut on 30/06/2017.
 */

public class HEXSeekBarPage extends Fragment {

    private ViewGroup hexPage;
    private EditText hexEditText;


    private MainActivity hexMainActivity;
    private ColorConverter hexColorConverter;
    private String hexColorString;
    private String hexTextString;

    private String fromUpdateString = "";

    private int hexColor;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        hexColorConverter = new ColorConverter();
        hexMainActivity = (MainActivity) getActivity();


        hexColor = hexMainActivity.getColor();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        hexPage = (ViewGroup) inflater.inflate(R.layout.hex_color_page, container, false);


        hexEditText = (EditText) hexPage.findViewById(R.id.hex_edit_text);
        verifyValidationHexString(hexEditText);

        return hexPage;
    }



    private void verifyValidationHexString(final EditText hexEditText){
        hexEditText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (before != 0 || start != 0){
                    fromUser = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


                if (fromUser) {
                    String hexTextString = s.toString();
                    //first step
                    alwaysStartWith(hexTextString);
                    //second step
                    stopAddCharsToEditText(hexTextString);
                    //third step
                    hexTextString = validString(hexTextString);
                    //fourth step
                    matching(hexTextString);
                    //set cursor in the end of the string
                    hexEditText.setSelection(hexTextString.length());

                } else {
                    fromUpdateString = hexColorConverter.color2Hex(hexMainActivity.getColor());
                    if (!(s.toString().equals(fromUpdateString))){
                        hexEditText.setText(fromUpdateString);
                    }
                }
            }
        });
    }


    private boolean checkValidationHexChar(char hexChar){
        int asciiChar = (int)hexChar;
        if ((asciiChar > 64 && asciiChar < 71) || (asciiChar > 96 && asciiChar < 103) || (asciiChar > 47 && asciiChar < 58))
            return true;
        else
            return false;
    }










    boolean fromUser = false;
    private void stopAddCharsToEditText(){
        //stop adding chars to the hexString
        if (hexEditText.length() > 7){
            hexTextString = hexTextString.substring(0, 7);
        }
    }




    //first step
    private void alwaysStartWith(String s){
        //always start with #
        if (s.length() > 0) {
            if (s.charAt(0) != '#') {
                hexEditText.setText("#" + s);
            }
        } else {
            hexEditText.setText("#");
        }
    }


    //second step
    private void stopAddCharsToEditText(String s){
        //stop adding chars to the hexString

        if (s.length() > 7){
            String str = s.toString();
            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... params) {
                    String str = params[0].substring(0, 7);
                    return str;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    hexEditText.setText(s);
                }
            }.execute(str);



        }
    }


    //third step
    private String validString(String s){
        //check if the hexString is valid
        String checkValidationString = "#";
        if (s.length() <= 7){
            for (int i = 1; i < s.length(); i++) {
                if (checkValidationHexChar(s.charAt(i))){
                    checkValidationString += s.charAt(i);
                }
            }
        } else {
            hexEditText.setText(s.substring(0, 7));
        }
        if (!(checkValidationString.equals(s.toString()))){
            hexEditText.setText(checkValidationString);
        }
        return  checkValidationString;
    }



    //fourth step
    private void matching(String s){
        //set the color matching hexString
        if (s.length() == 7){
            hexColorString = s.substring(1, 7);
            hexColor = hexColorConverter.hex2Color(hexColorString);
            hexMainActivity.updateColor(hexColor);
        }
    }


    public void setText(final int color){
        fromUser = false;
        fromUpdateString = hexColorConverter.color2Hex(color);
        hexEditText.setText(fromUpdateString);
        fromUser = true;


    }



    public String getStringColor(){
        return hexColorConverter.color2Hex(hexColor);
    }

}
