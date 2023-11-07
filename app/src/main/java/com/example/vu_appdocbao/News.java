package com.example.vu_appdocbao;

public class News {
    private String title;
    private String img;
    private String time;

    public News(String title, String img, String time) {
        this.title = title;
        this.img = img;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
