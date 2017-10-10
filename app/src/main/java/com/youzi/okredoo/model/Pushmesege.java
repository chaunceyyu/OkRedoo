package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/6.
 */

public class Pushmesege implements Serializable {
// "type":"live_noties",
// "url":"tudouni:\/\/tudouni\/playLive?lid=172",
// "content":"你的朋友正在直播，赶快去捧场把"}
   private String type;
   private String url;
   private String content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
