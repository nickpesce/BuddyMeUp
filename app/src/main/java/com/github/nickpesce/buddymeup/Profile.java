package com.github.nickpesce.buddymeup;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Profile implements Serializable{

    public HashMap<String, Friend> friends;
    private HashMap<String, Friend> buddies;
    public String name;
    public String id;

    public Profile(Context context)
    {
        buddies = new HashMap<>();
        friends = new HashMap<>();

    }

    public HashMap<String, Friend> getFriends()
    {
        return friends;
    }

    public HashMap<String, Friend> getBuddies() {
        return buddies;
    }


}
