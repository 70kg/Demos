package com.demos.RequestQueue;

import android.os.Bundle;
import android.widget.TextView;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;
import com.socks.library.KLog;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Scanner;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mr_Wrong on 16/3/4.
 * 一个异步的消息队列
 */
public class QueueActivity extends ToolBarActivity {
    @Bind(R.id.tv_queue)
    TextView tvQueue;
    RequestQueue queue = RequestQueue.getInstance();
    int index;

    @OnClick(R.id.btn_add_request)
    void _add() {
//        Resquestfor70kg<String> resquestfor70kg = new Resquestfor70kg<>("消息" + index++, new StringCallback(tvQueue));
//        Resquestfor70kg<String> resquestfor70kg = new Resquestfor70kg<>("消息" + index++, mStringResult);
        Resquestfor70kg<String> resquestfor70kg = new Resquestfor70kg<>("消息" + index++, new Result<String>() {
            @Override
            public void onResponse(String response) {
                tvQueue.setText(response);
            }
        });
        queue.add(resquestfor70kg);

    }

    Result<String> mStringResult = new Result<String>() {
        @Override
        public void onResponse(String response) {
            tvQueue.setText(response);
        }
    };

    static class StringCallback extends Result<String> {
        final WeakReference<TextView> mTextViewWeakReference;

        StringCallback(TextView textview) {
            mTextViewWeakReference = new WeakReference<>(textview);
        }

        @Override
        public void onResponse(String response) {
            mTextViewWeakReference.get().setText(response);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue.start();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.baidu.com").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                KLog.e(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                Scanner scanner = new Scanner(inputStream, "UTF-8");
                String text = scanner.useDelimiter("\\A").next();
            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.queue;
    }

}
