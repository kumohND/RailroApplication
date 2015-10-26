package kr.ac.kumoh.railroApplication.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.ButterKnife;
import kr.ac.kumoh.railroApplication.MainActivity;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.classes.ForeCast;
import kr.ac.kumoh.railroApplication.classes.LatLng;
import kr.ac.kumoh.railroApplication.classes.LocationInform;
import kr.ac.kumoh.railroApplication.classes.RealTimeLocationListener;
import kr.ac.kumoh.railroApplication.classes.WeatherCheck;


public abstract class BaseFragment extends Fragment {
    Toolbar mToolbar;
    RealTimeLocationListener mLocation;
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

    public MainActivity getDrawerActivity() {
        return ((MainActivity) super.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        mContext = view.getContext();
        ButterKnife.inject(this, view);
        mStartInform = new LocationInform();
        mCal = Calendar.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setToolbar(view);
    }


    protected void setToolbar(View view) {
        if (!hasCustomToolbar()) return;
        mToolbar = ButterKnife.findById(view, getToolbarId());
        mToolbar.setTitle(getTitle());
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDrawerActivity().openDrawer();
            }
        });
        if (getTitle() == R.string.plan_list) {

            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // Handle the menu item
                    switch (item.getItemId()) {
                        case R.id.action_show_weather:
                            ImageButton img = ButterKnife.findById(getActivity(), R.id.tab_weather);
                            if(!isOpen){
                                StartLocationService();
                                ForeCast cast  = new ForeCast(mStartInform,null);
                                cast.run();
//                                cast.getForeCast(String.valueOf(mCal.get(Calendar.YEAR)),String.valueOf(mCal.get(Calendar.MONTH) +1) ,
//                                        String.valueOf(mCal.get(Calendar.DAY_OF_MONTH)));
                                mWeather_Data = cast.getStart_Weather();
                                mCheck = new WeatherCheck(mWeather_Data);
                                img.setImageResource(mCheck.WeatherToPicture());
                                //img.setBackgroundResource(mCheck.WeatherToPicture());
                                img.setVisibility(View.VISIBLE);
                                isOpen = true;
                            }else{
                                //StopLocationService();
                                img.setVisibility(View.GONE);
                                isOpen = false;
                            }
                            return true;

                        case R.id.menu_realTime_GPS:
                            StartLocationService();
                            return true;
                    }


                    return false;
                }
            });
            mToolbar.inflateMenu(R.menu.menu_main);
        }


    }

    protected
    @IdRes
    int getToolbarId() {
        return R.id.toolbar;
    }

    public boolean hasCustomToolbar() {
        return false;
    }

    protected
    @StringRes
    int getTitle() {
        return R.string.not_title_set;
    }

    protected abstract
    @LayoutRes
    int getLayout();

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
