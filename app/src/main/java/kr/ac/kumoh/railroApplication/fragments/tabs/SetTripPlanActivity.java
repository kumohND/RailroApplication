package kr.ac.kumoh.railroApplication.fragments.tabs;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.classes.ForeCast;
import kr.ac.kumoh.railroApplication.classes.LatLng;
import kr.ac.kumoh.railroApplication.classes.LocationChangeLonLat;
import kr.ac.kumoh.railroApplication.classes.LocationInform;
import kr.ac.kumoh.railroApplication.classes.ReadTrainInfoSetActivity;
import kr.ac.kumoh.railroApplication.classes.StationInfo;
import kr.ac.kumoh.railroApplication.classes.WeatherConditionList;
import kr.ac.kumoh.railroApplication.classes.WeatherInfo;
import kr.ac.kumoh.railroApplication.classes.WebViewActivity;

/**
 * Created by sj on 2015-07-30.
 */
public class SetTripPlanActivity extends ActionBarActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    final int CLEAR_SKY = 1; final int FEW_CLOUDS = 2; final int SCATTERED_CLOUDS = 3;
    final int BROKEN_CLOUDS = 4; final int SHOWER_RAIN = 5; final int RAIN = 6;
    final int THUNDERSTORM = 7; final int SNOW = 8; final int MIST = 9;



    @InjectView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    Spinner mSpinner;

    Intent mIntent;

    private final int REQUEST_PLAN = 1000;

    private ArrayAdapter<String> cate;
    int selected;

    private ArrayList<String> station;

    LocationChangeLonLat mLocationData;
    //Context mContext;
    LinearLayout moveTrain;  LinearLayout moveBus;
    LinearLayout toMeal; LinearLayout sleep;
    LinearLayout layout_station_start_Weather;LinearLayout layout_station_end_Weather;
    LinearLayout layout_region_start_Weather; LinearLayout layout_region_end_Weather;
    LinearLayout layout_meal_Weather; LinearLayout layout_sleep_Weather;

    Button doSomething; Button sTimeFix; Button eTimeFix;
    Button sLocation; Button eLocation; Button wSleep;
    Button wEat;

    Button movingTime; Button movingTime2; Button sStation;
    Button eStation;
    Button plan_Success;
    Button start_Train_Weather;
    Button end_Train_Weather;
    Button start_Region_Weather;
    Button end_Region_Weather;
    Button meal_Weather;
    Button sleep_Weather;

    ImageView iv_Region_Start_Weather;
    TextView tv_Region_Start_Weather_Name;
    TextView tv_Region_Start_Date;
    TextView tv_Region_Start_Max_Temp;
    TextView tv_Region_Start_Min_Temp;
    TextView tv_Region_Start_Humi;
    TextView tv_Region_Start_Clouds;
    TextView tv_Region_Start_Winds;

    ImageView iv_Region_End_Weather;
    TextView tv_Region_End_Date;
    TextView tv_Region_End_Weather_Name;
    TextView tv_Region_End_Max_Temp;
    TextView tv_Region_End_Min_Temp;
    TextView tv_Region_End_Humi;
    TextView tv_Region_End_Clouds;
    TextView tv_Region_End_Winds;

    ImageView iv_Station_Start_Weather;
    TextView tv_Station_Start_Weather_Name;
    TextView tv_Station_Start_Date;
    TextView tv_Station_Start_Max_Temp;
    TextView tv_Station_Start_Min_Temp;
    TextView tv_Station_Start_Humi;
    TextView tv_Station_Start_Clouds;
    TextView tv_Station_Start_Winds;

    ImageView iv_Station_End_Weather;
    TextView tv_Station_End_Date;
    TextView tv_Station_End_Weather_Name;
    TextView tv_Station_End_Max_Temp;
    TextView tv_Station_End_Min_Temp;
    TextView tv_Station_End_Humi;
    TextView tv_Station_End_Clouds;
    TextView tv_Station_End_Winds;

    ImageView iv_Meal_Weather;
    TextView tv_Meal_Weather_Name;
    TextView tv_Meal_Date;
    TextView tv_Meal_Max_Temp;
    TextView tv_Meal_Min_Temp;
    TextView tv_Meal_Humi;
    TextView tv_Meal_Clouds;
    TextView tv_Meal_Winds;

    ImageView iv_Sleep_Weather;
    TextView tv_Sleep_Weather_Name;
    TextView tv_Sleep_Date;
    TextView tv_Sleep_Max_Temp;
    TextView tv_Sleep_Min_Temp;
    TextView tv_Sleep_Humi;
    TextView tv_Sleep_Clouds;
    TextView tv_Sleep_Winds;

    LocationInform mStartInform;
    LocationInform mEndInform;
    StationInfo data_startStation;
    StationInfo data_endStation;

    boolean flag_StartStation = false;
    boolean flag_EndStation = false;

    boolean flag_Start_StationLayout = false;
    boolean flag_End_StationLayout = false;
    boolean flag_Start_RegionLayout = false;
    boolean flag_End_RegionLayout = false;
    boolean flag_MealLayout = false;
    boolean flag_SleepLayout = false;


    String default_sTime = "출발 시간:"; String default_eTime = "도착 시간:"; String default_sStation = "출발역 : "; String default_eStation = "도착역 : ";
    String default_toDo = "할 일:"; String default_moveValue = "이동 시간:";


    WeatherInfo mStartTrainWeather;
    WeatherInfo mEndTrainWeather;
    WeatherInfo mStartRegionWeather;
    WeatherInfo mEndRegionWeather;
    WeatherInfo mMealWeather;
    WeatherInfo mSleepWeather;

    ContentValues start_Weather;
    ContentValues end_Weather;
    String year; String month; String day;
    Calendar calendar = Calendar.getInstance();

    String TokenForLocation(String val)
    {
        StringTokenizer token = new StringTokenizer(val,":");
        token.nextToken();
        String value = token.nextToken();
        return value;
    }
    void SetLayoutTrain()
    {
        moveTrain = (LinearLayout)findViewById(R.id.MoveToTrain);
        layout_station_start_Weather =(LinearLayout)findViewById(R.id.layout_Station_Start_Weather);
        layout_station_end_Weather = (LinearLayout)findViewById(R.id.layout_Station_End_Weather);
        start_Train_Weather = (Button)findViewById(R.id.btn_start_train_Weather);
        end_Train_Weather = (Button)findViewById(R.id.btn_end_train_Weather);

        sStation = (Button)findViewById(R.id.set_start_station);
        eStation = (Button)findViewById(R.id.set_end_station);

        movingTime = (Button)findViewById(R.id.btn_moving_time);
        start_Train_Weather.setOnClickListener(this);
        end_Train_Weather.setOnClickListener(this);
        sStation.setOnClickListener(this);
        eStation.setOnClickListener(this);
        movingTime.setOnClickListener(this);
        SetStartTrainWeatherDetail();
        SetEndTrainWeatherDetail();
    }
    void SetEndTrainWeatherDetail()
    {
        iv_Station_End_Weather = (ImageView)findViewById(R.id.iv_Station_End_Weather);
        tv_Station_End_Weather_Name = (TextView)findViewById(R.id.tv_Station_End_Weather_Name);
        tv_Station_End_Date = (TextView)findViewById(R.id.tv_Station_End_Date);
        tv_Station_End_Max_Temp = (TextView)findViewById(R.id.tv_Station_End_Max_Temp);
        tv_Station_End_Min_Temp = (TextView)findViewById(R.id.tv_Station_End_Min_Temp);
        tv_Station_End_Humi = (TextView)findViewById(R.id.tv_Station_End_Humi);
        tv_Station_End_Clouds = (TextView)findViewById(R.id.tv_Station_End_Clouds);
        tv_Station_End_Winds = (TextView)findViewById(R.id.tv_Station_End_Winds);
    }
    void SetStartTrainWeatherDetail()
    {
        iv_Station_Start_Weather = (ImageView)findViewById(R.id.iv_Station_Start_Weather);
        tv_Station_Start_Weather_Name = (TextView)findViewById(R.id.tv_Station_Start_Weather_Name);
        tv_Station_Start_Date = (TextView)findViewById(R.id.tv_Station_Start_Date);
        tv_Station_Start_Max_Temp = (TextView)findViewById(R.id.tv_Station_Start_Max_Temp);
        tv_Station_Start_Min_Temp = (TextView)findViewById(R.id.tv_Station_Start_Min_Temp);
        tv_Station_Start_Humi = (TextView)findViewById(R.id.tv_Station_Start_Humi);
        tv_Station_Start_Clouds = (TextView)findViewById(R.id.tv_Station_Start_Clouds);
        tv_Station_Start_Winds = (TextView)findViewById(R.id.tv_Station_Start_Winds);

    }
    void SetLayoutRegion()
    {
        layout_region_start_Weather =(LinearLayout)findViewById(R.id.layout_Region_Start_Weather);
        layout_region_end_Weather =(LinearLayout)findViewById(R.id.layout_Region_End_Weather);
        moveBus = (LinearLayout)findViewById(R.id.MoveTobus);
        movingTime2 = (Button)findViewById(R.id.btn_moving_time2);
        sLocation = (Button)findViewById(R.id.set_start_location);
        eLocation = (Button)findViewById(R.id.set_end_location);
        start_Region_Weather= (Button)findViewById(R.id.btn_start_region_Weather);
        end_Region_Weather= (Button)findViewById(R.id.btn_end_region_Weather);
        start_Region_Weather.setOnClickListener(this);
        end_Region_Weather.setOnClickListener(this);
        eLocation.setOnClickListener(this);
        sLocation.setOnClickListener(this);
        movingTime2.setOnClickListener(this);
        SetStartRegionWeatherDetail();
        SetEndRegionWeatherDetail();
    }
    void SetStartRegionWeatherDetail()
    {
        iv_Region_Start_Weather = (ImageView)findViewById(R.id.iv_Region_Start_Weather);
        tv_Region_Start_Weather_Name = (TextView)findViewById(R.id.tv_Region_Start_Weather_Name);
        tv_Region_Start_Date = (TextView)findViewById(R.id.tv_Region_Start_Date);
        tv_Region_Start_Max_Temp = (TextView)findViewById(R.id.tv_Region_Start_Max_Temp);
        tv_Region_Start_Min_Temp = (TextView)findViewById(R.id.tv_Region_Start_Min_Temp);
        tv_Region_Start_Humi = (TextView)findViewById(R.id.tv_Region_Start_Humi);
        tv_Region_Start_Clouds = (TextView)findViewById(R.id.tv_Region_Start_Clouds);
        tv_Region_Start_Winds = (TextView)findViewById(R.id.tv_Region_Start_Winds);
    }
    void SetEndRegionWeatherDetail()
    {
        iv_Region_End_Weather = (ImageView)findViewById(R.id.iv_Region_End_Weather);
        tv_Region_End_Weather_Name = (TextView)findViewById(R.id.tv_Region_End_Weather_Name);
        tv_Region_End_Date = (TextView)findViewById(R.id.tv_Region_End_Date);
        tv_Region_End_Max_Temp = (TextView)findViewById(R.id.tv_Region_End_Max_Temp);
        tv_Region_End_Min_Temp = (TextView)findViewById(R.id.tv_Region_End_Min_Temp);
        tv_Region_End_Humi = (TextView)findViewById(R.id.tv_Region_End_Humi);
        tv_Region_End_Clouds = (TextView)findViewById(R.id.tv_Region_End_Clouds);
        tv_Region_End_Winds = (TextView)findViewById(R.id.tv_Region_End_Winds);
    }
    void SetLayoutMeal()
    {
        layout_meal_Weather =(LinearLayout)findViewById(R.id.layout_Meal_Weather);
         toMeal = (LinearLayout)findViewById(R.id.ToMeal);
        wEat = (Button)findViewById(R.id.btn_toMeal_location);
        meal_Weather = (Button)findViewById(R.id.btn_meal_Weather);
        meal_Weather.setOnClickListener(this);
        wEat.setOnClickListener(this);
        SetMealWeatherDetail();


    }
    void SetMealWeatherDetail()
    {
        iv_Meal_Weather = (ImageView)findViewById(R.id.iv_Meal_Weather);
        tv_Meal_Weather_Name = (TextView)findViewById(R.id.tv_Meal_Weather_Name);
        tv_Meal_Date = (TextView)findViewById(R.id.tv_Meal_Date);
        tv_Meal_Max_Temp = (TextView)findViewById(R.id.tv_Meal_Max_Temp);
        tv_Meal_Min_Temp = (TextView)findViewById(R.id.tv_Meal_Min_Temp);
        tv_Meal_Humi = (TextView)findViewById(R.id.tv_Meal_Humi);
        tv_Meal_Clouds = (TextView)findViewById(R.id.tv_Meal_Clouds);
        tv_Meal_Winds = (TextView)findViewById(R.id.tv_Meal_Winds);
    }
    void SetLayoutSleep()
    {
        layout_sleep_Weather =(LinearLayout)findViewById(R.id.layout_Sleep_Weather);
        sleep = (LinearLayout)findViewById(R.id.Sleep);
        wSleep = (Button)findViewById(R.id.btn_Sleep_location);
        sleep_Weather = (Button)findViewById(R.id.btn_Sleep_Weather);
        sleep_Weather.setOnClickListener(this);
        wSleep.setOnClickListener(this);
        SetSleepWeatherDetail();
    }
    void SetSleepWeatherDetail()
    {
        iv_Sleep_Weather = (ImageView)findViewById(R.id.iv_Sleep_Weather);
        tv_Sleep_Weather_Name = (TextView)findViewById(R.id.tv_Sleep_Weather_Name);
        tv_Sleep_Date = (TextView)findViewById(R.id.tv_Sleep_Date);
        tv_Sleep_Max_Temp = (TextView)findViewById(R.id.tv_Sleep_Max_Temp);
        tv_Sleep_Min_Temp = (TextView)findViewById(R.id.tv_Sleep_Min_Temp);
        tv_Sleep_Humi = (TextView)findViewById(R.id.tv_Sleep_Humi);
        tv_Sleep_Clouds = (TextView)findViewById(R.id.tv_Sleep_Clouds);
        tv_Sleep_Winds = (TextView)findViewById(R.id.tv_Sleep_Winds);
    }
    void ButtonEffect()
    {

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_plan_list);
        ButterKnife.inject(this);
        setupToolbar();
        mCondition = new WeatherConditionList();
        SetLayoutTrain();
        SetLayoutRegion();
        SetLayoutMeal();
        SetLayoutSleep();

        calendar = Calendar.getInstance();
        sTimeFix = (Button)findViewById(R.id.fix_start_Time);
        eTimeFix = (Button)findViewById(R.id.fix_end_Time);
        doSomething = (Button)findViewById(R.id.tv_DoSomething);

        plan_Success = (Button)findViewById(R.id.btn_success_plan);
        plan_Success.setOnClickListener(this);
