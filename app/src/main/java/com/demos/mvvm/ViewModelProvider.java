package com.demos.mvvm;

import com.demos.mvvm.mvvmView.MvvmView;
import com.demos.mvvm.mvvmViewModel.MvvmViewModel;

import java.util.HashMap;

/**
 * Created by wangpeng on 16/5/18.
 */
public class ViewModelProvider {

    public static ViewModelProvider mInstacne;

    public static ViewModelProvider getInstacne() {
        if (mInstacne == null) {
            mInstacne = new ViewModelProvider();
        }
        return mInstacne;
    }

    private final HashMap<String, MvvmViewModel<? extends MvvmView>> mViewModelCache;

    public ViewModelProvider() {
        mViewModelCache = new HashMap<>();
    }

    public <V extends MvvmView> ViewModleWrapper<V> getViewModelWrapper(String viewModelId, Class<? extends MvvmViewModel<V>> viewModelClass) {

        MvvmViewModel<V> instance = (MvvmViewModel<V>) mViewModelCache.get(viewModelId);
        if (instance != null) {
            return new ViewModleWrapper<>(instance, false);
        }

        try {
            instance = viewModelClass.newInstance();
            instance.setViewModelId(viewModelId);
//            instance.initializeViewSubscribes();
            mViewModelCache.put(viewModelId, instance);
            return new ViewModleWrapper<>(instance, true);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public void removeViewModel(String viewModelId) {
        mViewModelCache.remove(viewModelId);

    }

    class ViewModleWrapper<V extends MvvmView> {
        public final MvvmViewModel<V> viewModel;
        public final boolean wasCreated;

        public ViewModleWrapper(MvvmViewModel<V> viewModel, boolean wasCreated) {
            this.viewModel = viewModel;
            this.wasCreated = wasCreated;
        }
    }

}
