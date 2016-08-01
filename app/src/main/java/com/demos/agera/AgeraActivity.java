package com.demos.agera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;
import com.google.android.agera.Updatable;
import com.socks.library.KLog;

import butterknife.Bind;

/**
 * Created by wangpeng on 16/5/23.
 */

public class AgeraActivity extends ToolBarActivity implements Updatable {
    @Bind(R.id.show)
    TextView show;
    @Bind(R.id.trigger)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AgeraBus.addUpdatable(this);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AgeraActivity.this, AgeraSecondActivity.class));
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.agera;
    }

    @Override
    public void update() {
        if (AgeraBus.getData() instanceof AgeraModel) {
            AgeraModel model = (AgeraModel) AgeraBus.getData();
            KLog.e(model.getValue());
        } else {
            KLog.e(AgeraBus.getData());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AgeraBus.removeUpdatable(this);
    }
}
