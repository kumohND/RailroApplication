package kr.ac.kumoh.railroApplication.classes;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import kr.ac.kumoh.railroApplication.R;

/**
 * Created by Woocha on 2015-09-05.
 */
public class ProgressDialog extends Dialog {
    public ProgressDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_dialog_layout);

    }
}
