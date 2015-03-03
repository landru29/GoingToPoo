package com.landru.goingtopoo.component;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import com.landru.goingtopoo.R;


import java.util.ArrayList;

/**
 * Created by cyrille on 03/03/15.
 */
public class RollingDigit extends ImageView {

    public static ArrayList<Integer> wheels;
    public static ArrayList<Integer> animatedWheels;
    private double scale;
    private ArrayList<Integer> animationList;
    private int value;

    /**
     * Constructor
     * @param context
     * @param attrs
     * @param defStyle
     */
    public RollingDigit(Context context, AttributeSet attrs, int defStyle)  {
        super(context, attrs, defStyle);
        generateWheel();
        animationList = new ArrayList<Integer>();
        this.setBackgroundResource(wheels.get(0));

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RollingDigit, 0, 0);
        this.scale = a.getFloat(R.styleable.RollingDigit_scale, 1);
        a.recycle();
    }

    /**
     * Constructor
     * @param context
     */
    public RollingDigit(Context context) {
        this(context, null);
    }

    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public RollingDigit(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Set the value on one digit
     * @param value
     */
    public void setValue(int value) {
        if (value != this.value) {
            chainAnimation(this.value, value);
        }
        this.value = value;
    }

    /**
     * Generate input data
     */
    private void generateWheel() {
        if (null == wheels) {
            wheels = new ArrayList<Integer>();
            wheels.add(R.drawable.wheel_0_0);
            wheels.add(R.drawable.wheel_1_0);
            wheels.add(R.drawable.wheel_2_0);
            wheels.add(R.drawable.wheel_3_0);
            wheels.add(R.drawable.wheel_4_0);
            wheels.add(R.drawable.wheel_5_0);
            wheels.add(R.drawable.wheel_6_0);
            wheels.add(R.drawable.wheel_7_0);
            wheels.add(R.drawable.wheel_8_0);
            wheels.add(R.drawable.wheel_9_0);
        }

        if (null == animatedWheels) {
            animatedWheels = new ArrayList<Integer>();
            animatedWheels.add(R.anim.zero);
            animatedWheels.add(R.anim.one);
            animatedWheels.add(R.anim.two);
            animatedWheels.add(R.anim.three);
            animatedWheels.add(R.anim.four);
            animatedWheels.add(R.anim.five);
            animatedWheels.add(R.anim.six);
            animatedWheels.add(R.anim.seven);
            animatedWheels.add(R.anim.eight);
            animatedWheels.add(R.anim.nine);
        }
    }

    /**
     * Chain and launch animations
     * @param from number from (one digit) - inclusive
     * @param to   number to (one digit) - inclusive
     */
    public void chainAnimation(int from, int to) {
        int thisFrom = from;
        if (animationList.size() != 0) {
            thisFrom = animationList.get(0);
            animationList.clear();
        }
        for(int i = thisFrom + 1; i <= to; i++) {
            animationList.add(i);
        }
        if (thisFrom>to) {
            for(int i=thisFrom + 1; i<= 9; i++) {
                animationList.add(i);
            }
            for(int i=0; i<= to; i++) {
                animationList.add(i);
            }
        }
        if (animationList.size()>0) {
            this.setBackgroundResource(animatedWheels.get(animationList.remove(0)));
            RollingAnimation animation = new RollingAnimation(this, (AnimationDrawable) this.getBackground());
            animation.launch((Activity) this.getContext(), animationList);
        }
    }

    /**
     * Redim element
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = (int)Math.round((double)getContext().getResources().getDrawable(R.drawable.wheel_0_0).getIntrinsicHeight() * scale);
        int width = (int)Math.round((double)getContext().getResources().getDrawable(R.drawable.wheel_0_0).getIntrinsicWidth() * scale);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
        getLayoutParams().height = height;
        getLayoutParams().width = width;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
