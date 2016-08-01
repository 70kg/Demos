package com.demos.flux.stores;

import com.demos.flux.actions.Action;
import com.demos.flux.actions.MessageAction;
import com.demos.flux.model.Message;

/**
 * Created by Mr_Wrong on 16/3/25.
 * store是把请求结果处理包装一下  发送给activity更新UI
 */
public class MessageStore extends Store {
    private Message mMessage = new Message();

    public MessageStore() {
        super();
    }

    public String getMessage() {
        return mMessage.getMessage();
    }

    @Override
    public void onAction(Action action) {
        switch (action.getType()) {
            case MessageAction.ACTION_MESSAGE:
                mMessage.setMessage((String) action.getData());
                break;
        }
        post();
    }

    @Override
    public StoreChangeEvent changeEvent() {
        return new MessageEvent();
    }

    public class MessageEvent extends StoreChangeEvent {
    }
}
