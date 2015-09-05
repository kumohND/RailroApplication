package kr.ac.kumoh.railroApplication.classes;

/**
 * Created by Woocha on 2015-09-05.
 */
public class LatLng {
    double lat;
    double lng;

    public LatLng(double nLat, double nLng)
    {
        lat = nLat;
        lng = nLng;
    }

    public double getLat() {
        return lat;
    }



    public double getLng() {
        return lng;
    }


}
