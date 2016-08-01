package com.demos.mvvm.mycase;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.demos.App;
import com.demos.mvvm.mvvmViewModel.MvvmBindingViewModel;
import com.demos.mvvm.mycase.model.LiveShow;
import com.demos.mvvm.mycase.model.LiveShowBlock;
import com.demos.mvvm.mycase.net.BolomeRequest;

import java.util.List;

/**
 * Created by wangpeng on 16/5/18.
 */
public class DetailViewModel extends MvvmBindingViewModel<List<LiveShow>, DetailView> {
    private List<LiveShow> oldContent;

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if (isViewAttached())
            getView().handleError(volleyError);
    }

    public void loadData() {
        if (oldContent == null) {
            RequestQueue queue = Volley.newRequestQueue(App.getContext());
            BolomeRequest<LiveShowBlock> request = new BolomeRequest<>(Request.Method.GET,
                    "https://stage.bolo.me/v7/live_show?page_size=20",
                    new Response.Listener<LiveShowBlock>() {
                        @Override
                        public void onResponse(LiveShowBlock liveShowBlock) {
                            oldContent = liveShowBlock.shows;
                            if (isViewAttached())
                                getView().setData(oldContent);
                        }
                    }, this, LiveShowBlock.class);
            queue.add(request);
        } else {
            if (isViewAttached())
                getView().setData(oldContent);
        }

    }

}
