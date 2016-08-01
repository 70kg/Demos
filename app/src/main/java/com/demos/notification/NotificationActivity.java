package com.demos.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.widget.Button;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;
import com.socks.library.KLog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wangpeng on 16/4/18.
 */
public class NotificationActivity extends ToolBarActivity {
    @Override
    public int getLayout() {
        return R.layout.notification;
    }

    @Bind(R.id.btn_new_notification)
    Button mButton;

    @OnClick(R.id.btn_new_notification)
    public void _onclick() {
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("My App")
                .setContentText("hello world")
                .setDeleteIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

//        Intent intent1 = new Intent("com.meizu.safe.security.SHOW_APPSEC");
//        intent1.addCategory(Intent.CATEGORY_DEFAULT);
//        intent1.putExtra("packageName", BuildConfig.APPLICATION_ID);
//        try {
//            startActivity(intent1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        KLog.e(android.os.Build.MANUFACTURER);

        Intent intent2 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", getPackageName(), null));
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager packageManager = getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
//            startActivity(intent2);
        }

        MyDialog dialog = new MyDialog(this);
        dialog.show();


    }
}
