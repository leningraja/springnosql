package com.lenin.springnosql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.lenin.springnosql.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
	
	Optional<User> getByUserId(String userId);
	
	@Query(value = "{name:?0}", sort= "{creationDate:1}") //1 = ASC, -1 = DESC
	public List<User> getUserByName(String name);

}
