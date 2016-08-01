package com.demos.notification;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;

import com.demos.R;

/**
 * Created by wangpeng on 16/4/28.
 */
public class MyDialog extends Dialog{
    public MyDialog(Context context) {
        super(context,R.style.my_dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_dialog);
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }

}
