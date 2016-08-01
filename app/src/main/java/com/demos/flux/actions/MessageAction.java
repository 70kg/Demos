package com.demos.flux.actions;

/**
 * Created by Mr_Wrong on 16/3/25.
 */
public class MessageAction extends Action<String> {
    public static final String ACTION_MESSAGE = "new_messsage";
    public MessageAction(String type, String data) {
        super(type, data);
    }

}
