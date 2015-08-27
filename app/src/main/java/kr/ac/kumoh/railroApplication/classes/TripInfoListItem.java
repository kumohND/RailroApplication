package kr.ac.kumoh.railroApplication.classes;

/**
 * Created by sj on 2015-07-23.
 */
public class TripInfoListItem {
    int TripImg;
    String TripTitle;
    String TripLocale;
    int mStar;

    public TripInfoListItem(int tripImg, String tripTitle, String tripLocale, int mStar) {
        TripImg = tripImg;
        TripTitle = tripTitle;
        TripLocale = tripLocale;
        this.mStar = mStar;
    }

    public int getTripImg() {
        return TripImg;
    }

    public void setTripImg(int tripImg) {
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
}
