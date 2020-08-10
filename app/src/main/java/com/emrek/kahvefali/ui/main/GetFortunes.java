package com.emrek.kahvefali.ui.main;

public class GetFortunes {

    private String fortune_header;
    private String fortune_text;
    private String fortuneImageUrl;
    private String fortune_time;
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFortune_time() {
        return fortune_time;
    }

    public void setFortune_time(String fortune_time) {
        this.fortune_time = fortune_time;
    }

    public String getFortuneImageUrl() {
        return fortuneImageUrl;
    }

    public void setFortuneImageUrl(String fortuneImageUrl) {
        this.fortuneImageUrl = fortuneImageUrl;
    }

    public String getFortune_header() {
        return fortune_header;
    }

    public void setFortune_header(String fortune_header) {
        this.fortune_header = fortune_header;
    }

    public String getFortune_text() {
        return fortune_text;
    }

    public void setFortune_text(String fortune_text) {
        this.fortune_text = fortune_text;
    }


    public GetFortunes(String fortune_header, String fortune_text, String fortuneImageUrl, String fortune_time,String user) {
        this.fortune_header = fortune_header;
        this.fortune_text = fortune_text;
        this.fortuneImageUrl = fortuneImageUrl;
        this.fortune_time = fortune_time;
        this.user = user;

    }

    public GetFortunes(){

    }
}
