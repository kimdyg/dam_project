package com.callor.tdam.persistance;

import java.util.List;

import com.callor.tdam.model.AuthorityVO;
import com.callor.tdam.model.TdamVO;
import com.callor.tdam.model.TdamVO2;
import com.callor.tdam.model.UserVO;

public interface UserDao extends GenericDao<UserVO, String> {

	public void create_user_table();
	public void create_auth_table();
	public List<AuthorityVO> select_auths(String username);
	public int role_insert(List<AuthorityVO> auths);
	public List<TdamVO> getTodoList(String username);
	public List<TdamVO2> getTodoList2(String username);
	
	
}
