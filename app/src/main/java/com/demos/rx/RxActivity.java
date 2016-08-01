package com.demos.rx;

import android.os.Bundle;

import com.demos.main.base.ToolBarActivity;
import com.socks.library.KLog;

import java.util.Arrays;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Mr_Wrong on 16/3/30.
 */
public class RxActivity extends ToolBarActivity {
    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable.from(Arrays.asList(1, 2, 3)).skipWhile(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer == 2;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                KLog.e(integer);
            }
        });

    }
}
