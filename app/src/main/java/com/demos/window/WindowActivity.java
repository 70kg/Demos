package com.demos.window;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;

/**
 * Created by Mr_Wrong on 16/2/23.
 */
public class WindowActivity extends ToolBarActivity {
    @Override
    public int getLayout() {
        return R.layout.window;
    }

    Button mUpdateButton;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUpdateButton = mTool.find(R.id.btn_update_window);
        mEditText = mTool.find(R.id.etv_alpha);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float alpha = Float.parseFloat(mEditText.getText().toString());
                Intent intent = new Intent(WindowActivity.this, WindowService.class);
                intent.putExtra("alpha", alpha);
                startService(intent);



            }
        });

    }
}
