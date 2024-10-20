package com.bloomtech.socialfeed.observerpattern;

import com.bloomtech.socialfeed.App;
import com.bloomtech.socialfeed.models.Post;
import com.bloomtech.socialfeed.models.User;

import java.util.ArrayList;
import java.util.List;

//TODO: Implement Observer Pattern
public class OUserFeed implements Observer {
    private User user;
    private List<Post> feed;

    public OUserFeed(User user) {
        this.user = user;
        //TODO: update OUserFeed in constructor after implementing observer pattern
        feed = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public List<Post> getFeed() {
        return feed;
    }

    @Override
    public void update() {
        List<String> followingList = user.getFollowing();
        List<Post> posts = App.sourceFeed.getPosts();
        this.feed.clear();
        for (Post p : posts) {
            if (followingList.contains(p.getUsername())) {
                feed.add(p);
            }

        }


    }
}
