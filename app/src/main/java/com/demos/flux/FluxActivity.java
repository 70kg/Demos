package com.demos.flux;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demos.R;
import com.demos.flux.model.User;
import com.demos.flux.stores.MessageStore;
import com.demos.flux.stores.UserStore;
import com.demos.main.base.BaseAdapter;
import com.demos.main.base.MyViewHolder;
import com.squareup.otto.Subscribe;

import butterknife.Bind;

/**
 * Created by Mr_Wrong on 16/3/25.
 * activity的职责只是去处理view的更新等操作
 *
 * 整个数据流向 action->dispatcher->store->view
 *
 * 简单的步骤 使用mActionsCreater里面定义请求方法,把action通过dispatcher进行分发,也就是调用store.onaction,
 * 分发到对应store里面,在store里面进行一定的包装处理去通知view进行更新 store里面只有get方法,只运行数据从store
 * 流向view,而且store 的数据改变只能是由于action的通知进行的改变
 */
public class FluxActivity extends FluxBaseActivity {

    @Bind(R.id.rv_flux)
    RecyclerView mRecyclerView;

    @Override
    public int getLayout() {
        return R.layout.flux;
    }

    MessageStore mStore = new MessageStore();
    UserStore mUserStore = new UserStore();
    UserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStore(mStore);
        initStore(mUserStore);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new UserAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mActionsCreater.queryUser("70kg");
    }

    @Subscribe
    public void messagechange(MessageStore.MessageEvent event) {

    }

    @Subscribe
    public void userchange(UserStore.UserEvent event) {
        mAdapter.add(mUserStore.getUser());
    }

    class UserAdapter extends BaseAdapter<User> {

        protected UserAdapter(Context context) {
            super(context);
        }

        @Override
        protected void onItemClick(View v, int position) {

        }

        @Override
        public void onBind(MyViewHolder viewHolder, int Position) {
            User user = get(Position);
            viewHolder.setTextView(R.id.tv_flux_name, user.getName());
        }

        @Override
        protected int getLayout() {
            return R.layout.flux_item;
        }
    }


}
