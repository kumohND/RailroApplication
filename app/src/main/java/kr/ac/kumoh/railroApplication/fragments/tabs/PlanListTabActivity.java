package kr.ac.kumoh.railroApplication.fragments.tabs;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.adapters.PagerAdapter;
import kr.ac.kumoh.railroApplication.classes.ForeCast;
import kr.ac.kumoh.railroApplication.classes.LatLng;
import kr.ac.kumoh.railroApplication.classes.LocationInform;
import kr.ac.kumoh.railroApplication.classes.RealTimeLocationListener;
import kr.ac.kumoh.railroApplication.classes.UseDB;
import kr.ac.kumoh.railroApplication.classes.WeatherCheck;

/**
 * Created by sj on 2015-10-23.
 */
public class PlanListTabActivity  extends ActionBarActivity{

    @InjectView(R.id.tab_layout)
    TabLayout mTabLayout;

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;

    @InjectView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.tab_weather)
    ImageView mTabWeather;

    @InjectView(R.id.share_menu_item)
    FloatingActionButton mFab;


    Context mContext;
    static LocationManager mManager;
    static RealTimeLocationListener mRTLocation;
    boolean isGPSEnabled;
    boolean isOpen = false;
    double latitude;
    double longitude;
    LocationInform mStartInform;
    LatLng mTempLocation;
    Calendar mCal;

    WeatherCheck mCheck;
    ContentValues mWeather_Data;
    int index;
    String title;
    String textName;
    int duration;
    UseDB mDB;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list_tab);
        ButterKnife.inject(this);
        mContext = getApplicationContext();
        mStartInform = new LocationInform();
        mCal = Calendar.getInstance();

        Intent intent = getIntent();
        index = intent.getIntExtra("index",0); //db 저장된 값을 받아옴
        onIntTripList();
        GetContentValue();
        isFirstTextRead();
        title = intent.getStringExtra("title");
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchPlaceActivity.class);
                startActivity(intent);
            }
        });
        setupToolbar();
        setupTabTextColor();
        setupViewPager();
        ChangeViewPagerIdToText(0);
    }
    public void GetContentValue()
    {
        mDB = new UseDB(mContext);
        ContentValues mValue = mDB.Read(index);
        String temp1 = String.valueOf(mValue.get("index_id"));
        String temp2 = String.valueOf(mValue.get("dbTextName"));
        textName = temp1 + temp2;
        duration = Integer.valueOf(String.valueOf(mValue.get("duration")));
    }


    private void isFirstTextRead()
    {
        File file;
        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext";
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(path + File.separator + textName + ".txt");
        if(!file.exists())
        {
            try {
                BufferedWriter buw = new BufferedWriter(new FileWriter(file));
                buw.write(String.valueOf(duration));
                buw.newLine();
                for (int i = 0; i < duration; i++) {
                    buw.write(String.valueOf(i));
                    buw.newLine();
                    for (int j = 1; j < 25; j++) { // 24시간 간격만듬
                        buw.write("time "+ j + "%&#"); // time 1/
                        buw.newLine();
                    }
                }
                buw.close();
            } catch (IOException e) {

            }
        }
    }


    private void onIntTripList() { // 임시로, 기차역 출발 , 도착역 씀
        File file;
        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext";
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + File.separator + "temp_index" + ".txt");
        try {
            BufferedWriter buw = new BufferedWriter(new FileWriter(file));
            buw.write(String.valueOf(index));
            buw.newLine();
            buw.close();
        } catch (IOException e) {

        }
    }
    private void ChangeViewPagerIdToText(int position)
    {
        File file;
        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext";
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + File.separator + "view_Pager" + ".txt");
        try {
            BufferedWriter buw = new BufferedWriter(new FileWriter(file));
            buw.write(String.valueOf(position));
            buw.newLine();
            buw.close();
        } catch (IOException e) {

        }
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
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_show_weather:
                if (!isOpen) {
                    StartLocationService();
                    ForeCast cast = new ForeCast(mStartInform, null);
                    cast.run();
//                                cast.getForeCast(String.valueOf(mCal.get(Calendar.YEAR)),String.valueOf(mCal.get(Calendar.MONTH) +1) ,
//                                        String.valueOf(mCal.get(Calendar.DAY_OF_MONTH)));
                    mWeather_Data = cast.getStart_Weather();
                    mCheck = new WeatherCheck(mWeather_Data);
                    mTabWeather.setImageResource(mCheck.WeatherToPicture());
                    //img.setBackgroundResource(mCheck.WeatherToPicture());
                    mTabWeather.setVisibility(View.VISIBLE);
                    isOpen = true;
                } else {
                    mTabWeather.setVisibility(View.GONE);
                    isOpen = false;
                }
                return true;

            case R.id.menu_realTime_GPS:
                StartLocationService();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupTabTextColor() {
        int tabTextColor = getResources().getColor(R.color.titleTextColor);
        mTabLayout.setTabTextColors(tabTextColor, tabTextColor);
    }

    private void setupViewPager() {
        //You could use the normal supportFragmentManger if you like
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), getApplicationContext(),index);
        mViewPager.setAdapter(pagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 여기서 변경점 저장
                ChangeViewPagerIdToText(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTabLayout.setupWithViewPager(mViewPager);//this is the new nice thing ;D
    }

    public void StartLocationService() {
        mRTLocation = new RealTimeLocationListener(mContext);

        if (mRTLocation.isGetLocation()) {
            mTempLocation = new LatLng(mRTLocation.getLatitude(),mRTLocation.getLongitude());
            mStartInform.latlng = mTempLocation;
        }
//        long minTime = 1000;
//        float minDistance = 0;
//
////        isGPSEnabled = mManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
////        if(isGPSEnabled != false) {
//            mManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
//            mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, mRTLocation);
//        //}
    }

    public void StopLocationService() {
        mManager.removeUpdates(mRTLocation);
    }
}
