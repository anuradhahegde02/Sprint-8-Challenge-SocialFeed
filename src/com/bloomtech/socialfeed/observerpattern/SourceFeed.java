package com.bloomtech.socialfeed.observerpattern;

import com.bloomtech.socialfeed.models.Post;
import com.bloomtech.socialfeed.models.User;
import com.bloomtech.socialfeed.repositories.PostRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//TODO: Implement Observer Pattern
public class SourceFeed implements Source {
    private final PostRepository postRepository = new PostRepository();

    private List<Post> posts;
    private List<Observer> oUserFeed;
    private String tempUserName = null;

    public SourceFeed() {
        this.posts = new ArrayList<>();
        this.oUserFeed = new ArrayList<>();
    }

    public void getAllPosts() {
        postRepository.getAllPosts();
    }

    public Post addPost(User user, String body) {
        Post post = new Post(user.getUsername(),
                LocalDateTime.now(),
                body);
        posts = postRepository.addPost(post);
        tempUserName = user.getUsername();
        updateAll();

        return post;
    }

    public List<Observer> getObservers() {
        return oUserFeed;
    }

    public List<Post> getPosts() {
        return posts;
    }

    @Override
    public void attach(Observer observer) {
        oUserFeed.add((OUserFeed) observer);

    }

    @Override
    public void detach(Observer observer) {
        oUserFeed.remove(observer);

    }

    @Override
    public void updateAll() {
        for (Observer o : oUserFeed) {
            if (o instanceof OUserFeed && tempUserName != null) {
                List<String> tempFollowers = ((OUserFeed) o).getUser().getFollowing();
                if (tempFollowers.contains(tempUserName)) {
                    o.update();
                }
            }


        }
    }
}
