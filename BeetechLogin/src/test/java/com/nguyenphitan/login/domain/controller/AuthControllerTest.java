package com.nguyenphitan.login.domain.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nguyenphitan.BeetechLogin.domain.controller.AuthController;
import com.nguyenphitan.BeetechLogin.domain.service.AuthService;
import com.nguyenphitan.BeetechLogin.request.LoginRequest;
import com.nguyenphitan.BeetechLogin.response.LoginResponse;

/**
 * AuthControllerTest.
 * 
 * @author ADMIN
 *
 */
public class AuthControllerTest {
	
	private MockMvc mockMvc;

	@Mock
	private AuthService service;
	
	@InjectMocks
	private AuthController controller;
	
	private static final String BASE_URL = "/api/v1/auth";
	
	// Set up
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.build();
	}
	
	/**
	 * checkLoginTest.
	 * 
	 * @throws Exception
	 */
	@Test
	void checkLoginTest() throws Exception {
		LoginResponse expect = new LoginResponse("access token", "refresh token");
		LoginRequest request = new LoginRequest();
		request.setUsername("test");
		request.setPassword("test");
		
		when(service.checkLogin(Mockito.any())).thenReturn(expect);

		mockMvc.perform(post(BASE_URL + "/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
		.andExpect(status().isOk());
		
		verify(service, times(1)).checkLogin(Mockito.any());
		verifyNoMoreInteractions(service);
	}
	
	
	/*
     * converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
