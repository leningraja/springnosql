package com.lenin.springnosql.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "roles")
public class Role implements GrantedAuthority{

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private ERole name;

	@Override
	public String getAuthority() {
		return this.name.name();
	}
}
