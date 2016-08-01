package com.demos.douban;

import android.widget.Button;
import android.widget.TextView;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by Mr_Wrong on 16/3/17.
 */
public class DouBanMovieActivity extends ToolBarActivity {
    @Override
    public int getLayout() {
        return R.layout.douban;
    }

    @Bind(R.id.click_me_BN)
    Button clickMeBN;
    @Bind(R.id.result_TV)
    TextView resultTV;

    @OnClick(R.id.click_me_BN)
    public void onClick() {
        getMovie();
    }

    private void setText(MovieEntity entity) {
        resultTV.setText(entity.getTitle());
    }


    Subscriber<MovieEntity> mSubscriber;

    private void getMovie() {
//        mSubscriber = new Subscriber<MovieEntity>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(MovieEntity movieEntity) {
//                setText(movieEntity);
//            }
//        };

//        HttpMethods.getInstance().getTopMovie(mSubscriber, 0, 10);


//        mSubscriber.unsubscribe();取消请求
//        HttpMethods.getInstance().getTopMovie1(new Subscriber<HttpResult<List<SubjectsEntity>>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                KLog.e(e);
//            }
//
//            @Override
//            public void onNext(HttpResult<List<SubjectsEntity>> listHttpResult) {
//                for (SubjectsEntity entity : listHttpResult.getSubjects())
//                    KLog.e(entity.getTitle());
//            }
//        }, 0, 10);

        //这里已经统一处理完code的情况
//        HttpMethods.getInstance().getTopMovie2(new Subscriber<List<SubjectsEntity>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                KLog.e(e);
//            }
//
//            @Override
//            public void onNext(List<SubjectsEntity> list) {
//                for (SubjectsEntity entity : list)
//                    KLog.e(entity.getTitle());
//
//            }
//        }, 0, 10);

        final StringBuilder builder = new StringBuilder();
        ProgressSubscriber<List<SubjectsEntity>> progressSubscriber = new ProgressSubscriber<>(new SubscriberOnNextListener<List<SubjectsEntity>>() {
            @Override
            public void onNext(List<SubjectsEntity> list) {

                for (SubjectsEntity entity : list) {
                    builder.append(entity.getTitle() + "\n");
                }
                resultTV.setText(builder.toString());
            }
        }, this);

        HttpMethods.getInstance().getTopMovie2(progressSubscriber, 0, 10);

    }



}
