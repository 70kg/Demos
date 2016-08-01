package com.demos.dragger;

import dagger.Component;

/**
 * Created by Mr_Wrong on 16/3/6.
 */
@Component(modules = {ActivityModule.class, BeanModuel.class})
public interface ActivityComponent {
    void inject(DraggerActivity activity);
}