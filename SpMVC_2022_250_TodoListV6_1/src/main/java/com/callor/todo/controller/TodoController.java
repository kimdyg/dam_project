package com.callor.todo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.callor.todo.model.TodoVO;
import com.callor.todo.model.TodoVO2;
import com.callor.todo.service.TodoService;
import com.callor.todo.service.TodoService2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/todo")
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	@Autowired
	private TodoService2 todoService2;
	
	@Autowired
	private String uploadPath;
	
	@RequestMapping(value = {"/",""},method=RequestMethod.GET)
	public String home(Principal principal, Model model) {
		
		// Spring Security Project 에서 로그인한 사용자의
		// username 을 get 하기
		String username = principal.getName();
		
		// 만약 혹시, 로그인된 사용자 정보를 알 수 없으면
		// 로그인 화면으로 redirect
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		
		// 사용자의 username 이 정상이면
		// 데이터 SELECT 하기
		/*
		 * List<TodoVO> todoList = todoService.findByUsername(username); 
		 * List<TodoVO2> todoList2 = todoService2.findByUsername(username);
		 */
		
		List<TodoVO> todoList = todoService.selectAll(); 
		List<TodoVO2> todoList2 = todoService2.selectAll();
		
		model.addAttribute("TODOS",todoList);
		model.addAttribute("TODOS2",todoList2);
		model.addAttribute("LAYOUT","TODO_LIST");
		
		return "home";
	}
	
	@RequestMapping(value = "/insert", method=RequestMethod.POST)
	public String insert(Principal principal, TodoVO todoVO, Model model) {
		
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		todoVO.setT_username(username);
		
		todoService.insert(todoVO);
		
		List<TodoVO> todoList = todoService.findByUsername(username);
		
		model.addAttribute("TODOS",todoList);
		
		model.addAttribute("LAYOUT","TODO_LIST");
		
		return "home";
	}
	
	@RequestMapping(value = {"/",""},method=RequestMethod.POST)
	public String insertToTodoVO(Principal principal, TodoVO todoVO) throws Exception {
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		
		todoVO.setT_username(username);
		todoService.insert(todoVO);
		
		return "redirect:/todo";
	}
	
	@RequestMapping(value = "/",method=RequestMethod.POST)
	public String insertToTodoVO2(Principal principal, MultipartFile file, TodoVO2 todoVO2 ) throws Exception {
		
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		todoVO2.setT_username(username);
		todoService2.insert(todoVO2);
		
		return "redirect:/todo";
	}
	@RequestMapping(value = "/insertFrm", method=RequestMethod.GET)
	public String insertFrm(Principal principal, @RequestParam(required=false) Long t_seq, Model model) {
		
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		
		if(t_seq != null) {
			TodoVO todoVO = todoService.getTodo(t_seq);
			model.addAttribute("todo",todoVO);
		}
		
		
		model.addAttribute("LAYOUT","TODO_WRITE");
		
		return "home";
	}
	
	/*
	 * @RequestMapping(value = "/update",method=RequestMethod.GET) public String
	 * update(String t_seq, Model model) {
	 * 
	 * Long l_seq = 0L; try { l_seq = Long.valueOf(t_seq);
	 * 
	 * } catch (Exception e) { // TODO: handle exception } TodoVO todoVO =
	 * todoService.findById(l_seq); model.addAttribute("TODO",todoVO);
	 * model.addAttribute("LAYOUT","TODO_LIST"); return "home"; }
	 */
	@RequestMapping(value = "/update",method=RequestMethod.POST)
	public String update(TodoVO todoVO, MultipartFile file, Model model) throws Exception {
		log.debug("수신된 데이터 {}",todoVO);
		todoService.update(todoVO);
		
		return "redirect:/todo";
	}
	@RequestMapping(value = "/update", method=RequestMethod.GET)
	public String update(Principal principal, TodoVO todoVO, Model model) {
		
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		
		todoVO.setT_username(username);
		
		todoService.update(todoVO);
		
		List<TodoVO> todoList = todoService.findByUsername(username);
		
		model.addAttribute("TODOS",todoList);
		
		model.addAttribute("LAYOUT","TODO_LIST");
		
		return "redirect:/todo/view?t_seq="+todoVO.getT_seq();
	}
	@RequestMapping(value = "/comp",method=RequestMethod.GET)
	public String comp(String t_seq, Model model) {
		
		int ret = todoService.todoComp(t_seq);
		return "redirect:/todo";
	}
	



	
	@RequestMapping(value = "/view", method=RequestMethod.GET)
	public String view(Principal principal, @RequestParam(required=false) Long t_seq, Model model) {
		
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		
		TodoVO todoVO = todoService.getTodo(t_seq);
		
		model.addAttribute("todo", todoVO);
		model.addAttribute("LAYOUT","TODO_VIEW");
		
		return "home";
	}
}
