package com.demos.NestedScrolling.person_center;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demos.R;
import com.demos.main.base.StringAdapter;
import com.demos.main.base.ToolBarActivity;

import java.util.Arrays;

import butterknife.Bind;

/**
 * Created by wangpeng on 16/7/4.
 */
public class PersonCenterActivity extends ToolBarActivity {
    @Override
    public int getLayout() {
        return R.layout.person_center;
    }

    @Bind(R.id.recycleview)
    RecyclerView recyclerView;
    @Bind(R.id.head_view)
    HeadLayout headLayout;

    @Bind(R.id.user_center_bg)
    ImageView imageViewBg;
    @Bind(R.id.iv_avatar)
    ImageView avatar;

    @Bind(R.id.user_center_cover_bg)
    ImageView imageViewCover;

    @Bind(R.id.tv_integral)
    TextView integral;

    @Bind(R.id.tv_coupon)
    TextView coupon;

    @Bind(R.id.tv_name)
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StringAdapter strings = new StringAdapter(this) {
            @Override
            protected void onItemClick(View v, int position) {

            }
        };
        strings.addAll(Arrays.asList("1", "2", "3", "4", "5", "1", "2", "3", "4", "5", "1", "2", "3",
                "4", "5", "1", "2", "3", "4", "5", "1", "2", "3", "4", "5", "1", "2", "3", "4", "5"));
        recyclerView.setAdapter(strings);

        headLayout.setHeaderTranslationListener(new HeaderTranslationListener() {
            @Override
            public void onTranslate(int offsetY) {
                int height = headLayout.getHeight();
                float percent = Math.abs((float) (offsetY + height) / (float) height);
                float infoPercent = Math.max(0, Math.min(1, percent));// 0~p~1
                float bgPercent = Math.max(1, percent);//1 ~

                imageViewBg.setScaleX(bgPercent);
                imageViewBg.setScaleY(bgPercent);
                imageViewCover.setScaleX(bgPercent);
                imageViewCover.setScaleY(bgPercent);

                if (offsetY > 0) {//down
                    imageViewCover.setAlpha(2 - percent);
                    imageViewBg.setTranslationY(offsetY / 2);
                    imageViewCover.setTranslationY(offsetY / 2);
                } else {//up
                    imageViewBg.setTranslationY(offsetY);
                    imageViewCover.setTranslationY(offsetY);
                    imageViewCover.setAlpha(1f);

                    avatar.setTranslationY(-offsetY / 3);
                    name.setTranslationY((float) ((-offsetY / 3) * 1.5));

                    integral.setTranslationY(offsetY / 3);
                    coupon.setTranslationY(offsetY / 3);
                    integral.setAlpha(infoPercent);
                    coupon.setAlpha(infoPercent);
                }

                avatar.setScaleX(infoPercent);
                avatar.setScaleY(infoPercent);
            }
        });

    }
}
