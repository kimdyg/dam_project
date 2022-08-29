package com.callor.todo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.callor.todo.model.TodoVO;
import com.callor.todo.model.TodoVO2;
import com.callor.todo.model.UserVO;
import com.callor.todo.service.TodoService2;
import com.callor.todo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TodoService2 todoService2;
	
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
	public String mypage(Principal principal, String error, Model model) {
		
		String username = principal.getName();
		
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		
		List<TodoVO> todoList = userService.getTodoList(username);
		List<TodoVO2> todoList2 = userService.getTodoList2(username);
		// List<TodoVO2> todoList2 = todoService2.findByUsername(username);
		
		model.addAttribute("TODOS",todoList);
		model.addAttribute("TODOS2",todoList2);
		
		model.addAttribute("LAYOUT","MY_PAGE");
		model.addAttribute("error",error);
		return "home";
	}
	
}
