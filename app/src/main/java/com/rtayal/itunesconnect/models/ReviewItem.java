package com.rtayal.itunesconnect.models;

import java.io.Serializable;
import java.util.HashMap;

public class ReviewItem implements Serializable {
    public final String id;
    public final String title;
    public final String rating;
    public final String review;

    public ReviewItem(HashMap<String, Object> object) {
        this.id = object.get("id").toString();
        this.title = object.get("title").toString();
        this.rating = object.get("rating").toString();
        this.review = object.get("review").toString();
    }

    @Override
    public String toString() {
        return title;
    }

}
