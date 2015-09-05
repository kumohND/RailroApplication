package kr.ac.kumoh.railroApplication.classes;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by Woocha on 2015-09-05.
 */
public class LocationChangeLonLat {
    String mStartLoc;
    String mEndLoc;

    LocationInform mStartInform;
    LocationInform mEndInform;

    Geocoder mGeocoder;
    List<Address> mListAddress;
    Address mAddress;

    double mDistance;

    Context mContext;

    String realDistance;
    String makeTime;

    public LocationInform getStartLocationLonLat()
    {
        return mStartInform;
    }

    public LocationInform getEndLocationLonLat()
    {
        return mEndInform;
    }

    public void initControl(String sAddress,String eAddress,Context mCon)
    {
        mContext = mCon;
        mStartLoc = sAddress;
        mEndLoc = eAddress;

        if(!mStartLoc.equals(""))
        {
            mStartInform = new LocationInform();
            mStartInform = searchLocation(mStartLoc);
        }
        if(!mEndLoc.equals(""))
        {
            mEndInform = new LocationInform();
            mEndInform = searchLocation(mEndLoc);
        }

    }

//    public String Calculator()
//    {
//        mDistance = getDistance();
//        if((mDistance/1000) != 0)
//        {
//            mDistance /= 1000;
//        }
//
//        if(mStartInform != null && mEndInform != null){
//           realDistance = SendByHttp(mStartInform.latlng, mEndInform.latlng);
//          realDistance = getParseString(realDistance);
//           return makeTime;
//        }
//
//        return "";
//    }




    public LocationInform searchLocation(String location){
        LocationInform nLocInform = new LocationInform();

        mGeocoder = new Geocoder(mContext);
        try {
            mListAddress = mGeocoder.getFromLocationName(location, 5);
            if(mListAddress.size()>0){//주소값이 존재하면
                mAddress = mListAddress.get(0);//0번째가 주소값
                nLocInform.latlng = new LatLng(mAddress.getLatitude(), mAddress.getLongitude());

                nLocInform.location = mAddress.getAddressLine(0);
            }
            else
                Toast.makeText(mContext, "위치 검색 실패", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return nLocInform;
    }

    public double getDistance()
    {
        double distance = 0;
        if(mStartInform != null && mEndInform != null){
            Location locStart = new Location("Start");
            Location locEnd = new Location("End");

            locStart.setLatitude(mStartInform.latlng.lat);
            locStart.setLongitude(mStartInform.latlng.lng);

            locEnd.setLatitude(mEndInform.latlng.lat);
            locEnd.setLongitude(mEndInform.latlng.lng);

            distance = locStart.distanceTo(locEnd);
        }


        return distance;
    }

    public String cutTail(double number, int n){//소수점  n자리 밑으로 자르기
        String cuttedNumber = Double.toString(number);
        int idxPoint = cuttedNumber.indexOf(".");
        cuttedNumber = cuttedNumber.substring(0, idxPoint + n);

        return cuttedNumber;
    }

    public String getUrl(LatLng start, LatLng end){
        StringBuilder urlString = new StringBuilder();

        urlString.append("https://www.google.com:443/maps/dir/");

        int n = 6;

        //from
        //urlString.append("&saddr=");
        urlString.append(cutTail(start.lat, n));
        urlString.append(",");
        urlString.append(cutTail(start.lng, n));

        //to
        urlString.append("/");
        urlString.append(cutTail(end.lat, n));
        urlString.append(",");
        urlString.append(cutTail(end.lng, n));

        urlString.append("?hl=en&amp;nogmmr=1");

        urlString.append("&ie=UTF8&0&om=0&output=dragdir");

        return urlString.toString();
    }

    public String SendByHttp(LatLng start, LatLng end)
    {
        String strURL = getUrl(start, end);
        String buf;
        String result = "";
        try {
            URL url = new URL(strURL);
            URLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setDoInput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream(), "UTF-8"));

            while((buf = br.readLine()) != null){
                result += buf;
                System.out.println(buf);
            }

            br.close();


        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public String reverseString(String s) {
        return ( new StringBuffer(s) ).reverse().toString();
    }

//    public String getParseString(String str){
//        int idx = str.indexOf("traffic");
//        str = str.substring(idx, str.length());
//        idx = str.indexOf("[");
//        str = str.substring(idx, str.length());
//        idx = str.indexOf("]");
//        String tmpStr = str.substring(idx+1, str.length());
//        idx += tmpStr.indexOf("]");
//        str = str.substring(0, idx+2);
//        idx = str.indexOf(",");
//        str = str.substring(idx+1, str.length());
//
//        idx = str.indexOf(",");
//        tmpStr = str.substring(idx+2);
//        idx = tmpStr.indexOf(",");
//        tmpStr = tmpStr.substring(0, idx-1);// 거리
//
//        idx = str.lastIndexOf(",");
//        str = str.substring(idx+2, str.length()-2);// 시간
//
//        str = "실제 경로 거리 = " + tmpStr + "," + "\n대중 교통시 경과 시간 = " + str;
//
//        makeTime = str;
//        return tmpStr;
//    }
//

}
