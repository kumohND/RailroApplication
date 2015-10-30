package kr.ac.kumoh.railroApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import kr.ac.kumoh.railroApplication.widget.LoginService;

/**
 * Created by sj on 2015-10-30.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       // setContentView(R.layout.activity_login_service);

        findViewById(R.id.splash).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginService.class));
                finish();

            }
        }, 500);


    }


}
