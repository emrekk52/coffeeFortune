package com.emrek.kahvefali;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import okhttp3.internal.Internal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.emrek.kahvefali.ui.main.SplashActivity;
import com.google.android.gms.vision.text.Line;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingScreen extends AppCompatActivity {

    ViewPager2 viewPager2;
    Handler handler = new Handler();
    ConstraintLayout constraintLayout;
    LinearLayout indicator_layout;
    Button materialButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        indicator_layout = findViewById(R.id.indicator_layout);
        materialButton = findViewById(R.id.material_button);
        constraintLayout = findViewById(R.id.onBoardScreen);
        setAnimation(constraintLayout);

        viewPager2 = findViewById(R.id.viewPager2);
        List<OnBoardSlider> onBoardSliders = new ArrayList<>();
        onBoardSliders.add(new OnBoardSlider(R.drawable.image1, "Click on the button at the bottom right while on the main page and choose one of the other two buttons according to your preference."));
        onBoardSliders.add(new OnBoardSlider(R.drawable.image2, "After selecting the picture, you will see the results in the section that opens from the bottom."));
        onBoardSliders.add(new OnBoardSlider(R.drawable.image3, "You can see all the results on the homepage."));

        viewPager2.setAdapter(new OnBoardSliderAdapter(onBoardSliders, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager2.getCurrentItem() + 1 < 3) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(OnBoardingScreen.this, MainActivity.class));
                    finish();
                }
            }
        });

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        setupIndıcator();
        currentIndıcator(0);

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.postDelayed(handlerRunnable, 3000);
                currentIndıcator(position);
            }
        });

    }

    private Runnable handlerRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    private void setAnimation(View view) {
        AnimationDrawable animationDrawable = (AnimationDrawable) view.getBackground();
        animationDrawable.setEnterFadeDuration(900);
        animationDrawable.setExitFadeDuration(900);
        animationDrawable.start();
    }

    private void setupIndıcator() {
        ImageView[] indicators = new ImageView[3];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.indicator_deactive));
            indicators[i].setLayoutParams(layoutParams);
            indicator_layout.addView(indicators[i]);
        }

    }

    private void currentIndıcator(int index) {
        int child = indicator_layout.getChildCount();
        for (int i = 0; i < child; i++) {
            ImageView imageView = (ImageView) indicator_layout.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.indicator_deactive));

            }
        }
        if (index == 2) {
            materialButton.setText("Start");
        } else {
            materialButton.setText("Next");
        }
    }
}
