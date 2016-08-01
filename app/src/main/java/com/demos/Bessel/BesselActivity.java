package com.demos.Bessel;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Mr_Wrong on 16/3/4.
 */
public class BesselActivity extends ToolBarActivity {
    @Bind(R.id.test_bessel)
    TestBesselView testBessel;

    @OnClick(R.id.btn_bssel)
    void _click() {
        testBessel.startAnimator(3000);
    }

    @Override
    public int getLayout() {
        return R.layout.test_bessel;
    }

}
