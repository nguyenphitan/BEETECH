package com.nguyenphitan.BeetechLogin.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nguyenphitan.BeetechLogin.custom.CustomUserDetails;
import com.nguyenphitan.BeetechLogin.model.User;
import com.nguyenphitan.BeetechLogin.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	// Kiểm tra xe user có tồn tại trong Database hay không?
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("Tai khoan mat khau khong chinh xac")
		);
		return new CustomUserDetails(user);
	}
	
	@Transactional
	public UserDetails loadUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("User not found with id: " + id)
		);
		
		return new CustomUserDetails(user);
	}

}
