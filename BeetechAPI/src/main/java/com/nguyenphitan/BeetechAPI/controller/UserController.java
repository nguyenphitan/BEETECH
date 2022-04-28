package com.nguyenphitan.BeetechAPI.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/users/{id}")
	public User getUser(@PathVariable("id") Long id) {
		return userRepository.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("User id invalid.")
		);
	}
	
	@PostMapping("/users")	// đăng ký tài khoản
	public User createUser(@Valid @RequestBody User user) {
		User user_raw = new User();
		user_raw.setUsername(user.getUsername());
		user_raw.setPassword(passwordEncoder.encode(user.getPassword()));
		user_raw.setRole(user.getRole());
		return userRepository.save(user_raw);
	}

	@PutMapping("/users/{id}")
	public User updateUser(@PathVariable("id") Long id, @Valid @RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable("id") Long id) {
		userRepository.deleteById(id);
	}
}