package kr.ac.kumoh.railroApplication.fragments;

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

import butterknife.ButterKnife;
import kr.ac.kumoh.railroApplication.MainActivity;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.classes.RealTimeLocationListener;


public abstract class BaseFragment extends Fragment {
    Toolbar mToolbar;
    RealTimeLocationListener mLocation;
    Context mContext;
    static LocationManager mManager;
    static RealTimeLocationListener mRTLocation;
    boolean isGPSEnabled;
    boolean isOpen = false;

    public MainActivity getDrawerActivity() {
        return ((MainActivity) super.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        mContext = view.getContext();
        ButterKnife.inject(this, view);
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
                                img.setVisibility(View.VISIBLE);
                                isOpen = true;
                            }else{
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
            double latitude = mRTLocation.getLatitude();
            double longitude = mRTLocation.getLongitude();
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
