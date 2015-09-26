package kr.ac.kumoh.railroApplication.classes;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by WooChan on 2015-09-25.
 */
public class RealTimeLocationListener extends Service implements LocationListener {


    double lat = 0;
    double lon = 0;
    Context mContext;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean isGetLocation = false;
    long minTime = 1000;
    float minDistance = 0;
    Location location;
    public LocationManager mManager;


    public RealTimeLocationListener(Context mContext) {
        this.mContext = mContext;
        GetLocation();

    }

    public double getLatitude(){
        if(location !=null)
        {
            lat = location.getLatitude();

        }
        return lat;
    }

    public double getLongitude(){
        if(location !=null)
        {
            lon = location.getLongitude();

        }
        return lon;
    }

    public boolean isGetLocation(){
        return this.isGetLocation;
    }
    public Location GetLocation()
    {
        try{
            mManager = (LocationManager)mContext.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = mManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled = mManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if(!isGPSEnabled && !isNetworkEnabled)
            {
                //GPS , NETWORK 둘다 불가
                showSettingAlert();
            }


                this.isGetLocation = true;
                //네트워크 정보로부터 위치값 가져오기
                if(isNetworkEnabled){
                    mManager.requestLocationUpdates(mManager.NETWORK_PROVIDER,
                            minTime,minDistance,this);

                    if(mManager !=null)
                    {
                        location = mManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location !=null)
                        {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }

                if(isGPSEnabled)
                {
                    if(location == null){
                        mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                minTime,minDistance,this);
                        if(mManager != null){
                            location = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location!=null)
                            {
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }
                }


        }catch(Exception e)
        {
            e.printStackTrace();
        }



        return location;
    }

    public void showSettingAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("GPS 사용유무셋팅");
        alertDialog.setMessage("GPS 셋팅이 되지 않았습니다 \n 설정창으로 가시겠습니까?");

        alertDialog.setPositiveButton("Setting",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }



    @Override
    public void onLocationChanged(Location location) {
         lat = location.getLatitude();
         lon = location.getLongitude();

        String msg = "Latitude : " + lat + "\nLongtitude:" + lon;
        Toast.makeText(mContext, "onLocationChaged: " + msg, Toast.LENGTH_SHORT);
        Log.i("GPSListener", msg);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        switch(i){
            case LocationProvider.OUT_OF_SERVICE:
                Log.i("GPSListener","OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.i("GPSListener","TEMPORARILY_UNAVAILABLE");
                break;
            case LocationProvider.AVAILABLE :
                Log.i("GPSListener","AVAILABLE");
                break;
        }
    }


    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
