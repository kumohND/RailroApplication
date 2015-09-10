package kr.ac.kumoh.railroApplication.classes;

/**
 * Created by Woocha on 2015-09-07.
 */
public class WeatherInfo {
    String weather_Name;
    String weather_Number;
    String weather_Much;
    String weather_Type;
    String wind_Direction;
    String wind_SortNumber;
    String wind_SortCode;
    String wind_Speed;
    String wind_Name;
    String temp_Min;
    String temp_Max;
    String humidity;
    String clouds_Value;
    String clouds_Sort;
    String clouds_Per;
    int picture_Category;
    int picture_ID;

    public int getPicture_ID() {
        return picture_ID;
    }

    public void setPicture_ID(int picture_ID) {
        this.picture_ID = picture_ID;
    }

    public int getPicture_Category() {
        return picture_Category;
    }

    public void setPicture_Category(int picture_Category) {
        this.picture_Category = picture_Category;
    }

    public WeatherInfo(String weather_Name, String weather_Number, String weather_Much,
                       String weather_Type, String wind_Direction, String wind_SortNumber,
                       String wind_SortCode, String wind_Speed, String wind_Name,
                       String temp_Min, String temp_Max, String humidity,
                       String clouds_Value, String clouds_Sort, String clouds_Per) {
        this.weather_Name = weather_Name;
        this.weather_Number = weather_Number;
        this.weather_Much = weather_Much;
        this.weather_Type = weather_Type;
        this.wind_Direction = wind_Direction;
        this.wind_SortNumber = wind_SortNumber;
        this.wind_SortCode = wind_SortCode;
        this.wind_Speed = wind_Speed;
        this.wind_Name = wind_Name;
        this.temp_Min = temp_Min;
        this.temp_Max = temp_Max;
        this.humidity = humidity;
        this.clouds_Value = clouds_Value;
        this.clouds_Sort = clouds_Sort;
        this.clouds_Per = clouds_Per;
    }

    public String getWeather_Name() {
        return weather_Name;
    }

    public String getWeather_Number() {
        return weather_Number;
    }

    public String getWeather_Much() {
        return weather_Much;
    }

    public String getWeather_Type() {
        return weather_Type;
    }

    public String getWind_Direction() {
        return wind_Direction;
    }

    public String getWind_SortNumber() {
        return wind_SortNumber;
    }

    public String getWind_SortCode() {
        return wind_SortCode;
    }

    public String getWind_Speed() {
        return wind_Speed;
    }

    public String getWind_Name() {
        return wind_Name;
    }

    public String getTemp_Min() {
        return temp_Min;
    }

    public String getTemp_Max() {
        return temp_Max;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getClouds_Value() {
        return clouds_Value;
    }

    public String getClouds_Sort() {
        return clouds_Sort;
    }

    public String getClouds_Per() {
        return clouds_Per;
    }
}
