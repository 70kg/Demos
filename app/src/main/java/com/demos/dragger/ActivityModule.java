package com.demos.dragger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mr_Wrong on 16/3/6.
 */
@Module
public class ActivityModule {

    @Provides
    UserModel provideUserModel() {
        return new UserModel();
    }
}