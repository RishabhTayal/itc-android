package com.rtayal.itunesconnect.models;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by rtayal on 1/15/18.
 */

public class TesterItem implements Serializable {

    public String first_name;
    public String last_name;
    public String email;

    public TesterItem(HashMap<String, Object> hashMap) {
        this.first_name = hashMap.get("first_name").toString();
        this.last_name = hashMap.get("last_name").toString();
        this.email = hashMap.get("email").toString();
    }
}
