package com.employmeo.data.service;

import java.util.Set;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.User;
import com.employmeo.data.repository.UserRepository;

import jersey.repackaged.com.google.common.collect.Sets;
import lombok.NonNull;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public Set<User> getAllUsers() {
		Set<User> users = Sets.newHashSet(userRepository.findAll());
		log.debug("Retrieved all {} users", users);

		return users;
	}

	@Override
	public User save(@NonNull User user) {
		User savedUser = userRepository.save(user);
		log.debug("Saved user {}", user);

		return savedUser;
	}

	@Override
	public User getUserById(@NonNull Long userId) {
		User user = userRepository.findOne(userId);
		log.debug("Retrieved for id {} entity {}", userId, user);

		return user;
	}
}
