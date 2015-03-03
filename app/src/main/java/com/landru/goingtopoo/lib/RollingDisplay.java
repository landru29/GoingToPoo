package com.landru.goingtopoo.lib;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.landru.goingtopoo.R;

import java.util.ArrayList;

/**
 * Created by cyrille on 03/03/15.
 */
public class RollingDisplay extends LinearLayout {

    private ArrayList<RollingDigit> digitView;
    private ArrayList<RollingDigit> decimalView;
    private double mainValue;


    public RollingDisplay(Context context) {
        this(context, 3, 2);
    }

    public RollingDisplay(Context context, int size, int decimaleSize) {
        super(context);
        this.mainValue = 0;
        this.setOrientation(HORIZONTAL);
        this.digitView = new ArrayList<RollingDigit>();
        this.decimalView = new ArrayList<RollingDigit>();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        for (int i=0; i<size; i++) {
            RollingDigit element = new RollingDigit(this.getContext());
            element.setLayoutParams(params);
            this.digitView.add(element);
            this.addView(element, 0);
        }
        for (int i=0; i<decimaleSize; i++) {
            RollingDigit element = new RollingDigit(this.getContext(), 0.5);
            element.setLayoutParams(params);
            this.decimalView.add(element);
            this.addView(element);
        }
    }


    public void setValue(double value) {
        for(int i=0; i<digitView.size(); i++) {
            digitView.get(i).setValue(getDigit(value, i));
        }
        for(int i=0; i<decimalView.size(); i++) {
            decimalView.get(i).setValue(getDecimal(value, i));
        }
    }


    private static int getDigit(double data, int pos) {
        int intData = (int)Math.floor(data);
        return (int)Math.floor((intData % Math.pow(10, pos + 1)) / Math.pow(10, pos));
    }

    private static int getDecimal(double data, int pos) {
        int shift = (int)Math.round(Math.pow(10, pos));
        double decimal = data * shift - Math.floor(data * shift);
        return (int)Math.floor(decimal * 10);
    }

}
