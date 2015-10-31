package kr.ac.kumoh.railroApplication.classes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by sj on 2015-07-23.
 */
public class TripInfoListItem extends Drawable {
    Bitmap TripImg;
    String TripTitle;
    String TripLocale;
    int mStar;
    public TripInfoListItem(Bitmap tripImg, String tripTitle,
                            String tripLocale, int mStar) {
        TripImg = tripImg;
        TripTitle = tripTitle;
        TripLocale = tripLocale;
        this.mStar = mStar;
    }

    public Bitmap getTripImg() {
        return TripImg;
    }

    public void setTripImg(Bitmap tripImg) {
        TripImg = tripImg;
    }

    public String getTripTitle() {
        return TripTitle;
    }

    public void setTripTitle(String tripTitle) {
        TripTitle = tripTitle;
    }

    public String getTripLocale() {
        return TripLocale;
    }

    public void setTripLocale(String tripLocale) {
        TripLocale = tripLocale;
    }

    public int getmStar() {
        return mStar;
    }

    public void setmStar(int mStar) {
        this.mStar = mStar;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
