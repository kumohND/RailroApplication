package kr.ac.kumoh.railroApplication.classes;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.kakao.ApprovalType;
import com.kakao.AuthType;

/**
 * Created by sj on 2015-10-26.
 */



public class GlobalApplication extends Application{
/*
    private static class KakaoSDKAdapter extends KakaoAdapter {
        /**
         * Session Config에 대해서는 default값들이 존재한다.
         * 필요한 상황에서만 override해서 사용하면 됨.
         * @return Session의 설정값.


        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Activity getTopActivity() {
                    return GlobalApplication.getCurrentActivity();
                }

                @Override
                public Context getApplicationContext() {
                    return GlobalApplication.getGlobalApplicationContext();
                }
            };
        }
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSDK.init(new KakaoSDKAdapter());


    }
*/

}
