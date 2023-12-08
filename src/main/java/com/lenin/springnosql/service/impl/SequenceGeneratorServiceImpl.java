package com.lenin.springnosql.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lenin.springnosql.model.DatabaseSequence;
import com.lenin.springnosql.repository.SequenceRepository;
import com.lenin.springnosql.service.SequenceGeneratorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService{

	@Autowired
	private SequenceRepository sequenceRepository;
	
	public long getNextValue(String seqName) {
		Optional<DatabaseSequence> sequence = sequenceRepository.findById(seqName);
		long primarySequence = 10000l;
		if (sequence.isPresent()) {
			DatabaseSequence sequenceUpdate = sequence.get();
			sequenceUpdate.setSequence(sequence.get().getSequence() + 1);
			sequenceRepository.save(sequenceUpdate);
			primarySequence = sequenceUpdate.getSequence();
		} else {
			sequenceRepository.save(DatabaseSequence.builder().id(seqName).sequence(primarySequence).build());
		}

		log.info("DatabaseSequence ID:{}, Sequence : {}", seqName, primarySequence);
		return primarySequence;
	}

}
