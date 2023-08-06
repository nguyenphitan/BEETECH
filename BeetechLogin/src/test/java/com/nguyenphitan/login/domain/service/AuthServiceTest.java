package com.nguyenphitan.login.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nguyenphitan.BeetechLogin.domain.entity.User;
import com.nguyenphitan.BeetechLogin.domain.repository.AccessTokenRepository;
import com.nguyenphitan.BeetechLogin.domain.repository.RefreshTokenRepository;
import com.nguyenphitan.BeetechLogin.domain.repository.UserRepository;
import com.nguyenphitan.BeetechLogin.domain.service.impl.AuthServiceImpl;
import com.nguyenphitan.BeetechLogin.request.ChangePasswordRequest;

/**
 * Auth service test.
 * 
 * @author ADMIN
 *
 */
public class AuthServiceTest {

	@Mock
	UserRepository userRepository;
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	@Mock
	AccessTokenRepository accessTokenRepository;
	
	@Mock
	RefreshTokenRepository refreshTokenRepository;

	@InjectMocks
	AuthServiceImpl service;
	
	@BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

	/**
	 * Change password test.
	 * 
	 * @throws Exception
	 */
	@Test
	void changePasswordTest() throws Exception {
		int expect = 0;
		
		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setUserId(1L);
		request.setPassword("123456");
		
		User user = new User();
		user.setId(1L);
		user.setUsername("test");
		user.setPassword("12345678");
		
		User userChanged = new User();
		userChanged.setId(user.getId());
		userChanged.setUsername(user.getUsername());
		userChanged.setPassword("asbdkasjdkas");
		
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn(userChanged.getPassword());
		when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		when(userRepository.save(Mockito.any())).thenReturn(userChanged);
		when(accessTokenRepository.deleteByUserId(Mockito.anyLong())).thenReturn(1);
		when(refreshTokenRepository.deleteByUserId(Mockito.anyLong())).thenReturn(1);
		
		int actual = service.changePassword(request);
		
		assertEquals(expect, actual);
		
		verify(passwordEncoder, times(1)).encode(Mockito.anyString());
		verify(userRepository, times(1)).findById(Mockito.anyLong());
		verify(userRepository, times(1)).save(Mockito.any());
		verify(accessTokenRepository, times(1)).deleteByUserId(Mockito.anyLong());
		verify(refreshTokenRepository, times(1)).deleteByUserId(Mockito.anyLong());
		verifyNoMoreInteractions(refreshTokenRepository);
	}
}
