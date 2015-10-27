package kr.ac.kumoh.railroApplication.classes;

import android.content.ContentValues;
import android.os.StrictMode;
import android.text.Html;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Woocha on 2015-08-26.
 */
public class ForeCast extends Thread {
//    ArrayList<ForecastInfo> mCast;
//    ArrayList<ForeXmlData> mWeatherCast;
ArrayList<String> mCastList;
//    ContentValues mContent;

    boolean same_Flag = false;
    //    ForecastInfo mFore_Data;
    String lon ;  String s_Date; String e_Date;
    String lat ;
    String tYear;
    String tMonth;
    String tDay;
    int weather = -1;
    int range_Day = 1;
    LocationInform mStartLocation;
    LocationInform mEndLocation;
    ContentValues start_Weatehr;
    ContentValues end_Weather;

    public ContentValues getStart_Weather()
    {
        return start_Weatehr;
    }
    public ContentValues getEnd_Weather()
    {
        return end_Weather;
    }
    public boolean CheckWeatherRange(String year,String month,String day)
    {

        Calendar check = Calendar.getInstance();
        for(int i = 0; i < 15; i++) {
            String temp_Year = String.valueOf(check.get(Calendar.YEAR));
            String temp_Month = String.valueOf(check.get(Calendar.MONTH)+1);
            String temp_Day = String.valueOf(check.get(Calendar.DAY_OF_MONTH));
            String total = temp_Year + temp_Month + temp_Day;

            if(total.equals(year+month+day)) return true;


            check.add(Calendar.DAY_OF_MONTH , range_Day);
        }

        return false;
    }

    public void getForeCast(String year,String month,String day)
    {
        if(this.mStartLocation == null) return ;

        start_Weatehr = GetOpenWeather(year,month,day,String.valueOf(this.mStartLocation.latlng.getLng()),String.valueOf(this.mStartLocation.latlng.getLat()));
        if(mEndLocation !=null ){
            end_Weather = GetOpenWeather(year,month,day,String.valueOf(this.mEndLocation.latlng.getLng()),String.valueOf(this.mEndLocation.latlng.getLat()));
        }
    }



    public ForeCast(LocationInform mStartLocation, LocationInform mEndLocation)
    {
        this.mStartLocation = mStartLocation;
        this.mEndLocation = mEndLocation;
    }

    public ContentValues NullOpenWeather()
    {
        ContentValues empty = new ContentValues();
        empty.put("weather_Name", "null");

        return empty;
    }

    //TODO : ㅁㄴㅇㄹ
    // API 키 입력해줘야함.
    public ContentValues GetOpenWeather(String year,String month,String day,String lon,String lat)
    {
        if(!CheckWeatherRange(year,month,day)) return NullOpenWeather(); // 존재하는지 먼저 체크,

        ContentValues mContent = new ContentValues();
//        mWeatherCast = new ArrayList<ForeXmlData>();
        String lat1 = "35";
        String lon1 = "138";

        String key = "0dcf7375b443e0a6c6717fe7e956fac7";
        try{
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?"+
                    "&APPID=" + key +
                    "&lat="+lat+
                    "&lon="+lon+
                    "&mode=xml" +
                    "&units=metric"+
                    "&cnt=" + 15
            );

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            // 위에서 생성된 URL을 통하여 서버에 요청하면 결과가 XML Resource로 전달됨

            XmlPullParser parser = factory.newPullParser();
            // XML Resource를 파싱할 parser를 factory로 생성

            parser.setInput(url.openStream(), null);
            // 파서를 통하여 각 요소들의 이벤트성 처리를 반복수행

            int parserEvent = parser.getEventType();
            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                // XML문이 끝날 때 까지 정보를 읽는다
                if (parserEvent == XmlPullParser.START_TAG) {
                    //시작태그의 이름을 알아냄
                    String tagName = parser.getName();
                    if(tagName.equals("time"))
                    {
                        if(Integer.valueOf(day) < 10){
                            day = "0"+day;
                        }
                        if(parser.getAttributeValue(null,"day").equals(year+"-"+month+"-"+day)) same_Flag = true;
                    }
                    if(same_Flag == true) {
                        switch (tagName) {
                            case "time": // 날짜

                                mContent.put("day", parser.getAttributeValue(null, "day"));
                                break;
                            case "symbol": // 해당 날씨
                                mContent.put("weather_Name", parser.getAttributeValue(null, "name"));
                                mContent.put("weather_Number", parser.getAttributeValue(null, "number"));
                                //mContent.put("weather_Much",parser.getAttributeValue(null,"var"));
                                break;
                            case "precipitation": //예측
                                mContent.put("weather_Much", parser.getAttributeValue(null, "value"));
                                mContent.put("weather_Type", parser.getAttributeValue(null, "type"));
                                break;
                            case "windDirection": // 바람 방향
                                mContent.put("wind_Direction", parser.getAttributeValue(null, "name"));
                                mContent.put("wind_SortNumber", parser.getAttributeValue(null, "deg"));
                                mContent.put("wind_SortCode", parser.getAttributeValue(null, "code"));
                                break;
                            case "windSpeed": // 바람 세기
                                mContent.put("wind_Speed", parser.getAttributeValue(null, "mps"));
                                mContent.put("wind_Name", parser.getAttributeValue(null, "name"));
                                break;
                            case "temperature": // 온도
                                mContent.put("temp_Min", parser.getAttributeValue(null, "min"));
                                mContent.put("temp_Max", parser.getAttributeValue(null, "max"));
                                break;
                            case "humidity": // 습도
                                mContent.put("humidity", parser.getAttributeValue(null, "value"));
                                mContent.put("humidity_unit", parser.getAttributeValue(null, "unit"));
                                break;
                            case "clouds": // 구름종류
                                mContent.put("Clouds_Sort", parser.getAttributeValue(null, "value"));
                                mContent.put("Clouds_Value", parser.getAttributeValue(null, "all"));
                                mContent.put("Clouds_Per", parser.getAttributeValue(null, "unit"));
                                same_Flag = false;
                                return mContent;

                        }
                    }
                }
                parserEvent = parser.next();
            }//asdasd
            //TODO : 12312321
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mContent;
    }



    @Override
    public void run() {
        Calendar mCal = Calendar.getInstance();
        tYear = String.valueOf(mCal.get(Calendar.YEAR));
        tMonth = String.valueOf(mCal.get(Calendar.MONTH)+1);
        tDay = String.valueOf(mCal.get(Calendar.DAY_OF_MONTH));
        super.run();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getForeCast(tYear, tMonth, tDay);
    }




}
