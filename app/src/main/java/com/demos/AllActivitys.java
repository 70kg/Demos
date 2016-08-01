package com.demos;

import android.app.Activity;

import com.demos.CustomDialog.CustomDialogActivity;
import com.demos.RequestQueue.QueueActivity;
import com.demos.TwoBallsSeekbar.SeekBarActivity;
import com.demos.agera.AgeraActivity;
import com.demos.aidl.AidlActivity;
import com.demos.aidl.BookManagerActivity;
import com.demos.asynchronous.AsyncTaskActivity;
import com.demos.db.DbActivity;
import com.demos.dragger.DraggerActivity;
import com.demos.faceabstrct.FaceAbsActivity;
import com.demos.headlist.RefreshListActivity;
import com.demos.hook.simpleHook.FirstActivity;
import com.demos.imageLoader.ImageLoaderActivity;
import com.demos.mvvm.MvvmActivity;
import com.demos.service.ServiceActivity;
import com.demos.uploadPic.UpLoadPicActivity;
import com.demos.viewdraghelper.SwipBackActivity;
import com.demos.window.WindowActivity;
import com.demos.NestedScrolling.person_center.PersonCenterActivity;
import com.demos.NestedScrolling.others.OthersActivity;
import com.demos.hook.simpleHook.weishu.WeishuActivity;

/**
 * Created by Mr_Wrong on 16/2/2.
 */
public enum AllActivitys {
    TestActivity(CustomDialogActivity.class),
    AidlActivity(AidlActivity.class),
    BookManagerActivity(BookManagerActivity.class),
    DragActivity(SwipBackActivity.class),
    AsyncTaskActivity(AsyncTaskActivity.class),
    WindowActivity(WindowActivity.class),
    CheckBoxActivity(com.demos.checkbox.CheckBoxActivity.class),
    SeekBarActivity(SeekBarActivity.class),
    BehaviorActivity(com.demos.NestedScrolling.BehaviorActivity.class),
    DayNightActivity(com.demos.support_23_2.DayNightActivity.class),
    UpLoadPicActivity(UpLoadPicActivity.class),
    PinnedSectionListActivity(com.demos.headlist.PinnedSectionListActivity.class),
    RefreshListActivity(RefreshListActivity.class),
    CustomDialogActivity(CustomDialogActivity.class),
    QueueActivity(QueueActivity.class),
    BesselActivity(com.demos.Bessel.BesselActivity.class),
    DraggerActivity(DraggerActivity.class),
    FaceAbsActivity(FaceAbsActivity.class),
    ServiceActivity(ServiceActivity.class),
    DouBanMovieActivity(com.demos.douban.DouBanMovieActivity.class),
    ImageLoaderActivity(ImageLoaderActivity.class),
    FluxActivity(com.demos.flux.FluxActivity.class),
    DbActivity(DbActivity.class),
    NotificationActivity(com.demos.notification.NotificationActivity.class),
    RecycleViewActivity(com.demos.recycleview.RecycleViewActivity.class),
    MvvmActivity(MvvmActivity.class),
    AgeraActivity(AgeraActivity.class),
    PersonCenterActivity(PersonCenterActivity.class),
    OthersActivity(OthersActivity.class),
    HookFirstActivity(FirstActivity.class),
    WeishuActivity(WeishuActivity.class);


    private Class activityClazz;

    AllActivitys(Class clazz) {
        activityClazz = clazz;
    }

    public Activity getActivity() {
        try {
            return (Activity) activityClazz.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init Activity instance");
        }

    }
}
