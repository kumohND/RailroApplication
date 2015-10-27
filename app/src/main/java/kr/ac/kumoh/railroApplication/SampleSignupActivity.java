package kr.ac.kumoh.railroApplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.kakao.usermgmt.LoginButton;

/**
 * Created by sj on 2015-10-27.
 */
public class SampleSignupActivity extends ActionBarActivity{

    LoginButton mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "DDDDDDDDdd",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
