package com.employmeo.data.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.User;
import com.employmeo.data.repository.UserRepository;

import jersey.repackaged.com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

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
