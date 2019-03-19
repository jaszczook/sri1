package com.jaszczook.sri1.services.service;

import com.jaszczook.sri1.services.dao.UserRepository;
import com.jaszczook.sri1.services.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Transactional
	public List<User> getUserList() {
		return userRepository.findAll();
	}

	@Transactional
	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Unable to find user with ID: " + userId));
	}

	@Transactional
	public User updateUser(Long userId, User user) {
		return userRepository.findById(userId).map(currentUser -> {
			user.setId(currentUser.getId());
			return userRepository.save(user);
		}).orElseThrow(() -> new RuntimeException("Unable to find user with ID: " + userId));
	}

	@Transactional
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}
}
