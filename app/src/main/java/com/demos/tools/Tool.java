package com.demos.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by Mr_Wrong on 16/2/2.
 */
public class Tool {
    private Context mContext;

    public Tool(Context context) {
        mContext = context;
    }

    public void showToast(Object msg) {
        if (msg == null) {
            return;
        }
        Toast.makeText(mContext, msg.toString(), Toast.LENGTH_SHORT).show();
    }

    public void start(Class<?> c) {
        Intent intent = new Intent(mContext, c);
        intent.putExtra(ToolBarActivity.TITLE, c.getSimpleName());
        mContext.startActivity(intent);
    }

    public void showPic(ImageView imageView, String url) {
        if (url != null)
            Picasso.with(mContext)
                    .load(url)
                    .placeholder(R.drawable.image_holder)
                    .config(Bitmap.Config.RGB_565)
                    .into(imageView);
    }

    public <T extends View> T find(int id) {
        return (T) ((Activity) mContext).findViewById(id);
    }

    public <T extends View> T find(View parentView, int id) {
        return (T) parentView.findViewById(id);
    }
}