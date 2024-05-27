package com.example.swim_suit_rank;

public class SwimSuitInfo {
    private String _name;
    private String _compression;
    private String _priceRange;
    private String _strokes;
    private String _distances;
    private String _pictureSrc;

    public SwimSuitInfo() {
        _name = "Default";
        _compression = "Default";
        _priceRange = "Default";
        _strokes = "Default";
        _distances = "Default";
        _pictureSrc = "ic_launcher_background";
    }

    public SwimSuitInfo(String name, String comp, String price, String strokes, String dist, String pic_src) {
        _name = name;
        _compression = comp;
        _priceRange = price;
        _strokes = strokes;
        _distances = dist;
        _pictureSrc = pic_src;
    }


    public enum DATA {NAME, COMPRESSION, PRICE_RANGE, STROKES, DISTANCES, PICTURE_SRC}
    public String get(DATA attr) {
        switch (attr) {
            case NAME:
                return _name;
            case COMPRESSION:
                return _compression;
            case PRICE_RANGE:
                return _priceRange;
            case STROKES:
                return _strokes;
            case DISTANCES:
                return _distances;
            case PICTURE_SRC:
                return _pictureSrc;
            default:
                return "Something went wrong";
        }
    }

    public void set(DATA attr, String str) {
        if(str == null || str.length() == 0) return;
        switch (attr) {
            case NAME:
                _name = str;
                break;
            case COMPRESSION:
                _compression = str;
                break;
            case PRICE_RANGE:
                _priceRange = str;
                break;
            case STROKES:
                _strokes = str;
                break;
            case DISTANCES:
                _distances = str;
                break;
            case PICTURE_SRC:
                _pictureSrc = str;
                break;
        }
    }
}
