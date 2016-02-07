package com.github.hussamsh.musicretriever;

public class AlbumArtColor {

    private int vibrantColor =  -1;
    private int lightVibrantColor = -1 ;
    private int darkVibrantColor = -1 ;
    private int mutedColor = -1 ;
    private int lightMutedColor = -1 ;
    private int darkMutedColor = -1 ;

    public AlbumArtColor() {}

    public AlbumArtColor(int vibrantColor, int lightVibrantColor, int darkVibrantColor,
                         int mutedColor, int lightMutedColor, int darkMutedColor) {
        this.vibrantColor = vibrantColor;
        this.lightVibrantColor = lightVibrantColor;
        this.darkVibrantColor = darkVibrantColor;
        this.mutedColor = mutedColor;
        this.lightMutedColor = lightMutedColor;
        this.darkMutedColor = darkMutedColor;
    }

    public boolean isSet(){
        return !(vibrantColor == -1&& lightVibrantColor == -1 && darkVibrantColor == -1 &&
                mutedColor  == -1 && lightMutedColor == -1 &&  darkMutedColor == -1);
    }

    public int getVibrantColor() {
        return vibrantColor;
    }

    public int getLightVibrantColor() {
        return lightVibrantColor;
    }

    public int getDarkVibrantColor() {
        return darkVibrantColor;
    }

    public int getMutedColor() {
        return mutedColor;
    }

    public int getLightMutedColor() {
        return lightMutedColor;
    }

    public int getDarkMutedColor() {
        return darkMutedColor;
    }

    public void setVibrantColor(int vibrantColor) {
        this.vibrantColor = vibrantColor;
    }

    public void setLightVibrantColor(int lightVibrantColor) {
        this.lightVibrantColor = lightVibrantColor;
    }

    public void setDarkVibrantColor(int darkVibrantColor) {
        this.darkVibrantColor = darkVibrantColor;
    }

    public int getMainColor(){
        return vibrantColor == -1 ? mutedColor : vibrantColor;
    }

    public int getLightColor(){
        return lightVibrantColor == -1 ? lightMutedColor : lightVibrantColor;
    }

    public int getDarkColor(){
        return darkVibrantColor == -1 ? darkMutedColor : darkVibrantColor ;
    }

    public void setMutedColor(int mutedColor) {
        this.mutedColor = mutedColor;
    }

    public void setLightMutedColor(int lightMutedColor) {
        this.lightMutedColor = lightMutedColor;
    }

    public void setDarkMutedColor(int darkMutedColor) {
        this.darkMutedColor = darkMutedColor;
    }

    public void set(AlbumArtColor newColor){
        this.vibrantColor = newColor.vibrantColor;
        this.lightVibrantColor = newColor.lightVibrantColor;
        this.darkMutedColor = newColor.darkVibrantColor;
        this.mutedColor = newColor.mutedColor;
        this.lightMutedColor = newColor.lightMutedColor;
        this.darkMutedColor = newColor.darkMutedColor;
    }
}
