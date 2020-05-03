package com.omeredut.colorconverter;

import android.graphics.Color;

/**
 * Created by omeredut on 27/06/2017.
 */

public class ColorConverter {


    //RGB color
    private int rgb[];

    //HSL color
    private float[] hsv;

    //hex color
    private String hexColor;
    private String mHex;

    //CMYK
    private float[] cmyk;

    public ColorConverter(){
        rgb = new int[3];
        hsv = new float[3];
        cmyk = new float[4];
        mHex = "";
        hexColor = "";
    }


    //convert between RGB and color.
    public int RGB2Color(int r, int g, int b){
        return Color.argb(1000, r, g, b);
    }
    public int[] color2RGB(int color){
        rgb[0] = Color.red(color);
        rgb[1] = Color.green(color);
        rgb[2] = Color.blue(color);
        return rgb;
    }


    //convert between HSV and color.
    public int HSV2Color(float[] hsv){
        return Color.HSVToColor(hsv);
    }
    public float[] color2HSV(int color){
        Color.colorToHSV(color, hsv);
        return hsv;
    }





    //convert between HEX and color.

    /*use RGB2Color method*/


    public int hex2Color(String hex){
        //i am assume that the hex is valid
        return Color.parseColor("#"+hex);

    }

    public String RGB2Hex(int[] rgb){
        if (rgb.length == 3) {
            int r = rgb[0];
            int g = rgb[1];
            int b = rgb[2];
            return String.format("#%02x%02x%02x", r, g, b);
        } else {
            return null;
        }
    }

    public String color2Hex(int color){
        int[] rgb = new int[3];
        rgb = color2RGB(color);
        String hexString = RGB2Hex(rgb);
        return hexString;
    }



    public int cmyk2Color(int[] cmyk){
        if (cmyk.length == 4) {

            float c = cmyk[0]/100f;
            float m = cmyk[1]/100f;
            float y = cmyk[2]/100f;
            float k = cmyk[3]/100f;

            float nc = (c * (1 - k) + k);
            float nm = (m * (1 - k) + k);
            float ny = (y * (1 - k) + k);

            rgb[0] = (int)(Math.ceil((1 - nc) * 255));
            rgb[1] = (int)(Math.ceil((1 - nm) * 255));
            rgb[2] = (int)(Math.ceil((1 - ny) * 255));

            return RGB2Color(rgb[0], rgb[1], rgb[2]);
        } else {
            return -1;
        }
    }

    public int[] color2Cmyk(int color){
        rgb = color2RGB(color);
        double r = rgb[0]/255.0;
        double g = rgb[1]/255.0;
        double b = rgb[2]/255.0;

        float k = (float) (1 - (Math.max(r, Math.max(g, b))));
        float c = (float) ((1 - r - k)/(1 - k));
        float m = (float) ((1 - g - k)/(1 - k));
        float y = (float) ((1 - b - k)/(1 - k));

        int[] newCmyk = new int[4];
        newCmyk[0] = (int)(c*100);
        newCmyk[1] = (int)(m*100);
        newCmyk[2] = (int)(y*100);
        newCmyk[3] = (int)(k*100);

        return newCmyk;
    }
}
