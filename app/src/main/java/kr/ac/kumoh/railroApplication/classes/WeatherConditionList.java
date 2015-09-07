package kr.ac.kumoh.railroApplication.classes;

import java.util.ArrayList;

/**
 * Created by Woocha on 2015-09-07.
 */
public class WeatherConditionList {
    ArrayList<WeatherCondition> mList;

    public WeatherConditionList() {
        mList = new ArrayList<WeatherCondition>();
        //-------------ThunderStrom------------------//
        mList.add(new WeatherCondition("200","thunderstorm with light rain"));
        mList.add(new WeatherCondition("201","thunderstorm with rain"));
        mList.add(new WeatherCondition("202","thunderstorm with heavy rain"));
        mList.add(new WeatherCondition("210","light thunderstorm"));
        mList.add(new WeatherCondition("211","thunderstorm"));
        mList.add(new WeatherCondition("212","heavy thunderstorm"));
        mList.add(new WeatherCondition("221","ragged thunderstorm"));
        mList.add(new WeatherCondition("230","thunderstorm with light drizzle"));
        mList.add(new WeatherCondition("231","thunderstorm with drizzle"));
        mList.add(new WeatherCondition("232","thunderstorm with heavy drizzle"));
        //------------Drizzle-------------------//
        mList.add(new WeatherCondition("300","light intensity drizzle"));
        mList.add(new WeatherCondition("301","drizzle"));
        mList.add(new WeatherCondition("302","heavy intensity drizzle"));
        mList.add(new WeatherCondition("310","light intensity drizzle rain"));
        mList.add(new WeatherCondition("311","drizzle rain"));
        mList.add(new WeatherCondition("312","heavy intensity drizzle rain"));
        mList.add(new WeatherCondition("313","shower rain and drizzle"));
        mList.add(new WeatherCondition("314","heavy shower rain and drizzle"));
        mList.add(new WeatherCondition("321","shower drizzle"));

       //------------Rain----------------------//
        mList.add(new WeatherCondition("500","light rain"));
        mList.add(new WeatherCondition("501","moderate rain"));
        mList.add(new WeatherCondition("502","heavy intensity rain"));
        mList.add(new WeatherCondition("503","very heavy rain"));
        mList.add(new WeatherCondition("504","extreme rain"));
        mList.add(new WeatherCondition("511","freezing rain"));
        mList.add(new WeatherCondition("520","light intensity shower rain"));
        mList.add(new WeatherCondition("521","shower rain"));
        mList.add(new WeatherCondition("522","heavy intensity shower rain"));
        mList.add(new WeatherCondition("531","ragged shower rain"));

        //------------Snow----------------------//
        mList.add(new WeatherCondition("600","light snow"));
        mList.add(new WeatherCondition("601","snow"));
        mList.add(new WeatherCondition("602","heavy snow"));
        mList.add(new WeatherCondition("611","sleet"));
        mList.add(new WeatherCondition("612","shower sleet"));
        mList.add(new WeatherCondition("615","light rain and snow"));
        mList.add(new WeatherCondition("616","rain and snow"));
        mList.add(new WeatherCondition("620","light shower snow"));
        mList.add(new WeatherCondition("621","shower snow"));
        mList.add(new WeatherCondition("622","heavy shower snow"));

        //------------Atmosphere----------------------//
        mList.add(new WeatherCondition("701","mist"));
        mList.add(new WeatherCondition("711","smoke"));
        mList.add(new WeatherCondition("721","haze"));
        mList.add(new WeatherCondition("731","sand, dust whirls"));
        mList.add(new WeatherCondition("741","fog"));
        mList.add(new WeatherCondition("751","sand"));
        mList.add(new WeatherCondition("761","dust"));
        mList.add(new WeatherCondition("762","volcanic ash"));
        mList.add(new WeatherCondition("771","squalls"));
        mList.add(new WeatherCondition("781","tornado"));

        //------------clouds----------------------//
        mList.add(new WeatherCondition("800","clear sky"));
        mList.add(new WeatherCondition("801","few clouds"));
        mList.add(new WeatherCondition("802","scattered clouds"));
        mList.add(new WeatherCondition("803","broken clouds"));
        mList.add(new WeatherCondition("804","overcast clouds"));

        //------------Extreme----------------------//
        mList.add(new WeatherCondition("900","tornado"));
        mList.add(new WeatherCondition("901","tropical storm"));
        mList.add(new WeatherCondition("902","hurricane"));
        mList.add(new WeatherCondition("903","cold"));
        mList.add(new WeatherCondition("904","hot"));
        mList.add(new WeatherCondition("905","windy"));
        mList.add(new WeatherCondition("906","hail"));

        //-----------------Additional----------//
        mList.add(new WeatherCondition("951","calm"));
        mList.add(new WeatherCondition("952","light breeze"));
        mList.add(new WeatherCondition("953","gentle breeze"));
        mList.add(new WeatherCondition("954","moderate breeze"));
        mList.add(new WeatherCondition("955","fresh breeze"));
        mList.add(new WeatherCondition("956","strong breeze"));
        mList.add(new WeatherCondition("957","high wind, near gale"));
        mList.add(new WeatherCondition("958","gale"));
        mList.add(new WeatherCondition("959","severe gale"));
        mList.add(new WeatherCondition("960","storm"));
        mList.add(new WeatherCondition("961","violent storm"));
        mList.add(new WeatherCondition("962","hurricane"));


    }

    public class WeatherCondition {
        String id;
        String meaning;

        public WeatherCondition(String id, String meaning) {
            this.id = id;
            this.meaning = meaning;
        }

        public String getId() {
            return id;
        }

        public String getMeaning() {
            return meaning;
        }
    }

}