//        plan_Cancel.setOnClickListener(this);
        doSomething.setOnClickListener(this);
        sTimeFix.setOnClickListener(this);
        eTimeFix.setOnClickListener(this);

        ButtonEffect();
        //mContext = getContext();


        onReadDetail(); // 초기, text data 존재하나 확인

        mSpinner = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);


    }



    private void setupToolbar() {
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        if (mToolbar == null) {
            //  LOGD(this, "Didn't find a toolbar");
            return;
        }
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setTitle("여행 설정");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }


    @Override
    public void onClick(View v) {
        Intent intent ;


        switch (v.getId()) {
            case R.id.fix_start_Time: // STARTtIME만 설정
                TimePickerDialog dialog = new TimePickerDialog(this, start_Listener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                dialog.show();
//                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//                    @Override
//                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                        //Toast.makeText(, "주소 오류", Toast.LENGTH_SHORT);
//                        return false;
//                    }
//                });
                break;
            case R.id.fix_end_Time: // STARTtIME만 설정
                TimePickerDialog dialog2 = new TimePickerDialog(this, end_Listener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                dialog2.show();
//                dialog2.setOnKeyListener(new DialogInterface.OnKeyListener() {
//                    @Override
//                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                        //Toast.makeText(, "주소 오류", Toast.LENGTH_SHORT);
//                        return false;
//                    }
//                });
                break;
            case R.id.set_start_station: // 시작하는 기차역 조회
                intent = new Intent(this, ReadTrainInfoSetActivity.class);
                startActivityForResult(intent, 1);
                flag_StartStation = true;
                break;

            case R.id.set_end_station: // 끝나는 기차역 조회
                intent = new Intent(this, ReadTrainInfoSetActivity.class);
                startActivityForResult(intent, 2);
                flag_EndStation = true;
                break;

            case R.id.btn_success_plan: // 저장 버튼 누를시 실행
                onPlanOkay();
                finish();
                break;
            case R.id.btn_moving_time:
                    intent = InputData(1);
                    if(intent == null){
                        Toast.makeText(this,"주소 오류",Toast.LENGTH_SHORT);
                        break;
                    }
                    startActivity(intent);

                break;
            case R.id.btn_moving_time2:
                intent = InputData(2);
                if(intent == null){
                    Toast.makeText(this,"주소 오류",Toast.LENGTH_SHORT);
                    break;
                }
                startActivity(intent);
                break;
            case R.id.btn_start_train_Weather:
                if(flag_Start_StationLayout == false)
                {
                    layout_station_start_Weather.setVisibility(View.VISIBLE);
                    onDetailChangeTrain();
                    flag_Start_StationLayout = true;
                }else if(flag_Start_StationLayout == true)
                {
                    layout_station_start_Weather.setVisibility(View.GONE);
                    flag_Start_StationLayout = false;
                }
                break;
            case R.id.btn_end_train_Weather:
                if(flag_End_StationLayout == false)
                {
                    layout_station_end_Weather.setVisibility(View.VISIBLE);
                    onDetailChangeTrain();
                    flag_End_StationLayout = true;
                }else if(flag_End_StationLayout == true)
                {
                    layout_station_end_Weather.setVisibility(View.GONE);
                    flag_End_StationLayout = false;
                }
                break;
            case R.id.btn_start_region_Weather:
                if(flag_Start_RegionLayout == false)
                {
                    layout_region_start_Weather.setVisibility(View.VISIBLE);
                    onDetailChangeRegion();
                    flag_Start_RegionLayout = true;
                }else if(flag_Start_RegionLayout == true)
                {
                    layout_region_start_Weather.setVisibility(View.GONE);
                    flag_Start_RegionLayout = false;
                }
                break;

            case R.id.btn_end_region_Weather:
                if(flag_End_RegionLayout == false)
                {
                    layout_region_end_Weather.setVisibility(View.VISIBLE);
                    onDetailChangeRegion();
                    flag_End_RegionLayout = true;
                }else if(flag_End_RegionLayout == true)
                {
                    layout_region_end_Weather.setVisibility(View.GONE);
                    flag_End_RegionLayout = false;
                }
                break;

            case R.id.btn_meal_Weather:
                if(flag_MealLayout == false)
                {
                    layout_meal_Weather.setVisibility(View.VISIBLE);
                    onDetailChangeMeal();
                    flag_MealLayout = true;
                }else if(flag_MealLayout == true)
                {
                    layout_meal_Weather.setVisibility(View.GONE);
                    flag_MealLayout = false;
                }
                break;
            case R.id.btn_Sleep_Weather:
                if(flag_SleepLayout == false)
                {
                    layout_sleep_Weather.setVisibility(View.VISIBLE);
                    onDetailChangeSleep();
                    flag_SleepLayout = true;
                }else if(flag_SleepLayout == true)
                {
                    layout_sleep_Weather.setVisibility(View.GONE);
                    flag_SleepLayout = false;
                }
                break;
        }
    }
    public void onDetailChangeTrain()
    {
        iv_Station_Start_Weather.setImageResource(mStartTrainWeather.getPicture_ID());
        tv_Station_Start_Weather_Name.setText(mStartTrainWeather.getWeather_Name());
        tv_Station_Start_Max_Temp.setText(mStartTrainWeather.getTemp_Max() + "℃");
        tv_Station_Start_Min_Temp.setText(mStartTrainWeather.getTemp_Min() + "℃");
        tv_Station_Start_Humi.setText("습도:" + mStartTrainWeather.getHumidity() + "%");
        tv_Station_Start_Clouds.setText(mStartTrainWeather.getClouds_Sort() +
                " : " + mStartTrainWeather.getClouds_Value() + "%");
        tv_Station_Start_Winds.setText(mStartTrainWeather.getWind_Name() + " : " +
                mStartTrainWeather.getWind_Speed() + "mps");

        //////////////////////
        iv_Station_End_Weather.setImageResource(mEndTrainWeather.getPicture_ID());
        tv_Station_End_Weather_Name.setText(mEndTrainWeather.getWeather_Name());
        tv_Station_End_Max_Temp.setText(mEndTrainWeather.getTemp_Max() + "℃");
        tv_Station_End_Min_Temp.setText(mEndTrainWeather.getTemp_Min() + "℃");
        tv_Station_End_Humi.setText("습도:" + mEndTrainWeather.getHumidity() + "%");
        tv_Station_End_Clouds.setText(mEndTrainWeather.getClouds_Sort() +
                " : " + mEndTrainWeather.getClouds_Value() + "%");
        tv_Station_End_Winds.setText(mEndTrainWeather.getWind_Name() + " : " +
                mEndTrainWeather.getWind_Speed() + "mps");
    }
    public void onDetailChangeRegion()
    {
        iv_Region_Start_Weather.setImageResource(mStartTrainWeather.getPicture_ID());
        tv_Region_Start_Weather_Name.setText(mStartTrainWeather.getWeather_Name());
        tv_Region_Start_Max_Temp.setText(mStartTrainWeather.getTemp_Max() + "");
        tv_Region_Start_Min_Temp.setText(mStartTrainWeather.getTemp_Min());
        tv_Region_Start_Humi.setText("습도:" + mStartTrainWeather.getHumidity() + "%");
        tv_Region_Start_Clouds.setText(mStartTrainWeather.getClouds_Sort() +
                " : " + mStartTrainWeather.getClouds_Value() + "%");
        tv_Region_Start_Winds.setText(mStartTrainWeather.getWind_Name() + " : " +
                mStartTrainWeather.getWind_Speed() + "mps");

        //////////////////////
        iv_Region_End_Weather.setImageResource(mEndTrainWeather.getPicture_ID());
        tv_Region_End_Weather_Name.setText(mEndTrainWeather.getWeather_Name());
        tv_Region_End_Max_Temp.setText(mEndTrainWeather.getTemp_Max() + "");
        tv_Region_End_Min_Temp.setText(mEndTrainWeather.getTemp_Min());
        tv_Region_End_Humi.setText("습도:" + mEndTrainWeather.getHumidity() + "%");
        tv_Region_End_Clouds.setText(mEndTrainWeather.getClouds_Sort() +
                " : " + mEndTrainWeather.getClouds_Value() + "%");
        tv_Region_End_Winds.setText(mEndTrainWeather.getWind_Name() + " : " +
                mEndTrainWeather.getWind_Speed() + "mps");
    }
    public void onDetailChangeMeal()
    {
        iv_Meal_Weather.setImageResource(mStartTrainWeather.getPicture_ID());
        tv_Meal_Weather_Name.setText(mStartTrainWeather.getWeather_Name());
        tv_Meal_Max_Temp.setText(mStartTrainWeather.getTemp_Max() + "");
        tv_Meal_Min_Temp.setText(mStartTrainWeather.getTemp_Min());
        tv_Meal_Humi.setText("습도:" + mStartTrainWeather.getHumidity() + "%");
        tv_Meal_Clouds.setText(mStartTrainWeather.getClouds_Sort() +
                " : " + mStartTrainWeather.getClouds_Value() + "%");
        tv_Meal_Winds.setText(mStartTrainWeather.getWind_Name() + " : " +
                mStartTrainWeather.getWind_Speed() + "mps");

    }

    public void onDetailChangeSleep()
    {
        iv_Sleep_Weather.setImageResource(mStartTrainWeather.getPicture_ID());
        tv_Sleep_Weather_Name.setText(mStartTrainWeather.getWeather_Name());
        tv_Sleep_Max_Temp.setText(mStartTrainWeather.getTemp_Max() + "");
        tv_Sleep_Min_Temp.setText(mStartTrainWeather.getTemp_Min());
        tv_Sleep_Humi.setText("습도:" + mStartTrainWeather.getHumidity() + "%");
        tv_Sleep_Clouds.setText(mStartTrainWeather.getClouds_Sort() +
                " : " + mStartTrainWeather.getClouds_Value() + "%");
        tv_Sleep_Winds.setText(mStartTrainWeather.getWind_Name() + " : " +
                mStartTrainWeather.getWind_Speed() + "mps");
    }
    // 이부분은 Train 에만 해당, 4가지 다 되게 바꿔야함
    public void CheckWeather()
    {
        mLocationData = new LocationChangeLonLat();

        if(selected == 0) TrainLocateAndWeather();
        else if(selected == 1) RegionLocateAndWeather();
        else if(selected == 2) MealLocateAndWeather();
        else SleepLocateAndWeather();

    }
    public void SleepLocateAndWeather()
    {
        mLocationData.initControl(TokenForLocation(String.valueOf(wSleep.getText())),
                "",this);


        mStartInform = mLocationData.getStartLocationLonLat();

        if(mStartInform != null ) {
            ForeCast mWeather = new ForeCast(mStartInform,mEndInform);
            mWeather.run();
            start_Weather = mWeather.getStart_Weather();

            Input_Weather();
        }else {
            Toast.makeText(this, "위치 정보가 잘못되었습니다.",Toast.LENGTH_SHORT);
        }
        mSleepWeather.setPicture_Category(Calculator_Weather(mStartRegionWeather.getWeather_Number()));
        mSleepWeather.setPicture_ID(WeatherToPicture(mStartRegionWeather.getWeather_Number()));

        sleep_Weather.setBackgroundResource(mSleepWeather.getPicture_ID());
    }
    public void  MealLocateAndWeather()
    {
        mLocationData.initControl(TokenForLocation(String.valueOf(wEat.getText())),
               "",this );


        mStartInform = mLocationData.getStartLocationLonLat();

        if(mStartInform != null ) {
            ForeCast mWeather = new ForeCast(mStartInform,mEndInform);
            mWeather.run();
            start_Weather = mWeather.getStart_Weather();

            Input_Weather();
        }else {
            Toast.makeText(this, "위치 정보가 잘못되었습니다!",Toast.LENGTH_SHORT);
        }
        mMealWeather.setPicture_Category(Calculator_Weather(mStartRegionWeather.getWeather_Number()));
        mMealWeather.setPicture_ID(WeatherToPicture(mStartRegionWeather.getWeather_Number()));

        meal_Weather.setBackgroundResource(mMealWeather.getPicture_ID());
    }
    public void RegionLocateAndWeather()
    {

        mLocationData.initControl(TokenForLocation(String.valueOf(sLocation.getText())),
                TokenForLocation(String.valueOf(eLocation.getText())),this );

        mStartInform = mLocationData.getStartLocationLonLat();
        mEndInform =  mLocationData.getEndLocationLonLat();

        if(mStartInform != null && mEndInform != null) {
            ForeCast mWeather = new ForeCast(mStartInform,mEndInform);
            mWeather.run();
            start_Weather = mWeather.getStart_Weather();
            end_Weather = mWeather.getEnd_Weather();

            Input_Weather();
        }else {
            Toast.makeText(this, "위치 정보가 잘못되었습니다.",Toast.LENGTH_SHORT);
        }
        mStartRegionWeather.setPicture_Category(Calculator_Weather(mStartRegionWeather.getWeather_Number()));
        mEndRegionWeather.setPicture_Category(Calculator_Weather(mEndRegionWeather.getWeather_Number()));
        mStartRegionWeather.setPicture_ID(WeatherToPicture(mStartRegionWeather.getWeather_Number()));
        mEndRegionWeather.setPicture_ID(WeatherToPicture(mEndRegionWeather.getWeather_Number()));

        start_Region_Weather.setBackgroundResource(mStartRegionWeather.getPicture_ID());
        end_Region_Weather.setBackgroundResource(mEndRegionWeather.getPicture_ID());
    }
    public void TrainLocateAndWeather()
    {
        mLocationData.initControl(TokenForLocation(String.valueOf(sStation.getText())),
                TokenForLocation(String.valueOf(eStation.getText())),this );


        mStartInform = mLocationData.getStartLocationLonLat();
        mEndInform =  mLocationData.getEndLocationLonLat();

        if(mStartInform != null && mEndInform != null) {
            ForeCast mWeather = new ForeCast(mStartInform,mEndInform);
            mWeather.run();
            start_Weather = mWeather.getStart_Weather();
            end_Weather = mWeather.getEnd_Weather();

            Input_Weather();
        }else {
            Toast.makeText(this, "위치 정보가 잘못되었습니다.",Toast.LENGTH_SHORT);
        }
        mStartTrainWeather.setPicture_Category(Calculator_Weather(mStartTrainWeather.getWeather_Number()));
        mEndTrainWeather.setPicture_Category(Calculator_Weather(mEndTrainWeather.getWeather_Number()));
        mStartTrainWeather.setPicture_ID(WeatherToPicture(mStartTrainWeather.getWeather_Number()));
        mEndTrainWeather.setPicture_ID(WeatherToPicture(mEndTrainWeather.getWeather_Number()));


        start_Train_Weather.setBackgroundResource(mStartTrainWeather.getPicture_ID());
        end_Train_Weather.setBackgroundResource(mEndTrainWeather.getPicture_ID());

    }
    //TODO : 꾸꾸까까꾸까ㅜ까
    public void Input_Weather()
    {
        if(selected == 0)
        {
            mStartTrainWeather = new WeatherInfo(
                    String.valueOf(start_Weather.get("weather_Name")),  String.valueOf(start_Weather.get("weather_Number")), String.valueOf(start_Weather.get("weather_Much")),
                    String.valueOf(start_Weather.get("weather_Type")),  String.valueOf(start_Weather.get("wind_Direction")),  String.valueOf(start_Weather.get("wind_SortNumber")),
                    String.valueOf(start_Weather.get("wind_SortCode")),  String.valueOf(start_Weather.get("wind_Speed")),  String.valueOf(start_Weather.get("wind_Name")),
                    String.valueOf(start_Weather.get("temp_Min")),  String.valueOf(start_Weather.get("temp_Max")),  String.valueOf(start_Weather.get("humidity")),
                    String.valueOf(start_Weather.get("Clouds_Value")),  String.valueOf(start_Weather.get("Clouds_Sort")), String.valueOf(start_Weather.get("Clouds_Per"))
            );
            mEndTrainWeather = new WeatherInfo(
                    String.valueOf(end_Weather.get("weather_Name")),  String.valueOf(end_Weather.get("weather_Number")), String.valueOf(end_Weather.get("weather_Much")),
                    String.valueOf(end_Weather.get("weather_Type")),  String.valueOf(end_Weather.get("wind_Direction")),  String.valueOf(end_Weather.get("wind_SortNumber")),
                    String.valueOf(end_Weather.get("wind_SortCode")),  String.valueOf(end_Weather.get("wind_Speed")),  String.valueOf(end_Weather.get("wind_Name")),
                    String.valueOf(end_Weather.get("temp_Min")),  String.valueOf(end_Weather.get("temp_Max")),  String.valueOf(end_Weather.get("humidity")),
                    String.valueOf(end_Weather.get("Clouds_Value")),  String.valueOf(end_Weather.get("Clouds_Sort")), String.valueOf(end_Weather.get("Clouds_Per"))
            );
        }else if(selected ==1)
        {
            mStartRegionWeather =  new WeatherInfo(
                    String.valueOf(start_Weather.get("weather_Name")),  String.valueOf(start_Weather.get("weather_Number")), String.valueOf(start_Weather.get("weather_Much")),
                    String.valueOf(start_Weather.get("weather_Type")),  String.valueOf(start_Weather.get("wind_Direction")),  String.valueOf(start_Weather.get("wind_SortNumber")),
                    String.valueOf(start_Weather.get("wind_SortCode")),  String.valueOf(start_Weather.get("wind_Speed")),  String.valueOf(start_Weather.get("wind_Name")),
                    String.valueOf(start_Weather.get("temp_Min")),  String.valueOf(start_Weather.get("temp_Max")),  String.valueOf(start_Weather.get("humidity")),
                    String.valueOf(start_Weather.get("Clouds_Value")),  String.valueOf(start_Weather.get("Clouds_Sort")), String.valueOf(start_Weather.get("Clouds_Per"))
            );
            mEndRegionWeather = new WeatherInfo(
                    String.valueOf(end_Weather.get("weather_Name")),  String.valueOf(end_Weather.get("weather_Number")), String.valueOf(end_Weather.get("weather_Much")),
                    String.valueOf(end_Weather.get("weather_Type")),  String.valueOf(end_Weather.get("wind_Direction")),  String.valueOf(end_Weather.get("wind_SortNumber")),
                    String.valueOf(end_Weather.get("wind_SortCode")),  String.valueOf(end_Weather.get("wind_Speed")),  String.valueOf(end_Weather.get("wind_Name")),
                    String.valueOf(end_Weather.get("temp_Min")),  String.valueOf(end_Weather.get("temp_Max")),  String.valueOf(end_Weather.get("humidity")),
                    String.valueOf(end_Weather.get("Clouds_Value")),  String.valueOf(end_Weather.get("Clouds_Sort")), String.valueOf(end_Weather.get("Clouds_Per"))
            );
        }else if(selected == 2)
        {
            mMealWeather =  new WeatherInfo(
                    String.valueOf(start_Weather.get("weather_Name")),  String.valueOf(start_Weather.get("weather_Number")), String.valueOf(start_Weather.get("weather_Much")),
                    String.valueOf(start_Weather.get("weather_Type")),  String.valueOf(start_Weather.get("wind_Direction")),  String.valueOf(start_Weather.get("wind_SortNumber")),
                    String.valueOf(start_Weather.get("wind_SortCode")),  String.valueOf(start_Weather.get("wind_Speed")),  String.valueOf(start_Weather.get("wind_Name")),
                    String.valueOf(start_Weather.get("temp_Min")),  String.valueOf(start_Weather.get("temp_Max")),  String.valueOf(start_Weather.get("humidity")),
                    String.valueOf(start_Weather.get("Clouds_Value")),  String.valueOf(start_Weather.get("Clouds_Sort")), String.valueOf(start_Weather.get("Clouds_Per"))
            );
        }else if(selected == 3)
        {
            mSleepWeather=  new WeatherInfo(
                    String.valueOf(start_Weather.get("weather_Name")),  String.valueOf(start_Weather.get("weather_Number")), String.valueOf(start_Weather.get("weather_Much")),
                    String.valueOf(start_Weather.get("weather_Type")),  String.valueOf(start_Weather.get("wind_Direction")),  String.valueOf(start_Weather.get("wind_SortNumber")),
                    String.valueOf(start_Weather.get("wind_SortCode")),  String.valueOf(start_Weather.get("wind_Speed")),  String.valueOf(start_Weather.get("wind_Name")),
                    String.valueOf(start_Weather.get("temp_Min")),  String.valueOf(start_Weather.get("temp_Max")),  String.valueOf(start_Weather.get("humidity")),
                    String.valueOf(start_Weather.get("Clouds_Value")),  String.valueOf(start_Weather.get("Clouds_Sort")), String.valueOf(start_Weather.get("Clouds_Per"))
            );
        }


    }
    WeatherConditionList mCondition = new WeatherConditionList();

    public int Calculator_Weather(String weatherNumber)
    {
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
    public int WeatherToPicture(String weatherNumber)
    {

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

    public Intent InputData(int index)
    {

        mLocationData = new LocationChangeLonLat();
        if(index == 1 ) {
            mLocationData.initControl(TokenForLocation(String.valueOf(sStation.getText())),
                    TokenForLocation(String.valueOf(eStation.getText())),this );
        }else{
            mLocationData.initControl(TokenForLocation(String.valueOf(sLocation.getText())),
                    TokenForLocation(String.valueOf(sLocation.getText())),this );
        }
        mStartInform = mLocationData.getStartLocationLonLat();
        mEndInform =  mLocationData.getEndLocationLonLat();

        if(mStartInform != null && mEndInform != null) {
            Intent i = new Intent(this, WebViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("start", mStartInform);
            bundle.putParcelable("end", mEndInform);
            bundle.putString("url", getUrl(mStartInform.latlng, mEndInform.latlng));
            i.putExtras(bundle);
            return i;
        }
        return null;
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
        urlString.append(cutTail(start.getLat(), n));
        urlString.append(",");
        urlString.append(cutTail(start.getLng(), n));

        //to
        urlString.append("/");
        urlString.append(cutTail(end.getLat(), n));
        urlString.append(",");
        urlString.append(cutTail(end.getLng(), n));


        return urlString.toString();
    }



    BufferedWriter buf;

    private void onPlanOkay() {
        String path = "/data/data/kr.ac.kumoh.railrotravel/files/datasheet.ext"; // 저장 할 곳
        File file;
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + File.separator + "201508065" + ".txt"); // 여기서 텍스트 이름만 수정하면 될듯

        try{

            int check = selected;

            buf = new BufferedWriter(new FileWriter(file));

            buf.write(String.valueOf(check));  buf.newLine();
            // 구분 번호
            buf.write((String)sTimeFix.getText()); buf.newLine();
            buf.write((String)eTimeFix.getText());   buf.newLine();
            // Time Save //
            buf.write((String)doSomething.getText()); buf.newLine();
            // Do Something //
            // ------------------ 공통 부분은 여기에 저장 ----------------- //
            if(check == 0) // train
            {
                buf.write((String) sStation.getText()); buf.newLine();
                buf.write(data_startStation.getStationCode()); buf.newLine();
                buf.write((String) eStation.getText()); buf.newLine();
                buf.write(data_endStation.getStationCode()); buf.newLine();
                buf.write((String) movingTime.getText());  buf.newLine();
            }else if(check ==1) // bus
            {
                buf.write((String) sLocation.getText()); buf.newLine();
                buf.write((String) eLocation.getText()); ; buf.newLine();
                buf.write((String) movingTime2.getText());
            }else if(check ==2){ // sleep
                buf.write((String) wSleep.getText());
            }else{ //toMeal
                buf.write((String) wEat.getText());
            }
            buf.close();

        }catch(IOException e)
        {

        }
    }

    BufferedReader buw;
    int readCheck = -1; // 껏다 켯을때, spin위치 체크

    private void onReadDetail() {
        String path = "/data/data/kr.ac.kumoh.railrotravel/files/datasheet.ext";
        File file;
        file = new File(path);
        ArrayList<String> mData;
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + File.separator + "201508065" + ".txt");
        try{

            int check;

            buw = new BufferedReader(new FileReader(file));
            String rawData = "";
            String temp;
            while((temp = buw.readLine()) != null)
            {
                rawData += (temp + "\r\n");
            }

            Log.d("READ",rawData);

            buw.close();
            if(rawData.equals("")) return ;

            mData = InitDataStringTokenizer(rawData); // text 데이터 -> ArrayList로 만들어 반환

            readCheck = Integer.valueOf(mData.get(0));
            check = Integer.valueOf(mData.get(0));
            // 구분 번호 //
            sTimeFix.setText(default_sTime + mData.get(1));
            eTimeFix.setText(default_eTime + mData.get(2));

            // Time Save //
            doSomething.setText(default_toDo + mData.get(3));

            // Do Something //


            // 보여줄 데이터 셋
            if(check == 0) // train
            {
                data_startStation = new StationInfo(mData.get(4),mData.get(5));
                sStation.setText(default_sStation + mData.get(4));
                data_endStation = new StationInfo(mData.get(6),mData.get(7));
                eStation.setText(default_eStation + mData.get(6));
                movingTime.setText(mData.get(7) + default_moveValue );
            }else if(check ==1) // bus
            {
                sLocation.setText(mData.get(4));
                eLocation.setText(mData.get(5));
                movingTime.setText(mData.get(6) + default_moveValue);
            }else if(check ==2){ // sleep
                wSleep.setText(mData.get(4));
            }else{ //toMeal
                wEat.setText(mData.get(4));
            }
        }catch(IOException e)
        {

        }

        CheckWeather();
    }

    //토큰 분리
    private ArrayList<String> InitDataStringTokenizer(String rawString) {

        StringTokenizer mToken = new StringTokenizer(rawString, "\r\n");
        ArrayList<String> mData = new ArrayList<String>();
        while (mToken.hasMoreTokens())
            mData.add(mToken.nextToken());


        return mData;

    }

    String named_buffer;

    // 기차역 조회
    private void onTestReadAndSet(int check) {
        //mCast = new ForeCast();

        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext";
        try{
            File file;
            file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(path + File.separator + "temp" + ".txt");


            BufferedReader buw = new BufferedReader(new FileReader(file));

            named_buffer = buw.readLine();
            named_buffer += "\r\n" + buw.readLine();
        }catch(IOException e)
        {

        }
        if(buw == null) {
            if (check == 1) {
                sStation.setText(default_sStation + "서울역");
                return;
            } else{
                eStation.setText(default_eStation + "부산역");
                return;
            }
        }

        SetTextStation(check);

        CheckWeather();
    }

    void SetTextStation(int flag)
    {
        if(flag == 1) //startStation
        {

            flag_StartStation = false ;
            data_startStation = StringToToken(named_buffer);
            if(data_startStation == null){
                sStation.setText(default_sStation + "서울역");
                return ;
            }
            sStation.setText(default_sStation + data_startStation.getStationName());
        }else if(flag == 2) // endStation
        {
            flag_EndStation = false;
            data_endStation = StringToToken(named_buffer);
            if(data_endStation == null){
                eStation.setText(default_eStation + "부산역");
                return ;
            }
            eStation.setText(default_eStation + data_endStation.getStationName());
        }

    }

    StationInfo StringToToken(String rawString) {
        if(rawString.contains("nothing")){
               return null;
        }
        StringTokenizer mToken = new StringTokenizer(rawString, "\r\n");
        StationInfo temp;

        String mName = mToken.nextToken();
        String mCode = mToken.nextToken();
        temp = new StationInfo(mName, mCode);
        return temp;
    }

    private TimePickerDialog.OnTimeSetListener end_Listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //startTime.setText(hourOfDay + minute);
            eTimeFix.setText("종료 시간 : "+ hourOfDay + ":" +minute);



        }
    };
    private TimePickerDialog.OnTimeSetListener start_Listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //startTime.setText(hourOfDay + minute);
            sTimeFix.setText("시작 시간 : " + hourOfDay + ":" + minute);
        }
    };


    @Override // 기차역 조회 Activity 끝나고 실행되는 함수 onStart
    public void onStart() {
        super.onStart();
        if (flag_StartStation == true) // 시작
        {
            onTestReadAndSet(1);
        }
        if (flag_EndStation == true) // 종료
        {
            onTestReadAndSet(2);
        }




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        if(readCheck != -1)
        {
            if(position == 0) // Train
            {
                selected = 0;
                moveTrain.setVisibility(View.VISIBLE);
                moveBus.setVisibility(View.GONE);
                toMeal.setVisibility(View.GONE);
                sleep.setVisibility(View.GONE);
                CheckWeather();
            }else if(position == 1) // Bus
            {
                selected = 1;
                moveTrain.setVisibility(View.GONE);
                moveBus.setVisibility(View.VISIBLE);
                toMeal.setVisibility(View.GONE);
                sleep.setVisibility(View.GONE);
                CheckWeather();
            }else if(position == 2) // Sleep
            {
                selected = 2;
                moveTrain.setVisibility(View.GONE);
                moveBus.setVisibility(View.GONE);
                toMeal.setVisibility(View.GONE);
                sleep.setVisibility(View.VISIBLE);
//                CheckWeather();
            }else // toMeal
            {
                selected = 3;
                moveTrain.setVisibility(View.GONE);
                moveBus.setVisibility(View.GONE);
                toMeal.setVisibility(View.VISIBLE);
                sleep.setVisibility(View.GONE);
//                CheckWeather();
            }
            readCheck = -1;
            return ;
        }

        selected = position;

        if(position == 0) // Train
        {
            moveTrain.setVisibility(View.VISIBLE);
            moveBus.setVisibility(View.GONE);
            toMeal.setVisibility(View.GONE);
            sleep.setVisibility(View.GONE);

            CheckWeather();

        }else if(position == 1) // Bus
        {
            moveTrain.setVisibility(View.GONE);
            moveBus.setVisibility(View.VISIBLE);
            toMeal.setVisibility(View.GONE);
            sleep.setVisibility(View.GONE);

            CheckWeather();
        }else if(position == 2) // Sleep
        {
            moveTrain.setVisibility(View.GONE);
            moveBus.setVisibility(View.GONE);
            toMeal.setVisibility(View.GONE);
            sleep.setVisibility(View.VISIBLE);

            CheckWeather();
        }else // toMeal
        {
            moveTrain.setVisibility(View.GONE);
            moveBus.setVisibility(View.GONE);
            toMeal.setVisibility(View.VISIBLE);
            sleep.setVisibility(View.GONE);

            CheckWeather();
        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
