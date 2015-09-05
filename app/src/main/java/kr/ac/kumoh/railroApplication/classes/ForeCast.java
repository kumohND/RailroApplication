package kr.ac.kumoh.railroApplication.classes;

import android.content.ContentValues;
import android.os.StrictMode;
import android.text.Html;

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
    ContentValues mContent;

    ForecastInfo mFore_Data;
    String lon ="35";
    String lat ="139";
    int tYear;
    int tMonth;
    int tDay;
    int weather = -1;
    int range_Day = 0;
    public boolean CheckWeatherRange(String year,String month,String day)
    {
        range_Day = 1;
        Calendar check = Calendar.getInstance();
        for(range_Day = 1; range_Day <15; range_Day++) {
            check.add(Calendar.DATE , range_Day);
            String tYear = String.valueOf(check.YEAR);
            String tMonth = String.valueOf(check.MONTH+1);
            String tDay = String.valueOf(check.DATE);
            String total = tYear + tMonth + tDay;

            if(total.equals(year+month+day)) return true;
        }

        return false;
    }

    public void LonAndLatObtain(String SearchCity)
    {

    }
    String t_date1;
    String t_date2;


    public ContentValues GetOpenWeather(String year,String month,String day,String searchCity)
    {
        if(!CheckWeatherRange(year,month,day)) return null;

        mContent = new ContentValues();
        LonAndLatObtain(searchCity);
//        mWeatherCast = new ArrayList<ForeXmlData>();
        String lat1 = "35";
        String lon1 = "138";

        try{
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?" +
                    "&lat="+lat1+
                    "&lon="+lon1+
                    "&mode=xml" +
                    "&units=metric"+
                    "&cnt=" + 10
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
                    switch(tagName)
                    {
                        case "time": // 날짜
                            mContent.put("day",parser.getAttributeValue(null,"day"));
                            break;
                        case "symbol": // 해당 날씨
                            mContent.put("weather_Name",parser.getAttributeValue(null,"name"));
                            mContent.put("weather_Number",parser.getAttributeValue(null,"number"));
                            //mContent.put("weather_Much",parser.getAttributeValue(null,"var"));
                            break;
                        case "precipitation": //예측
                            mContent.put("weather_Much",parser.getAttributeValue(null,"value"));
                            mContent.put("weather_Type",parser.getAttributeValue(null,"type"));
                            break;
                        case "windDirection": // 바람 방향
                            mContent.put("wind_Drection",parser.getAttributeValue(null,"name"));
                            mContent.put("wind_SortNumber",parser.getAttributeValue(null,"deg"));
                            mContent.put("wind_SortCode",parser.getAttributeValue(null,"code"));
                            break;
                        case "windSpeed": // 바람 세기
                            mContent.put("wind_Speed",parser.getAttributeValue(null,"mps"));
                            mContent.put("wind_Name",parser.getAttributeValue(null,"name"));
                            break;
                        case "temperature": // 온도
                            mContent.put("temp_Min",parser.getAttributeValue(null,"min"));
                            mContent.put("temp_Max",parser.getAttributeValue(null,"max"));
                            break;
                        case "humidity": // 습도
                            mContent.put("humidity_",parser.getAttributeValue(null,"value"));
                            mContent.put("humidity_unit",parser.getAttributeValue(null,"unit"));
                            break;
                        case "clouds ": // 구름종류
                            mContent.put("Clouds_Value",parser.getAttributeValue(null,"value"));
                            mContent.put("Clouds_Sort",parser.getAttributeValue(null,"all"));
                            mContent.put("Clouds_Per",parser.getAttributeValue(null,"unit"));
                            break;
                    }
                }
                parserEvent = parser.next();
            }//asdasd
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mContent;
    }


    public class ForecastInfo {
        String Category;
        String ObsrValue;

        public String getCategory() {
            return Category;
        }

        public void setCategory(String category) {
            this.Category = category;
        }

        public String getObsrValue() {
            return ObsrValue;
        }

        public void setObsrValue(String obsrValue) {
            ObsrValue = obsrValue;
        }
    }

    public ForeCast(int tYear, int tMonth, int tDay) {
//        mCast = new ArrayList<ForecastInfo>();
        this.tYear = tYear;
        this.tMonth = tMonth + 1;
        this.tDay = tDay;
        run();
    }
    public ForeCast() {
        run();
    }
    @Override
    public void run() {
        super.run();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        GetOpenWeather("2015","08","27","seoul");
    }




}
