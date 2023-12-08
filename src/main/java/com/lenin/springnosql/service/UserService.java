package com.lenin.springnosql.service;

import java.util.List;
import java.util.Optional;

import com.lenin.springnosql.model.User;

public interface UserService {

	List<User> getAllUsers();

	Optional<User> getUserById(String userId);

	User addNewUser(User user);

	Object getAllUserSettings(String userId);

	String getUserSetting(String userId, String key);

	String addUserSetting(String userId, String key, String value);

	List<User> getUserByName(String name);

	User updateUserStatus(String id, boolean status);
}
