package com.bloomtech.socialfeed.repositories;

import com.bloomtech.socialfeed.models.Post;
import com.bloomtech.socialfeed.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostRepository {
    private static final String POST_DATA_PATH = "src/resources/PostData.json";

    public PostRepository() {
    }

    public List<Post> getAllPosts() {
        //TODO: return all posts from the PostData.json file
        List<Post> allPosts = new ArrayList<>();
        List<Post> posts = new ArrayList<>();
        //TODO: return parsed list of Users from PostData.json
        // Create a Gson instance
        Gson gson = new Gson();
        // Type to represent a List of User objects
        Type postListType = new TypeToken<List<Post>>() {
        }.getType();
        // Read and convert JSON file content to User object
        try (FileReader reader = new FileReader(POST_DATA_PATH)) {
            // Parse JSON array to List<User>
            posts = gson.fromJson(reader, postListType);
            if (posts != null) {
                allPosts.addAll(posts);
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return allPosts;
    }

    public List<Post> findByUsername(String username) {
        return getAllPosts()
                .stream()
                .filter(p -> p.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    public List<Post> addPost(Post post) {
        List<Post> allPosts = getAllPosts();
        Optional<Post> existingPost = allPosts.stream()
                .filter(u -> u.getUsername().equals(post.getUsername()))
                .filter(u -> u.getPostedon().equals(post.getPostedon()))
                .filter(u -> u.getBody().equals(post.getBody()))
                .findFirst();
        if (!existingPost.isEmpty()) {
            throw new RuntimeException("post: " + post.toString() + " already exists!");
        }
        allPosts.add(post);

        //TODO: Write the new Post data to the PostData.json file
        // Create a Gson instance
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // Write the list to the JSON file
        try (FileWriter writer = new FileWriter(POST_DATA_PATH)) {
            gson.toJson(allPosts, writer);
            System.out.println("List successfully written to " + POST_DATA_PATH);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }


        //TODO: Return an updated list of all posts
        return allPosts;
    }
}
