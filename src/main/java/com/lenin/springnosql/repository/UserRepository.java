package com.lenin.springnosql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lenin.springnosql.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> getByUserId(String userId);

	Page<User> findByStatus(boolean status, Pageable pageable);

	Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

	List<User> findByNameContainingIgnoreCase(String name);

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

}
