package com.lenin.springnosql.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document("database_sequences")
@Data
@Builder
public class DatabaseSequence {

	@Id
	private String id;
	private Long sequence;

}
