package com.lenin.springnosql.listener;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.lenin.springnosql.model.User;
import com.lenin.springnosql.service.SequenceGeneratorService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserModelListener extends AbstractMongoEventListener<User> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public UserModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }
    
    @Override
    public void onBeforeConvert(final BeforeConvertEvent<User> event) {
    	log.info("Source value: {}", event.getSource());
    	log.info("User  ID: {}", event.getSource().getId());
        if (!StringUtils.hasText(event.getSource().getId())) {
        	event.getSource().setCreationDate(new Date());
        	String userSequence =  "U" + sequenceGenerator.getNextValue(User.SEQUENCE_NAME);
        	log.info("User sequence ID: {}", userSequence);
            event.getSource().setUserId(userSequence);
        }
    }


}
