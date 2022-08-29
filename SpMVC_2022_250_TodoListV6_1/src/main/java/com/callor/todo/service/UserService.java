package com.callor.todo.service;

import java.util.List;

import com.callor.todo.model.TodoVO;
import com.callor.todo.persistance.UserDao;

public interface UserService extends UserDao {

	List<TodoVO> getTodoList(String username);
	

}
