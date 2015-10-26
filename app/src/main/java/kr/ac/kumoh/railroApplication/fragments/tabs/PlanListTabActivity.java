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

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.adapters.PagerAdapter;
import kr.ac.kumoh.railroApplication.classes.ForeCast;
import kr.ac.kumoh.railroApplication.classes.LatLng;
import kr.ac.kumoh.railroApplication.classes.LocationInform;
import kr.ac.kumoh.railroApplication.classes.RealTimeLocationListener;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list_tab);
        ButterKnife.inject(this);
        mContext = getApplicationContext();
        mStartInform = new LocationInform();
        mCal = Calendar.getInstance();

        Intent intent = getIntent();
        index = intent.getIntExtra("index",0);
        index++;
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
                    //StopLocationService();
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
