package com.lenin.springnosql.service;

public interface SequenceGeneratorService {
	
	public long getNextValue(String seqName);
	
}
