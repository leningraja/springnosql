package com.lenin.springnosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lenin.springnosql.model.DatabaseSequence;

@Repository
public interface SequenceRepository extends MongoRepository<DatabaseSequence, String>{
	
}
