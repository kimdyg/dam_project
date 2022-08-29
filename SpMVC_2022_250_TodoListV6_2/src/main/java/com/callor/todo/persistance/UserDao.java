package com.callor.todo.persistance;

import java.util.List;

import com.callor.todo.model.AuthorityVO;
import com.callor.todo.model.TodoVO;
import com.callor.todo.model.TodoVO2;
import com.callor.todo.model.UserVO;

public interface UserDao extends GenericDao<UserVO, String> {

	public void create_user_table();
	public void create_auth_table();
	public List<AuthorityVO> select_auths(String username);
	public int role_insert(List<AuthorityVO> auths);
	public List<TodoVO> getTodoList(String username);
	public List<TodoVO2> getTodoList2(String username);
	
	
}
