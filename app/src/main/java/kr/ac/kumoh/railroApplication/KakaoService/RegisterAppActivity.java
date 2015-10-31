package kr.ac.kumoh.railroApplication.KakaoService;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import java.util.HashMap;
import java.util.Map;

import kr.ac.kumoh.railroApplication.R;

/**
 * Created by sj on 2015-10-27.
 */
public class RegisterAppActivity extends ActionBarActivity {


    private Button mRegOK;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_app);

        mRegOK = (Button) findViewById(R.id.reg_OK);
        mRegOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestUpdateProfile();
                finish();
            }
        });
    }
    private abstract class UsermgmtResponseCallback<T> extends ApiResponseCallback<T> {
        @Override
        public void onNotSignedUp() {

            //redirectSignupActivity();
        }

        @Override
        public void onFailure(ErrorResult errorResult) {
            String message = "failed to get user info. msg=" + errorResult;
            Logger.e(message);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onSessionClosed(ErrorResult errorResult) {
            //redirectLoginActivity();
        }
    }

    private void requestUpdateProfile() {
        final Map<String, String> properties = new HashMap<String, String>();
        properties.put("gender", "여");
        properties.put("age", "33");

        UserManagement.requestUpdateProfile(new UsermgmtResponseCallback<Long>() {
            @Override
            public void onSuccess(Long userId) {
              /*  userProfile.updateUserProfile(properties);
                if (userProfile != null) {
                    userProfile.saveUserToCache();
                }
                Logger.d("succeeded to update user profile" + userProfile);*/
                Toast.makeText(getApplicationContext(), "LOGIN!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotSignedUp() {
               // redirectSignupActivity(self);
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.e(message);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                //redirectLoginActivity(self);
            }

        }, properties);
    }
}
