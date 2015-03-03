package com.landru.goingtopoo.lib;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cyrille on 03/03/15.
 */
public class RollingAnimation {
    private Timer timer;
    private AnimationDrawable source;
    private ImageView image;

    public RollingAnimation(ImageView img, AnimationDrawable source) {
        super();
        this.source = source;
        this.image = img;
    }


    public void launch(final Activity currentActivity, final ArrayList<Integer> numbers) {
        long duration = 0;
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                if (numbers.size()>0) {
                    image.setBackgroundResource(RollingDigit.animatedWheels.get(numbers.remove(0)));
                    RollingAnimation animation = new RollingAnimation(image, (AnimationDrawable) image.getBackground());
                    animation.launch(currentActivity, numbers);
                }
            }
        };
        for (int i=0; i< source.getNumberOfFrames(); i++) {
            duration += source.getDuration(i);
        }
        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (task != null) {
                    currentActivity.runOnUiThread(task);
                }
            }
        }, duration);
        source.start();
    }

}
