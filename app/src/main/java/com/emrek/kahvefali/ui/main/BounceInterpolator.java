package com.emrek.kahvefali.ui.main;

import android.view.animation.Interpolator;

public class BounceInterpolator implements Interpolator {


    double frequence=10,amplitude=1;

    public BounceInterpolator(double frequence, double amplitude) {
        this.frequence = frequence;
        this.amplitude = amplitude;
    }

    @Override
    public float getInterpolation(float input) {
        return (float)(-1*Math.pow(Math.E,-input/amplitude)*Math.cos(frequence*input)+1);
    }
}
