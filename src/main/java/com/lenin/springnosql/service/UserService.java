package com.lenin.springnosql.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lenin.springnosql.model.User;

public interface UserService {

	Optional<User> getUserById(String userId);

	User addNewUser(User user);

	Object getAllUserSettings(String userId);

	String getUserSetting(String userId, String key);

	String addUserSetting(String userId, String key, String value);

	List<User> getUserByName(String name);

	User updateUserStatus(String id, boolean status);

	Page<User> findByStatus(boolean status, Pageable pageable);

	Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Page<User> findAll(Pageable paging);

	List<User> findAll();
}
