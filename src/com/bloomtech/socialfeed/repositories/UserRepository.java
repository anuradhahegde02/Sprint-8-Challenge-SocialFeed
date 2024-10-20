package com.bloomtech.socialfeed.repositories;

import com.bloomtech.socialfeed.models.User;
import com.bloomtech.socialfeed.validators.UserInfoValidator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepository {
    private static final String USER_DATA_PATH = "src/resources/UserData.json";

    private static final UserInfoValidator userInfoValidator = new UserInfoValidator();

    public UserRepository() {
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        List<User> users;
        //TODO: return parsed list of Users from UserData.json
        // Create a Gson instance
        Gson gson = new Gson();
        // Type to represent a List of User objects
        Type userListType = new TypeToken<List<User>>() {
        }.getType();
        // Read and convert JSON file content to User object
        try (FileReader reader = new FileReader(USER_DATA_PATH)) {
            // Parse JSON array to List<User>
            users = gson.fromJson(reader, userListType);
            if (users != null) {
                allUsers.addAll(users);
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return allUsers;
    }

    public Optional<User> findByUsername(String username) {
        return getAllUsers()
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    public void save(User user) {
        List<User> allUsers = getAllUsers();

        Optional<User> existingUser = allUsers.stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst();

        if (!existingUser.isEmpty()) {
            throw new RuntimeException("User with name: " + user.getUsername() + " already exists!");
        }
        allUsers.add(user);
        //TODO: Write allUsers to UserData.json
        // Create a Gson instance
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // Write the list to the JSON file
        try (FileWriter writer = new FileWriter(USER_DATA_PATH)) {
            gson.toJson(allUsers, writer);
            // System.out.println("List successfully written to " + USER_DATA_PATH);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

    }
}
