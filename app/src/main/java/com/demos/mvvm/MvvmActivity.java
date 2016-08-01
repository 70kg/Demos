package com.demos.mvvm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;
import com.demos.mvvm.mvvmfragment.MvvmFragment;
import com.demos.mvvm.mycase.HomeFragment;
import com.demos.mvvm.mycase.LiveFragment;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wangpeng on 16/5/18.
 */
public class MvvmActivity extends ToolBarActivity {
    @Override
    public int getLayout() {
        return R.layout.mvvm_activity;
    }

    int oldIndex;

    @Bind(R.id.btn_1)
    Button button1;
    @Bind(R.id.btn_2)
    Button button2;
    List<MvvmFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments.add(new HomeFragment());
        fragments.add(new LiveFragment());

        onBottomBarClickListener onBottomBarClickListener = new onBottomBarClickListener();
        button1.setOnClickListener(onBottomBarClickListener);
        button2.setOnClickListener(onBottomBarClickListener);

        replaceFragment(fragments.get(0));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
//        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    class onBottomBarClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int index = Integer.parseInt((String) v.getTag());
            if (index == oldIndex)
                return;
            replaceFragment(fragments.get(index));
            oldIndex = index;
        }
    }
}
