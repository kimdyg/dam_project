package com.callor.tdam.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.callor.tdam.model.AuthorityVO;
import com.callor.tdam.model.TdamVO;
import com.callor.tdam.model.TdamVO2;
import com.callor.tdam.model.UserVO;
import com.callor.tdam.persistance.UserDao;
import com.callor.tdam.service.UserService;

@Service("userServiceV1")
public class UserServiceImplV1 implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void create_user_table() {
		
	}

	@Override
	public void create_auth_table() {
		
	}

	@Override
	public List<AuthorityVO> select_auths(String username) {
		return null;
	}

	@Override
	public List<UserVO> selectAll() {
		return null;
	}

	@Override
	public UserVO findById(String id) {
		return null;
	}

	@Transactional
	@Override
	public int insert(UserVO vo) {
		
		List<UserVO> userList = userDao.selectAll();
		List<AuthorityVO> authList = new ArrayList<>();
		if(userList == null || userList.size() < 1 ) {
			authList.add(AuthorityVO.builder()
								.username(vo.getUsername())
								.authority("ROLE_ADMIN")
								.build()
					);
			authList.add(AuthorityVO.builder()
					.username(vo.getUsername())
					.authority("ROLE_USER")
					.build()
					);
			vo.setEnabled(true);
		} else {
			authList.add(AuthorityVO.builder()
					.username(vo.getUsername())
					.authority("ROLE_USER")
					.build()
					);
			vo.setEnabled(false);
		}
		String encPassword = passwordEncoder.encode(vo.getPassword());
		vo.setPassword(encPassword);
		
		int ret = userDao.role_insert(authList);
		ret += userDao.insert(vo);
		return ret;
	}

	@Override
	public int update(UserVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(UserVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int role_insert(List<AuthorityVO> auths) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TdamVO> getTodoList(String username) {
		// TODO Auto-generated method stub
		return userDao.getTodoList(username);
	}

	@Override
	public List<TdamVO2> getTodoList2(String username) {
		// TODO Auto-generated method stub
		return userDao.getTodoList2(username);
	}


}
