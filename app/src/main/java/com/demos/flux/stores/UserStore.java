package com.demos.flux.stores;

import com.demos.flux.actions.Action;
import com.demos.flux.actions.UserAction;
import com.demos.flux.model.GitHubUser;
import com.demos.flux.model.User;

/**
 * Created by Mr_Wrong on 16/3/25.
 */
public class UserStore extends Store {
    User mUser = new User();

    @Override
    public void onAction(Action action) {
        switch (action.getType()) {
            case UserAction.MYACTION:
                GitHubUser gitHubUser = (GitHubUser) action.getData();
                mUser.setName(gitHubUser.name);
                break;
        }
        post();
    }

    public User getUser() {
        return mUser;
    }

    @Override
    public StoreChangeEvent changeEvent() {
        return new UserEvent();
    }

    public class UserEvent extends StoreChangeEvent {
    }

}
