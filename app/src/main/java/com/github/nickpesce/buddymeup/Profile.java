package com.github.nickpesce.buddymeup;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Profile implements Serializable{

    private HashMap<String, Friend> friends;
    private HashMap<String, Friend> buddies;
    public String name;
    public String id;

    public Profile(Context context)
    {
        buddies = new HashMap<>();
        friends = new HashMap<>();
        friends.put("1", new Friend(context, "Tal", "1"));
        friends.put("0", new Friend(context, "Nick", "0"));
        friends.put("2", new Friend(context, "Kevin", "2"));
        friends.put("3", new Friend(context, "Diane", "3"));
    }

    public HashMap<String, Friend> getFriends()
    {
        return friends;
    }

    public HashMap<String, Friend> getBuddies() {
        return buddies;
    }


}
