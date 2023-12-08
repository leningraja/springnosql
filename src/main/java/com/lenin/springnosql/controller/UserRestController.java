package com.lenin.springnosql.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lenin.springnosql.model.User;
import com.lenin.springnosql.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/user")
@Slf4j
public class UserRestController {

	private final UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping()
	public List<User> getAllUsers() {
		log.info("Getting all users.");
		return userService.getAllUsers();
	}

	@GetMapping("/{userId}")
	public User getUser(@PathVariable String userId) {
		log.info("Getting user with ID: {}.", userId);
		Optional<User> user = userService.getUserById(userId);
		return user.isPresent() ? user.get() : null;
	}

	@PostMapping("/create")
	public User addNewUsers(@RequestBody User user) {
		log.info("Saving user.");
		log.info("UserId: {} , Name: {}", user.getUserId(), user.getName());
		return userService.addNewUser(user);
	}

	@GetMapping("/settings/{userId}")
	public Object getAllUserSettings(@PathVariable String userId) {
		return userService.getAllUserSettings(userId);
	}

	@GetMapping("/settings/{userId}/{key}")
	public String getUserSetting(@PathVariable String userId, @PathVariable String key) {
		Optional<User> user = userService.getUserById(userId);
		if (user.isPresent()) {
			return user.get().getUserSettings().get(key);
		} else {
			return "User not found.";
		}
	}

	@GetMapping("/settings/{userId}/{key}/{value}")
	public String addUserSetting(@PathVariable String userId, @PathVariable String key, @PathVariable String value) {
		Optional<User> user = userService.getUserById(userId);
		if (user.isPresent()) {
			User updateUser = user.get();
			updateUser.getUserSettings().put(key, value);
			userService.addNewUser(updateUser);
			return "Key added";
		} else {
			return "User not found.";
		}
	}
}
