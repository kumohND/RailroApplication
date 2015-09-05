package kr.ac.kumoh.railroApplication.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Woocha on 2015-09-05.
 */
public class LocationInform implements Parcelable {
    public LatLng latlng;
    String location;

    public LocationInform() {

    }

    public LocationInform(Parcel source) {
        // TODO Auto-generated constructor stub
//		this.location = source.readString();
//		this.latlng.lat = source.readDouble();
//		this.latlng.lng = source.readDouble();
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub

    }

    public static final Parcelable.Creator<LocationInform> CREATOR = new Creator<LocationInform>() {

        @Override
        public LocationInform createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new LocationInform(source);
        }

        @Override
        public LocationInform[] newArray(int size) {
            // TODO Auto-generated method stub
            return new LocationInform[size];
        }

    };
}
