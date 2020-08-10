package com.emrek.kahvefali;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class OnBoardSliderAdapter extends RecyclerView.Adapter<OnBoardSliderAdapter.OnBoardSliderViewHolder> {

    private List<OnBoardSlider> onBoardSliderList;
    private ViewPager2 viewPager2;

    OnBoardSliderAdapter(List<OnBoardSlider> onBoardSliderList, ViewPager2 viewPager2) {
        this.onBoardSliderList = onBoardSliderList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public OnBoardSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnBoardSliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.onboard_design, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnBoardSliderViewHolder holder, int position) {
        holder.setImage(onBoardSliderList.get(position));

    }

    @Override
    public int getItemCount() {
        return onBoardSliderList.size();
    }

    class OnBoardSliderViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView roundedImageView;
        private TextView onBoardText;

        OnBoardSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageView = itemView.findViewById(R.id.onboard_image);
            onBoardText = itemView.findViewById(R.id.onboardText);
        }

        void setImage(OnBoardSlider onBoardSlider) {
            roundedImageView.setImageResource(onBoardSlider.getImage());
            onBoardText.setText(onBoardSlider.getOnBoardText());
        }

    }
}
