package com.amosayomide.radio;

public class Stream {
    private final String name;
    private final String url;
    private final int logoResId;

    public Stream(String name, String url, int logoResId) {
        this.name = name;
        this.url = url;
        this.logoResId = logoResId;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getLogoResId() {
        return logoResId;
    }
}
