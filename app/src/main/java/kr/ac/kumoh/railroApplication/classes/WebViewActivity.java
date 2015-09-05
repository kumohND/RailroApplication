package kr.ac.kumoh.railroApplication.classes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import kr.ac.kumoh.railroApplication.R;

/**
 * Created by Woocha on 2015-09-05.
 */
public class WebViewActivity extends Activity {
    LocationInform mStartInform;
    LocationInform mEndInform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity_layout);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

//        mStartInform = new LocationInform();
//        mEndInform = new LocationInform();

//        mStartInform = bundle.getParcelable("start");
//        mEndInform = bundle.getParcelable("end");

        String url = bundle.getString("url");
        WebView webView = (WebView) findViewById(R.id.rootwebView);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClientClass());
        webView.loadUrl(url);



    }
    public class WebViewClientClass extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
//        finish();
        super.onResume();

    }
}
