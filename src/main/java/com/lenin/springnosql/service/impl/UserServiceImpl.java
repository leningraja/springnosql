package com.lenin.springnosql.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lenin.springnosql.model.User;
import com.lenin.springnosql.repository.UserRepository;
import com.lenin.springnosql.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Page<User> findAll(Pageable paging) {
		return userRepository.findAll(paging);
	}
	
	@Override
	public Page<User> findByStatus(boolean status, Pageable pageable){
		return userRepository.findByStatus(status, pageable);
	}
	
	@Override
	public Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable){
		return userRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public Optional<User> getUserById(String id) {
		log.info("User ID: {}", id);
		return userRepository.getByUserId(id);
	}
	
	@Override
	public List<User> getUserByName(String name) {
		return userRepository.findByNameContainingIgnoreCase(name);
	}

	@Override
	public User addNewUser(User user) {
		if (StringUtils.hasText(user.getId())) {
			Optional<User> userUpdate = userRepository.findById(user.getId());
			if (userUpdate.isPresent()) {
				userUpdate.get().setStatus(user.isStatus());
				userUpdate.get().setUsername(user.getUsername());
				return userRepository.save(userUpdate.get());
			}
		}
		return userRepository.save(user);
	}

	@Override
	public Object getAllUserSettings(String userId) {
		Optional<User> user = userRepository.getByUserId(userId);
		return user.isPresent() ? user.get().getUserSettings() : "User not found.";
	}

	@Override
	public String getUserSetting(String userId, String key) {
		Optional<User> user = userRepository.getByUserId(userId);
		return user.isPresent() ? user.get().getUserSettings().get(key) : "Not found.";
	}
	
	@Override
	public User updateUserStatus(String id, boolean status) {
		Optional<User> user = userRepository.getByUserId(id);
		if (user.isPresent()) {
			User userUpdate = user.get();
			userUpdate.setStatus(status);;
			return userRepository.save(userUpdate);
		} else {
			return null;
		}
	}

	@Override
	public String addUserSetting(String userId, String key, String value) {
		Optional<User> user = userRepository.getByUserId(userId);
		if (user.isPresent()) {
			User userUpdate = user.get();
			userUpdate.getUserSettings().put(key, value);
			userRepository.save(userUpdate);
			return "Key added.";
		} else {
			return "User not found.";
		}
	}
}
