package com.emrek.kahvefali;


public class OnBoardSlider {

    private int image;
    private String onBoardText;

    public String getOnBoardText() {
        return onBoardText;
    }

    public OnBoardSlider(int image, String onBoardText) {
        this.image = image;
        this.onBoardText = onBoardText;
    }

    public int getImage() {
        return image;
    }
}
