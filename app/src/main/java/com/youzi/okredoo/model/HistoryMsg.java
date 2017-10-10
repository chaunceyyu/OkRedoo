package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by hjw on 16/5/17.
 */
public class HistoryMsg implements Serializable {
    private String name;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
