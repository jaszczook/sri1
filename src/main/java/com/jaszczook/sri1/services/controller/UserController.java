package com.jaszczook.sri1.services.controller;

import com.jaszczook.sri1.services.domain.User;
import com.jaszczook.sri1.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<User> createUser(
			@RequestBody User user
	) {
		User createdUser = userService.createUser(user);
		return ResponseEntity.ok(createdUser);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUserList() {
		return ResponseEntity.ok(userService.getUserList());
	}

	@RequestMapping(value = "{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(
			@PathVariable(value = "userId") Long userId
	) {
		User user = userService.getUser(userId);

		CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS);
		cacheControl.cachePrivate();

		return ResponseEntity.ok()
				.eTag(Long.toString(user.hashCode()))
				.cacheControl(cacheControl)
				.body(user);
	}

	@RequestMapping(value = "{userId}", method = RequestMethod.PUT)
	ResponseEntity<User> updateUser(
			@PathVariable(value = "userId") Long userId,
			@RequestBody User user
	) {
		User updatedUser = userService.updateUser(userId, user);
		return ResponseEntity.ok(updatedUser);
	}

	@RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
	ResponseEntity<Void> deleteUser(
			@PathVariable(value = "userId") Long userId
	) {
		userService.deleteUser(userId);
		return ResponseEntity.ok().build();
	}
}
