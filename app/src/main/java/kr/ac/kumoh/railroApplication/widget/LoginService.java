package kr.ac.kumoh.railroApplication.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.AuthType;
import com.kakao.auth.ErrorResult;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.request.SignupRequest;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.StoryProtocol;
import com.kakao.util.helper.TalkProtocol;
import com.kakao.util.helper.log.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.ac.kumoh.railroApplication.MainActivity;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.adapters.KakaoSDKAdapter;
import kr.ac.kumoh.railroApplication.util.GlobalApplication;

/**
 * Created by sj on 2015-10-28.
 */
public class LoginService extends Activity {

    private SessionCallback callback;


    LoginButton mLoginButton;
    Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_service);



  //      GlobalApplication.setCurrentActivity(this);
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

        Toast.makeText(this, "LOGIN START", Toast.LENGTH_SHORT).show();

      //  mButton = (Button)findViewById(R.id.close);
       /* mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

*/
       // finish();
    }




    private static class Item {
        public final int textId;
        public final int icon;
        public final AuthType authType;
        public Item(final int textId, final Integer icon, final AuthType authType) {
            this.textId = textId;
            this.icon = icon;
            this.authType = authType;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        GlobalApplication.setCurrentActivity(this);
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
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            redirectSignupActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
        }
    }

    protected void redirectSignupActivity() {
       // final Intent intent = new Intent(this, SignUpService.class);
        //startActivity(intent);

      // finish();
    }


}
