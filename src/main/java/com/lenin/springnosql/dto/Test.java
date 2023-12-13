package com.lenin.springnosql.dto;

import java.nio.charset.StandardCharsets;

import io.jsonwebtoken.Jwts;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new String(Jwts.SIG.HS256.key().build().getEncoded(), StandardCharsets.UTF_8));
	}

}
