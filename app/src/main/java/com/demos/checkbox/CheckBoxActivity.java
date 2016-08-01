package com.demos.checkbox;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;

/**
 * Created by Mr_Wrong on 16/2/23.
 */
public class CheckBoxActivity extends ToolBarActivity {
    @Override
    public int getLayout() {
        return R.layout.checkbox;
    }

    String[] filter_type_strs = {"音乐", "书籍", "电影"};
    CheckBox cbx;
    private boolean lockState = false;
    int current_filter_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cbx = (CheckBox) findViewById(R.id.cbx);
        cbx.setText(filter_type_strs[current_filter_type]);
        cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (lockState) return;
                try {
                    if (isChecked) {
                        PopupWindow pw = new FilterLinePw(buttonView.getContext(), cbx, filter_type_strs);
                        pw.showAsDropDown(cbx);
                    } else {
                        //只改变Mode
                        Integer pos = (Integer) buttonView.getTag();
                        if (pos == null || pos == -1) return;
                        current_filter_type = pos;

                        Toast.makeText(CheckBoxActivity.this, "搜索" + filter_type_strs[current_filter_type], Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException e) {
                    lockState = true;
                    buttonView.setChecked(!isChecked);
                    lockState = false;
                }
            }
        });
    }
}
