package com.example.demo.to;

public class UrlResponseTO {
    private String urlOriginal;
    private String urlShort;

    public UrlResponseTO(String urlOriginal, String urlShort) {
        this.urlOriginal = urlOriginal;
        this.urlShort = urlShort;
    }
    public String getUrlOriginal() {

        return urlOriginal;
    }
    public void setUrlOriginal(String urlOriginal) {

        this.urlOriginal = urlOriginal;
    }
    public String getUrlShort() {

        return urlShort;
    }
    public void setUrlShort(String urlShort) {

        this.urlShort = urlShort;
    }

}
