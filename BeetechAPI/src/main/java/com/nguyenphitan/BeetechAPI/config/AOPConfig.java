package com.nguyenphitan.BeetechAPI.config;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AOPConfig {
	
	@Autowired
	HttpSession session;
	
	@Before("execution(* com.nguyenphitan.BeetechAPI.controller.*.*(..))")
	public void handleUserLogin(JoinPoint joinPoint) throws Exception {
		String token = session.getAttribute("token") == null ? null : (String) session.getAttribute("token");
//		if(token == null) {
//			throw new Exception("Bạn chưa đăng nhập");
//		}
		System.out.println(token);
	}
	
}
