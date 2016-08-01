package com.demos.flux.actions;

import com.demos.flux.dispatcher.Dispatcher;
import com.demos.flux.model.GitHubUser;
import com.demos.flux.request.GitHubApiUtils;
import com.socks.library.KLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Mr_Wrong on 16/3/25.
 * 网络请求在actioncreateer里面进行
 */
public class ActionsCreater {
    private static ActionsCreater creater;
    private Dispatcher mDispatcher;

    public ActionsCreater(Dispatcher dispatcher) {
        mDispatcher = dispatcher;
    }

    public static ActionsCreater getInstance(Dispatcher Dispatcher) {
        if (creater == null) {
            synchronized (ActionsCreater.class) {
                if (creater == null) {
                    creater = new ActionsCreater(Dispatcher);
                }
            }
        }
        return creater;
    }

//---

    //这里进行网络的请求操作
    public void sendMessage(String message) {
        mDispatcher.dispatch(new MessageAction(MessageAction.ACTION_MESSAGE, message));
    }

    public void queryUser(String name) {
        GitHubApiUtils.getInstance().getGitHubApi().user(name)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GitHubUser>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e);
                    }

                    @Override
                    public void onNext(GitHubUser gitHubUser) {
                        mDispatcher.dispatch(new UserAction(UserAction.MYACTION, gitHubUser));
                    }
                });
    }


}
