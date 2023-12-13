package com.lenin.springnosql.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lenin.springnosql.model.ERole;
import com.lenin.springnosql.model.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
	Optional<Role> findByName(ERole name);
}
