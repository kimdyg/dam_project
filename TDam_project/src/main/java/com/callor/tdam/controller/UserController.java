package com.callor.tdam.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.callor.tdam.model.AuthorityVO;
import com.callor.tdam.model.TdamVO;
import com.callor.tdam.model.TdamVO2;
import com.callor.tdam.model.UserVO;
import com.callor.tdam.service.TdamService;
import com.callor.tdam.service.TdamService2;
import com.callor.tdam.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TdamService tdamService;
	@Autowired
	private TdamService2 tdamService2;
	
	@RequestMapping(value = "/join",method=RequestMethod.GET)
	public String join(Model model) {
		model.addAttribute("LAYOUT", "JOIN");
		return "home";
	}
	
	@RequestMapping(value = "/join",method=RequestMethod.POST)
	public String join(UserVO userVO) {
		
		log.debug(" 회원가입정보 {}",userVO);
		userService.insert(userVO);
		return "redirect:/";
	}
	@RequestMapping(value = "/login",method=RequestMethod.GET)
	public String login(String error, Model model) {
		model.addAttribute("LAYOUT","LOGIN");
		model.addAttribute("error",error);
		return "home";
	}
	
	@RequestMapping(value = "/mypage",method=RequestMethod.GET)
	public String mypage(Principal principal, String error, Model model,
			@RequestParam(name = "tabValue",
			required = false, defaultValue = "1") int tabValue) {
		
		String username = principal.getName();
		
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		
		List<TdamVO> todoList = userService.getTodoList(username);
		List<TdamVO2> todoList2 = userService.getTodoList2(username);
		
		model.addAttribute("TODOS",todoList);
		model.addAttribute("TODOS2",todoList2);
		
		model.addAttribute("LAYOUT","MY_PAGE");
		model.addAttribute("error",error);
		
		
		return "home";
	}
	@RequestMapping(value = "/view", method=RequestMethod.GET)
	public String view(Principal principal, @RequestParam(required=false) Long t_seq, Model model, AuthorityVO authVO) {
		
		log.debug("수신된데이터 {}");
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		
		TdamVO todoVO = tdamService.getTodo(t_seq);
		
		model.addAttribute("todo", todoVO);
		model.addAttribute("LAYOUT","TODO_VIEW");
		
		return "home";
	}
	@RequestMapping(value = "/view2", method=RequestMethod.GET)
	public String view2(Principal principal, @RequestParam(required=false) Long t_seq2, Model model) {
		
		log.debug("수신된데이터 {}");
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		
		TdamVO2 todoVO2 = tdamService2.getTodo(t_seq2);
		
		model.addAttribute("todo2", todoVO2);
		model.addAttribute("LAYOUT","TODO_VIEW2");
		
		return "home";
	}
}
