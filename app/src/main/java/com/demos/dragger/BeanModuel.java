package com.demos.dragger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mr_Wrong on 16/3/6.
 */
@Module
public class BeanModuel {
    @Provides
    Bean providerBean() {
        return new Bean();
    }
}
