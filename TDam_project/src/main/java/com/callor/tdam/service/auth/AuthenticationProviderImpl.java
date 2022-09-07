package com.callor.tdam.service.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.callor.tdam.model.AuthorityVO;
import com.callor.tdam.model.UserVO;
import com.callor.tdam.persistance.UserDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("authenticationProvider")
public class AuthenticationProviderImpl  implements AuthenticationProvider{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		UserVO userVO = userDao.findById(username);
		log.debug("로그인한 사용자 {}", userVO);
		if(userVO == null) {
			throw new UsernameNotFoundException(username+ " 이 없음");
		}
		
		String encPassword = userVO.getPassword();
		
		if(!passwordEncoder.matches(password, encPassword)) {
			throw new BadCredentialsException("비밀번호 오류!");
		}
		
		List<AuthorityVO> authList = userDao.select_auths(username);
		List<GrantedAuthority> grantList = new ArrayList<>();
		
		for(AuthorityVO vo : authList) {
			grantList.add(new SimpleGrantedAuthority(vo.getAuthority()));
		}

		return new UsernamePasswordAuthenticationToken
				(userVO, null, grantList);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
