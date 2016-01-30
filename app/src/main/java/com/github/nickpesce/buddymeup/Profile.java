package com.github.nickpesce.buddymeup;

import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable{

    ArrayList<Friend> friends;
    ArrayList<Friend> buddies;

    public Profile()
    {
        friends = new ArrayList<>();
        friends.add(new Friend("Tal", 1));
        friends.add(new Friend("Nick", 0));

    }

    public ArrayList<Friend> getFriends()
    {
        return friends;
    }

}
