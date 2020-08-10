package com.emrek.kahvefali.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.os.CountDownTimer;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.airbnb.lottie.LottieAnimationView;
import com.emrek.kahvefali.MainActivity;
import com.emrek.kahvefali.R;
import com.emrek.kahvefali.ScrollingActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Fragment1 extends Fragment {

    Fragment1ListViewAdapter fragment1ListViewAdapter;
    ListView listView;
    LottieAnimationView lottieAnimationView;
    SwipeRefreshLayout swipeRefreshLayout;

    public static TextView otherText, otherHeader, otherTime, user;
    public static RoundedImageView roundedImageView;

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.isInternetConnection())
            tekrarla();
    }

    public void tekrarla() {

        final Handler handler = new Handler();
        Timer timer;


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (listView.getCount() > 0)
                            lottieAnimationView.setVisibility(View.INVISIBLE);
                        else
                            fragment1ListViewAdapter.notifyDataSetChanged();

                        if (listView.getCount() < MainActivity.userArrayList.size())
                            lottieAnimationView.setVisibility(View.VISIBLE);
                        else
                            lottieAnimationView.setVisibility(View.INVISIBLE);


                    }
                });
            }
        };

        timer = new Timer();

        timer.schedule(timerTask, 1000, 1000);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v;
        if (!MainActivity.isInternetConnection())
            v = inflater.inflate(R.layout.offline_fragment, container, false);
        else {
            v = inflater.inflate(R.layout.fragment_1, container, false);

            fragment1ListViewAdapter = new Fragment1ListViewAdapter(MainActivity.userArrayList, getActivity());

            listView = v.findViewById(R.id.listView);
            lottieAnimationView = v.findViewById(R.id.lottieFrag1);
            lottieAnimationView.setVisibility(View.VISIBLE);


            fragment1ListViewAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    otherText = view.findViewById(R.id.otherText);
                    otherHeader = view.findViewById(R.id.otherHeader);
                    otherTime = view.findViewById(R.id.cardViewTime);
                    user = view.findViewById(R.id.cardViewName);
                    roundedImageView = view.findViewById(R.id.cardViewImage);
                    Intent intent = new Intent(getActivity(), ScrollingActivity.class);

                    androidx.core.util.Pair<View, String> pair1 = Pair.create(view.findViewById(R.id.cardViewImage), "scrollimage");
                    androidx.core.util.Pair<View, String> pair2 = Pair.create(view.findViewById(R.id.otherHeader), "scrollheader");
                    androidx.core.util.Pair<View, String> pair3 = Pair.create(view.findViewById(R.id.otherText), "scrolltext");
                    androidx.core.util.Pair<View, String> pair4 = Pair.create(view.findViewById(R.id.cardViewTime), "scrolltext");
                    androidx.core.util.Pair<View, String> pair5 = Pair.create(view.findViewById(R.id.cardViewName), "scrolltext");


                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pair1, pair2, pair3, pair4, pair5);
                    intent.putExtra("fragment", 1);
                    startActivity(intent, optionsCompat.toBundle());

                    System.out.println(otherText.getText().toString());
                }
            });
            listView.setAdapter(fragment1ListViewAdapter);

            swipeRefreshLayout = v.findViewById(R.id.fragRefreshLayout);
            setupRefreshLayout();
            tekrarla();
        }


        return v;
    }

    private void setupRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onRefresh() {

                lottieAnimationView.setVisibility(View.VISIBLE);
                MainActivity.downloadFortunes();

                fragment1ListViewAdapter.notifyDataSetChanged();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        lottieAnimationView.setVisibility(View.INVISIBLE);
                    }
                }, 4000);
            }
        });
        swipeRefreshLayout.setColorScheme(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light,
                android.R.color.holo_green_light);

    }


}
