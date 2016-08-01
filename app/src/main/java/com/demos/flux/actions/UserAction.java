package com.demos.flux.actions;

import com.demos.flux.model.GitHubUser;

/**
 * Created by Mr_Wrong on 16/3/25.
 */
public class UserAction extends Action<GitHubUser> {
    public static final String MYACTION = "new_useraction";

    public UserAction(String type, GitHubUser data) {
        super(type, data);
    }
}
