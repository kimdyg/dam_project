package com.callor.todo.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.callor.todo.persistance.TodoDao;
import com.callor.todo.persistance.TodoDao2;
import com.callor.todo.persistance.UserDao;

/*
 * 프로젝트가 시작될 때 자동으로 실행할 method, bean 정의하기
 */
@Service
public class BeanService {

	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TodoDao todoDao;
	
	@Autowired
	private TodoDao2 todoDao2;
	
	// 자동실행 <> 실무에선 쓰지않는 꼼수
	@Bean
	public void create_table() {
		userDao.create_auth_table();
		userDao.create_user_table();
		todoDao.create_todo_table();
		todoDao2.create_todo_table2();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
