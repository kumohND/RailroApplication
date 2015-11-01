package kr.ac.kumoh.railroApplication.classes;

import android.content.ContentValues;

import kr.ac.kumoh.railroApplication.R;

/**
 * Created by WooChan on 2015-10-11.
 */
public class WeatherCheck {
    WeatherConditionList mCondition;
    ContentValues mData;
    WeatherInfo mWeatherInfo;
    final int CLEAR_SKY = 1; final int FEW_CLOUDS = 2; final int SCATTERED_CLOUDS = 3;
    final int BROKEN_CLOUDS = 4; final int SHOWER_RAIN = 5; final int RAIN = 6;
    final int THUNDERSTORM = 7; final int SNOW = 8; final int MIST = 9;
    String temparature;
    String weatherName;

    public String getWeatherName() {
        return weatherName;
    }

    public String getTemparature() {
        return temparature;
    }

    public WeatherCheck(ContentValues tData)
    {
        mCondition = new WeatherConditionList();
        mData = tData;

        mWeatherInfo = new WeatherInfo(
                String.valueOf(mData.get("weather_Name")),  String.valueOf(mData.get("weather_Number")), String.valueOf(mData.get("weather_Much")),
                String.valueOf(mData.get("weather_Type")),  String.valueOf(mData.get("wind_Direction")),  String.valueOf(mData.get("wind_SortNumber")),
                String.valueOf(mData.get("wind_SortCode")),  String.valueOf(mData.get("wind_Speed")),  String.valueOf(mData.get("wind_Name")),
                String.valueOf(mData.get("temp_Min")),  String.valueOf(mData.get("temp_Max")),  String.valueOf(mData.get("humidity")),
                String.valueOf(mData.get("Clouds_Value")),  String.valueOf(mData.get("Clouds_Sort")), String.valueOf(mData.get("Clouds_Per"))
        );

        weatherName = Hangeul_Weather(mWeatherInfo.getWeather_Number());
        temparature = mWeatherInfo.getTemp_Max();

    }
    public int Calculator_Weather()
    {
        String weatherNumber = mWeatherInfo.getWeather_Number();
        if(isSnow(weatherNumber)) return SNOW;
        else if(isClear(weatherNumber)) return CLEAR_SKY;
        else if(isBroken_Clouds(weatherNumber)) return BROKEN_CLOUDS;
        else if(isFew_Clouds(weatherNumber)) return FEW_CLOUDS;
        else if(isScattered_Clouds(weatherNumber)) return SCATTERED_CLOUDS;
        else if(isRain(weatherNumber)) return RAIN;
        else if(isShower_Rain(weatherNumber)) return SHOWER_RAIN;
        else if(isThunderStrom(weatherNumber)) return THUNDERSTORM;
        else if(isMist(weatherNumber)) return MIST;
        else
            return 0;
    }
    public int WeatherToPicture()
    {
        String weatherNumber = mWeatherInfo.getWeather_Number();
        if(isSnow(weatherNumber)) return R.drawable.snow;
        else if(isClear(weatherNumber)) return R.drawable.sun;
        else if(isBroken_Clouds(weatherNumber)) return R.drawable.broken_cloud;
        else if(isFew_Clouds(weatherNumber)) return R.drawable.few_clouds;
        else if(isScattered_Clouds(weatherNumber)) return R.drawable.scattered_clouds;
        else if(isRain(weatherNumber)) return R.drawable.rainy;
        else if(isShower_Rain(weatherNumber)) return R.drawable.shower_rain;
        else if(isThunderStrom(weatherNumber)) return R.drawable.thunder_strom;
        else if(isMist(weatherNumber)) return R.drawable.mist;
        else
            return 0;
    }
    public boolean isSnow(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListSnow.size() ; i++)
        {
            if(mCondition.mListSnow.get(i).equals(weatherNumber))
                return true;
        }
        return false;
    }
    public boolean isClear(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListClearSky.size() ; i++)
        {
            if(mCondition.mListClearSky.get(i).getId().equals(weatherNumber))
                return true;
        }
        return false;
    }
    public boolean isBroken_Clouds(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListBroken_Clouds.size() ; i++)
        {
            if(mCondition.mListBroken_Clouds.get(i).getId().equals(weatherNumber))
                return true;
        }
        return false;
    }
    public boolean isFew_Clouds(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListFew_Clouds.size() ; i++)
        {
            if(mCondition.mListFew_Clouds.get(i).getId().equals(weatherNumber))
                return true;
        }
        return false;
    }
    public boolean isScattered_Clouds(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListScattered_Clouds.size() ; i++)
        {
            if(mCondition.mListScattered_Clouds.get(i).getId().equals(weatherNumber))
                return true;
        }
        return false;
    }
    public boolean isRain(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListRain.size() ; i++)
        {
            if(mCondition.mListRain.get(i).getId().equals(weatherNumber))
                return true;
        }
        return false;
    }
    public boolean isShower_Rain(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListShower_Rain.size() ; i++)
        {
            if(mCondition.mListShower_Rain.get(i).getId().equals(weatherNumber))
                return true;
        }
        return false;
    }
    public boolean isThunderStrom(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListThunderStorm.size() ; i++)
        {
            if(mCondition.mListThunderStorm.get(i).getId().equals(weatherNumber))
                return true;
        }
        return false;
    }
    public boolean isMist(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListMist.size() ; i++)
        {
            if(mCondition.mListMist.get(i).getId().equals(weatherNumber))
                return true;
        }
        return false;

    }

    public String SnowToHangeul(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListSnow.size() ; i++)
        {
            if(mCondition.mListSnow.get(i).equals(weatherNumber))
                return mCondition.mListSnowToHangeul.get(i).getMeaning();
        }
        return "";
    }
    public String ClearToHangeul(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListClearSky.size() ; i++)
        {
            if(mCondition.mListClearSky.get(i).getId().equals(weatherNumber))
                return mCondition.mListClearSkyToHangeul.get(i).getMeaning();
        }
        return "";
    }
    public String BrokenCloudsToHangeul(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListBroken_Clouds.size() ; i++)
        {
            if(mCondition.mListBroken_Clouds.get(i).getId().equals(weatherNumber))
                return mCondition.mListBroken_CloudsToHangeul.get(i).getMeaning();
        }
        return "";
    }
    public String FewCloudsToHangeul(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListFew_Clouds.size() ; i++)
        {
            if(mCondition.mListFew_Clouds.get(i).getId().equals(weatherNumber))
                return mCondition.mListFew_CloudsToHangeul.get(i).getMeaning();
        }
        return "";
    }
    public String ScatteredCloudsToHangeul(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListScattered_Clouds.size() ; i++)
        {
            if(mCondition.mListScattered_Clouds.get(i).getId().equals(weatherNumber))
                return mCondition.mListScattered_CloudsToHangeul.get(i).getMeaning();
        }
        return "";
    }
    public String RainToHangeul(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListRain.size() ; i++)
        {
            if(mCondition.mListRain.get(i).getId().equals(weatherNumber))
                return mCondition.mListRainToHangeul.get(i).getMeaning();
        }
        return "";
    }
    public String ShowerRainToHanGeul(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListShower_Rain.size() ; i++)
        {
            if(mCondition.mListShower_Rain.get(i).getId().equals(weatherNumber))
                return mCondition.mListShower_RainToHangeul.get(i).getMeaning();
        }
        return "";
    }
    public String ThunderStromToHangeul(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListThunderStorm.size() ; i++)
        {
            if(mCondition.mListThunderStorm.get(i).getId().equals(weatherNumber))
                return mCondition.mListThunderStormToHangeul.get(i).getMeaning();
        }
        return "";
    }
    public String MistToHangeul(String weatherNumber)
    {
        for(int i = 0; i < mCondition.mListMist.size() ; i++)
        {
            if(mCondition.mListMist.get(i).getId().equals(weatherNumber))
                return mCondition.mListMistToHangeul.get(i).getMeaning();
        }
        return "";
    }

    public String WindToHangeul(String windName)
    {
        for(int i = 0; i < mCondition.mListWind.size() ; i++) {
            if(mCondition.mListWind.get(i).getMeaning().equals(windName.toLowerCase()))
                return mCondition.mListWindToHangeul.get(i).getMeaning();
        }
        return "";
    }

    public String Hangeul_Weather(String weatherName)
    {
        String snow = SnowToHangeul(weatherName);
        String clear = ClearToHangeul(weatherName);
        String broken_Cloud = BrokenCloudsToHangeul(weatherName);
        String few_Cloud = FewCloudsToHangeul(weatherName);
        String scatter = ScatteredCloudsToHangeul(weatherName);
        String Rain = RainToHangeul(weatherName);
        String shower = ShowerRainToHanGeul(weatherName);
        String thunder = ThunderStromToHangeul(weatherName);
        String mist = MistToHangeul(weatherName);

        if(!snow.equals("")) return snow;
        else if(!clear.equals("")) return clear;
        else if(!broken_Cloud.equals("")) return broken_Cloud;
        else if(!few_Cloud.equals("")) return few_Cloud;
        else if(!scatter.equals("")) return scatter;
        else if(!Rain.equals("")) return Rain;
        else if(!shower.equals("")) return shower;
        else if(!thunder.equals("")) return thunder;
        else if(!mist.equals("")) return mist;

        return "";
    }
}

