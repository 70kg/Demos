package com.demos.faceabstrct;

import android.os.Bundle;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;

import butterknife.Bind;

/**
 * Created by Mr_Wrong on 16/3/11.
 */
public class FaceAbsActivity extends ToolBarActivity {
    @Bind(R.id.layout)
    Layout layout;

    @Override
    public int getLayout() {
        return R.layout.face_abs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bean bean = new Bean();
        bean.setContent("内容");
        bean.setName("70kg");
        layout.setPost(bean);
    }
}
