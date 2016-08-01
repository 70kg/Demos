package com.demos.faceabstrct;

/**
 * Created by Mr_Wrong on 16/3/11.
 */
public class Bean implements IPost {

    String name;
    String content;

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getContent() {
        return content;
    }
}
