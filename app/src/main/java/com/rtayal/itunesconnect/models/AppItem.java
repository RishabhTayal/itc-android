package com.rtayal.itunesconnect.models;

import java.io.Serializable;
import java.util.HashMap;

public class AppItem implements Serializable {
    public final String bundle_id;
    public final String name;
    public final String app_icon_preview_url;

    public AppItem(HashMap<String, Object> hashMap) {
        this.bundle_id = hashMap.get("bundle_id").toString();
        this.name = hashMap.get("name").toString();
        this.app_icon_preview_url = hashMap.get("app_icon_preview_url").toString();
    }
}
