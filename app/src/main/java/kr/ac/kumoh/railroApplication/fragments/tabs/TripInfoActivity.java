package kr.ac.kumoh.railroApplication.fragments.tabs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

public class TripInfoActivity extends Activity {

    //맨위에 star
    ImageView mStarImg1;
    ImageView mStarImg2;
    ImageView mStarImg3;
    ImageView mStarImg4;
    ImageView mStarImg5;

    //Title
    TextView mTitle;

    //버튼(페이지)
    ImageButton btn1;
    ImageButton btn2;
    ImageButton btn3;
    ImageButton btn4;
    ImageButton btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trip_info);
        // ButterKnife.inject(this);

        //star
        mStarImg1 = (ImageView)findViewById(R.id.star_5);
        mStarImg2 = (ImageView)findViewById(R.id.star_4);
        mStarImg3 = (ImageView)findViewById(R.id.star_3);
        mStarImg4 = (ImageView)findViewById(R.id.star_2);
        mStarImg5 = (ImageView)findViewById(R.id.star_1);

        //title
        mTitle = (TextView)findViewById(R.id.title_info);

        Intent intent = getIntent();

        int contentid = intent.getExtras().getInt("contentid");
        int star = intent.getExtras().getInt("star");
        String title = intent.getExtras().getString("title");
        //title
        mTitle.setText(title);

        //페이지 버튼
        btn1 = (ImageButton)findViewById(R.id.info1);
        btn2 = (ImageButton)findViewById(R.id.info2);
        btn3 = (ImageButton)findViewById(R.id.info3);
        btn4 = (ImageButton)findViewById(R.id.info4);
        btn5 = (ImageButton)findViewById(R.id.info5);
    }

}
