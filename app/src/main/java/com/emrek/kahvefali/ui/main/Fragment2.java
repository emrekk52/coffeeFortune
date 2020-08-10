package com.emrek.kahvefali.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.emrek.kahvefali.MainActivity;
import com.emrek.kahvefali.R;
import com.emrek.kahvefali.ScrollingActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class Fragment2 extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    Fragment2ListViewAdapter fragment2ListViewAdapter;
    ListView listView;
    LottieAnimationView lottieAnimationView;

    public static TextView otherText, otherHeader, otherTime, user;
    public static RoundedImageView roundedImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        if (!MainActivity.isInternetConnection())
            v = inflater.inflate(R.layout.offline_fragment, container, false);
        else {
            v = inflater.inflate(R.layout.fragment_2, container, false);
            fragment2ListViewAdapter = new Fragment2ListViewAdapter(MainActivity.otherArrayList, getActivity());
            listView = v.findViewById(R.id.listView);
            lottieAnimationView = v.findViewById(R.id.lottieFrag2);
            lottieAnimationView.setVisibility(View.VISIBLE);


            fragment2ListViewAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    otherText = view.findViewById(R.id.otherText);
                    otherHeader = view.findViewById(R.id.otherHeader);
                    otherTime = view.findViewById(R.id.cardViewTime);
                    user = view.findViewById(R.id.cardViewName);

                    Intent intent = new Intent(getActivity(), ScrollingActivity.class);

                    androidx.core.util.Pair<View, String> pair1 = Pair.create(view.findViewById(R.id.cardViewImage), "scrollimage");
                    androidx.core.util.Pair<View, String> pair2 = Pair.create(view.findViewById(R.id.otherHeader), "scrollheader");
                    androidx.core.util.Pair<View, String> pair3 = Pair.create(view.findViewById(R.id.otherText), "scrolltext");
                    androidx.core.util.Pair<View, String> pair4 = Pair.create(view.findViewById(R.id.cardViewTime), "scrolltext");
                    androidx.core.util.Pair<View, String> pair5 = Pair.create(view.findViewById(R.id.cardViewName), "scrolltext");


                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pair1, pair2, pair3, pair4, pair5);
                    intent.putExtra("fragment", 2);
                    startActivity(intent, optionsCompat.toBundle());

                    System.out.println(otherText.getText().toString());
                }
            });

            listView.setAdapter(fragment2ListViewAdapter);

            swipeRefreshLayout = v.findViewById(R.id.refreshLayout);
            setupRefreshLayout();
            tekrarla();
        }


        return v;

    }

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
                            fragment2ListViewAdapter.notifyDataSetChanged();

                        if (listView.getCount() < MainActivity.otherArrayList.size())
                            lottieAnimationView.setVisibility(View.VISIBLE);
                        else
                            lottieAnimationView.setVisibility(View.INVISIBLE);


                    }
                });
            }
        };

        timer = new Timer();

        timer.schedule(timerTask, 1000, 5000);

    }

    private void setupRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onRefresh() {

                lottieAnimationView.setVisibility(View.VISIBLE);
                MainActivity.downloadFortunes();
                fragment2ListViewAdapter.notifyDataSetChanged();


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
