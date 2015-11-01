package kr.ac.kumoh.railroApplication.KakaoService;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.kakao.auth.ErrorCode;
import com.kakao.auth.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

/**
 * Created by sj on 2015-10-30.
 */
public class SignUpService extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }

    /**
     * 자동가입앱인 경우는 가입안된 유저가 나오는 것은 에러 상황.
     */
    protected void showSignup() {
        Logger.d("not registered user");
//        redirectLoginActivity();
        String message = "not registered user.\nYou should signup at UserManagememt menu.";
        Dialog dialog = new DialogBuilder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
        dialog.show();
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                if (errorResult.getErrorCode() == ErrorCode.CLIENT_ERROR_CODE) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                   // redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {

                //redirectLoginActivity();
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Logger.d("UserProfile : " + userProfile);
               // redirectMainActivity();
            }

            @Override
            public void onNotSignedUp() {
                showSignup();
            }
        });
    }

}
