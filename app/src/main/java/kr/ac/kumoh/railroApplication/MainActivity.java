package kr.ac.kumoh.railroApplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.kakao.auth.ErrorResult;
import com.kakao.auth.Session;
import com.kakao.auth.ISessionCallback;
import com.kakao.kakaostory.KakaoStoryService;
import com.kakao.kakaostory.callback.StoryResponseCallback;
import com.kakao.kakaostory.response.ProfileResponse;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.AdditionalService.NoticeActivity;
import kr.ac.kumoh.railroApplication.KakaoService.RegisterAppActivity;
import kr.ac.kumoh.railroApplication.classes.RealTimeLocationListener;
import kr.ac.kumoh.railroApplication.classes.UseDB;
import kr.ac.kumoh.railroApplication.fragments.BaseFragment;
import kr.ac.kumoh.railroApplication.fragments.DigitalFootprintFragment;
import kr.ac.kumoh.railroApplication.fragments.FloatingActionButtonFragment;
import kr.ac.kumoh.railroApplication.fragments.HomeFragment;
import kr.ac.kumoh.railroApplication.fragments.MyTripListFragment;
import kr.ac.kumoh.railroApplication.fragments.TripInfoFragment;
import kr.ac.kumoh.railroApplication.util.GlobalApplication;
import kr.ac.kumoh.railroApplication.util.LogUtils;
import kr.ac.kumoh.railroApplication.util.Navigator;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.navigation_view)
    NavigationView mNavigationView;

    @InjectView(R.id.drawer_header)
    RelativeLayout mDrawerHeader;


    static LocationManager mManager;
    static RealTimeLocationListener mRTLocation;
    private static Navigator mNavigator;
    private Toolbar mToolbar;

    LoginButton mLoginButton;
    TextView mUserID;
    ImageView mUserImg;
    ImageView mSetting;
    String mUserProfileImage;


    private
    @IdRes
    int mCurrentMenuItem;

    private SessionCallback mCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


      /*  AdView adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("3AB81DDBDEC96ABB")
                .addTestDevice("3D04B52E387484C9")
                .addTestDevice("3A8318B9B9390A6A")
                .build();

 //       adView.loadAd(adRequest);*/

        mCallback = new SessionCallback();
        UseDB mDB = new UseDB(this);
