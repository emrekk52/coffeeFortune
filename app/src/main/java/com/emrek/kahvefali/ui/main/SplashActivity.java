package com.emrek.kahvefali.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emrek.kahvefali.MainActivity;
import com.emrek.kahvefali.OnBoardingScreen;
import com.emrek.kahvefali.R;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    RelativeLayout relativeLayout;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = this.getSharedPreferences("com.emrek.kahvefali",Context.MODE_APPEND);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        relativeLayout = findViewById(R.id.splashRelativeLayout);
        setAnimation(relativeLayout);
        AnimationBounce();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (sharedPreferences.getBoolean("state", false))
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                else
                    intent = new Intent(SplashActivity.this, OnBoardingScreen.class);

                startActivity(intent);
                finish();
            }
        }, 2120);

    }

    private void setAnimation(View view) {
        AnimationDrawable animationDrawable = (AnimationDrawable) view.getBackground();
        animationDrawable.setEnterFadeDuration(1060);
        animationDrawable.setExitFadeDuration(1060);
        animationDrawable.start();
    }

    private void AnimationBounce() {
        TextView splashText = findViewById(R.id.splashText);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        BounceInterpolator bounceInterpolator = new BounceInterpolator(20, 0.2);
        animation.setInterpolator(bounceInterpolator);
        splashText.startAnimation(animation);

    }
}
