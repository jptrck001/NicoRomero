package com.adedo;

import android.content.Context;

/**
 * Created by Rulo-PC on 23/4/2016.
 */
public class Utilities {

    public static String getMonthString(int month) {

        String result = "";

        switch (month) {
            case 1: result = "ene"; break;
            case 2: result = "feb"; break;
            case 3: result = "mar"; break;
            case 4: result = "abr"; break;
            case 5: result = "may"; break;
            case 6: result = "jun"; break;
            case 7: result = "jul"; break;
            case 8: result = "ago"; break;
            case 9: result = "sep"; break;
            case 10: result = "oct"; break;
            case 11: result = "nov"; break;
            case 12: result = "dic"; break;
        }
        return result;
    }

    public static String getUrl(Context context) {
        return context.getResources().getString(R.string.string_url);
    }

}