//        mDB.DeleteTable();
        Session.getCurrentSession().addCallback(mCallback);
        Session.getCurrentSession().checkAndImplicitOpen();


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setupToolbar();
        setupNavDrawer();
        initNavigator();
        // mCurrentMenuItem = R.id.standard_app_bar_menu_item;32
        mCurrentMenuItem = R.id.toolbar_flexible_space_with_image;
        // setNewRootFragment(dddndardAppBarFragment.newInstance());
        setNewRootFragment(HomeFragment.newInstance());

        mSetting = ButterKnife.findById(this, R.id.navigation_settings);
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "IMG CLICK", Toast.LENGTH_SHORT).show();
            }
        });

        mLoginButton = (LoginButton) ButterKnife.findById(this, R.id.com_kakao_login);

        mDrawerHeader.setBackgroundColor(R.drawable.ic_cast_dark);
        mUserID = ButterKnife.findById(this, R.id.drawer_user_name);
        mUserImg = ButterKnife.findById(this, R.id.profile_image);

    }


    @Override
    protected void onResume() {
        super.onResume();

        GlobalApplication.setCurrentActivity(this);


    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            redirectSignupActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(mCallback);
    }


    protected void redirectSignupActivity() {

        if (Session.getCurrentSession().isOpened()) {
            final Intent intent = new Intent(this, RegisterAppActivity.class);
            startActivity(intent);
            requestMe();
            readProfile();
            // mLoginButton.setAlpha(0);
        }

        //finish();


        // Session.getCurrentSession().open(AuthType.KAKAO_ACCOUNT, this);

    }

    private void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
               // redirectLoginActivity();
                Toast.makeText(getApplicationContext(), "세션이 종료되었습니다.\n다시 로그인해주세요", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Logger.d("UserProfile : " + userProfile);
                //     redirectMainActivity();

                mLoginButton.setVisibility(View.GONE);
                mUserProfileImage = userProfile.getProfileImagePath();
                mUserImg.setImageDrawable(LoadImageFromWebOperations(mUserProfileImage));
                mUserID.setTextSize(20);
                mUserID.setText(userProfile.getNickname());
            }

            @Override
            public void onNotSignedUp() {
                //   showSignup();
            }
        });
    }

    public void readProfile() {
        KakaoStoryService.requestProfile(new KakaoStoryResponseCallback<ProfileResponse>() {
            @Override
            public void onSuccess(ProfileResponse profile) {
                Logger.d("succeeded to get story profile");
                String mUserthumbnailImagePath = profile.getBgImageURL();
                mDrawerHeader.setBackground(LoadImageFromWebOperations(mUserthumbnailImagePath));

            }
        });
    }

    private abstract class KakaoStoryResponseCallback<T> extends StoryResponseCallback<T> {

        @Override
        public void onNotKakaoStoryUser() {
            Logger.d("not KakaoStory user");
            mDrawerHeader.setBackground(LoadImageFromWebOperations(mUserProfileImage));
        }

        @Override
        public void onFailure(ErrorResult errorResult) {
            String error ="KakaoStoryResponseCallback : failure : " + errorResult;
            Logger.e(error);
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onSessionClosed(ErrorResult errorResult) {
            // redirectLoginActivity();
            Toast.makeText(getApplicationContext(), "세션이 종료되었습니다.\n다시 로그인해주세요", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNotSignedUp() {
            //  redirectSignupActivity();
        }
    }


    public static Drawable LoadImageFromWebOperations(String url) {
        Drawable drawable = null;

        try {
            InputStream inputStream = new URL(url).openStream();
            drawable = Drawable.createFromStream(inputStream, null);
            inputStream.close();
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        }

        return drawable;

    }


    //html 주소 string을 받아 해당 주소의 이미지를 화면에 띄움
    BitmapDrawable downloadFile(String fileUrl) {
        URL myFileUrl = null; // URL 타입의 myFileUrl을  NULL로 초기화 시켜줍니다.
        Bitmap mBitmap = null;

        try {
            myFileUrl = new URL(fileUrl); //  파라미터로 넘어온 Url을 myFileUrl에 대입합니다.
        } catch (MalformedURLException e) // 예외처리를 해줍니다.
        {
            // Todo Auto-generated catch block
            e.printStackTrace();
        }
        try {
            if (fileUrl.equals("")) return null;

            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            int length = conn.getContentLength(); // 받아온 컨텐츠의 길이를 length 변수에 저장합니다.
            InputStream is = conn.getInputStream(); // InputStream is 변수에 받아온 InputStream을 저장합니다.

            mBitmap = BitmapFactory.decodeStream(is); // 받아온 이미지를 bmImg에 넣어둡니다.
        } catch (IOException e) // 예외처리를 해줍니다.
        {
            e.printStackTrace();
        }


        return new BitmapDrawable(mBitmap);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /*
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
           if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }*/
    private void initNavigator() {
        if (mNavigator != null) return;
        mNavigator = new Navigator(getSupportFragmentManager(), R.id.container);
    }

    private void setNewRootFragment(BaseFragment fragment) {
        if (fragment.hasCustomToolbar()) {
            hideActionBar();
        } else {
            showActionBar();
        }
        mNavigator.setRootFragment(fragment);
        mDrawerLayout.closeDrawers();
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
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.hide();
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.show();
    }

    private void setupNavDrawer() {
        if (mDrawerLayout == null) {
            LogUtils.LOGE(this, "mDrawerLayout is null - Can not setup the NavDrawer! Have you set the android.support.v7.widget.DrawerLayout?");
            return;
        }
        mNavigationView.setNavigationItemSelectedListener(this);
        //LOGD(this, "setup setupNavDrawer");
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        @IdRes int id = menuItem.getItemId();
        if (id == mCurrentMenuItem) {
            mDrawerLayout.closeDrawers();
            return false;
        }
        switch (id) {
            case R.id.home_menu_item:
                setNewRootFragment(HomeFragment.newInstance());
                break;

            case R.id.my_trip_menu_item:
                setNewRootFragment(MyTripListFragment.newInstance(getApplicationContext()));
                break;

          /*  case R.id.plan_list_menu_item:
                setNewRootFragment(PlanListTabFragment.newInstance());
                break;*/

            case R.id.plan_info_menu_item:
                setNewRootFragment(TripInfoFragment.newInstance());
                break;

            case R.id.digital_footprint_menu_item:
//                setNewRootFragment(DigitalFootprintFragment.newInstance());
                setNewRootFragment(DigitalFootprintFragment.newInstance());
                break;

            case R.id.share_menu_item:
                setNewRootFragment(FloatingActionButtonFragment.newInstance());
                break;

            case R.id.drawer_notice:
                Intent intent = new Intent(this, NoticeActivity.class);
                startActivity(intent);
                break;

            case R.id.drawer_service:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "rachel0211s@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "불만사항 메일입니다.");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "기종 :"+"\n"+ "오류사항(자세히 기재 해주세요) :"+"\n");
                startActivity(Intent.createChooser(emailIntent, "내일이 고객센터 메일"));
                break;


        }
        mCurrentMenuItem = id;
        menuItem.setChecked(true);
        return false;
    }

    @Override
    public void finish() {
        mNavigator = null;
        super.finish();
    }
}
