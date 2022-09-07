package com.callor.tdam.service;

import java.util.List;

import com.callor.tdam.model.TdamVO;
import com.callor.tdam.model.TdamVO2;
import com.callor.tdam.persistance.UserDao;

public interface UserService extends UserDao {

	List<TdamVO> getTodoList(String username);
	List<TdamVO2> getTodoList2(String username);
	

}
