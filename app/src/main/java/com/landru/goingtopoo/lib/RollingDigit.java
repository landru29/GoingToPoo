package com.landru.goingtopoo.lib;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
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

    public RollingDigit(Context context, double scale) {
        super(context);
        generateWheel();
        animationList = new ArrayList<Integer>();
        this.setBackgroundResource(wheels.get(0));
        this.scale = scale;
    }

    public RollingDigit(Context context) {
        this(context, 1);
    }

    public void setValue(int value) {
        if (value != this.value) {
            //this.setImageResource(wheels.get(value));
            chainAnimation(this.value, value);
        }
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

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
        this.setBackgroundResource(animatedWheels.get(animationList.remove(0)));
        RollingAnimation animation = new RollingAnimation(this, (AnimationDrawable)this.getBackground());
        animation.launch((Activity) this.getContext(), animationList);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = (int)Math.round((double)getContext().getResources().getDrawable(R.drawable.wheel_0_0).getIntrinsicHeight() * scale);
        int width = (int)Math.round((double)getContext().getResources().getDrawable(R.drawable.wheel_0_0).getIntrinsicWidth() * scale);
        Log.i("Height !", "" + height);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
        getLayoutParams().height = height;
        getLayoutParams().width = width;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
