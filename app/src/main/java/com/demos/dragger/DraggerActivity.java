package com.demos.dragger;

import android.os.Bundle;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;
import com.socks.library.KLog;

import javax.inject.Inject;

/**
 * Created by Mr_Wrong on 16/3/6.
 */
public class DraggerActivity extends ToolBarActivity {
    private ActivityComponent mActivityComponent;
    @Inject
    UserModel userModel;
    @Inject
    Bean mBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dragger);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule())
                .beanModuel(new BeanModuel())
                .build();
        mActivityComponent.inject(this);
        KLog.e(mBean.id);
//        ((TextView) findViewById(R.id.user_desc_line)).setText(userModel.id + "\n" + userModel.name + "\n" + userModel.gender);

    }

    @Override
    public int getLayout() {
        return R.layout.dragger;
    }
}
