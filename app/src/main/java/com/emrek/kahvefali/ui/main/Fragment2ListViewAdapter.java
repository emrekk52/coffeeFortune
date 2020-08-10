package com.emrek.kahvefali.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emrek.kahvefali.MainActivity;
import com.emrek.kahvefali.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

public class Fragment2ListViewAdapter extends BaseAdapter {

    public static String url;

    private ArrayList<GetFortunes> getFortunesArrayList;
    private Context context;
    private LayoutInflater layoutInflater;
    RoundedImageView roundedImageView;
    AdapterView.OnItemClickListener onItemClickListener;


    @Override
    public int getCount() {
        return getFortunesArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return getFortunesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final View customView = layoutInflater.inflate(R.layout.cardview_layout_design, null);
        TextView otherHeader = customView.findViewById(R.id.otherHeader);
        TextView otherText = customView.findViewById(R.id.otherText);
        CardView cardView = customView.findViewById(R.id.cardView);
        TextView cardlastupload = customView.findViewById(R.id.cardlastupload);
        TextView time = customView.findViewById(R.id.cardViewTime);
        View cardInView = customView.findViewById(R.id.cardInView);
        View edgeView = customView.findViewById(R.id.edgeView);
        TextView user = customView.findViewById(R.id.cardViewName);


        roundedImageView = customView.findViewById(R.id.cardViewImage);
        user.setText(getFortunesArrayList.get(position).getUser());
        otherHeader.setText(getFortunesArrayList.get(position).getFortune_header());
        otherText.setText(getFortunesArrayList.get(position).getFortune_text());
        time.setText(getFortunesArrayList.get(position).getFortune_time());
        Picasso.get().load(getFortunesArrayList.get(position).getFortuneImageUrl()).into(roundedImageView);

        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(null, customView, position, -1);
                    url=getFortunesArrayList.get(position).getFortuneImageUrl();
                }
            }
        });

        if (position + 1 == getFortunesArrayList.size() && MainActivity.isClick == true) {
            cardInView.setVisibility(View.VISIBLE);
            cardlastupload.setVisibility(View.VISIBLE);
            cardView.getBackground().setTint(Color.WHITE);
            otherHeader.setTextColor(Color.BLACK);
            otherText.setTextColor(Color.GRAY);
            time.setTextColor(Color.GRAY);

        }

        AnimationDrawable animationDrawable= (AnimationDrawable) edgeView.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);
        animationDrawable.start();

        return customView;
    }

    public Fragment2ListViewAdapter(ArrayList<GetFortunes> getFortunesArrayList, Context context) {
        this.getFortunesArrayList = getFortunesArrayList;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}

