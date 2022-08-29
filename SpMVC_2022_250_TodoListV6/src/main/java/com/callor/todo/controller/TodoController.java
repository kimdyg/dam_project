package com.callor.todo.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.callor.todo.model.SearchPage;
import com.callor.todo.model.TodoVO;
import com.callor.todo.model.TodoVO2;
import com.callor.todo.service.TodoService;
import com.callor.todo.service.TodoService2;
import com.callor.todo.utils.UploadFileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/todo")
public class TodoController {
	
	private final TodoService todoService;
	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}
	
	@Autowired
	private TodoService2 todoService2;
	
	@Autowired
	private String uploadPath;
	
	@RequestMapping(value = {"/",""},method=RequestMethod.GET)
	public String home(Principal principal, Model model, @RequestParam(name = "pageno",
			required = false, defaultValue = "1") int pageno,
			SearchPage searchPage,SearchPage searchPage2) {
		
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		SearchPage searchPage1 = SearchPage.builder()
				.t_username("")
				.limit(10)
				.offset(pageno * 10)
				.build();
		SearchPage searchpage2 = SearchPage.builder()
				.t_username("")
				.limit(10)
				.offset(pageno * 10)
				.build();
		
		searchPage1.setCurrentPageNo(pageno);
		// 페이지 계산
		todoService.searchAndPage(model,searchPage1);
		todoService2.searchAndPage2(model,searchpage2);
		
		// 데이터 가져오기
		List<TodoVO> todoList = todoService.searchAndPage(searchPage1);
		List<TodoVO2> todoList2 = todoService2.searchAndPage2(searchpage2);
		//model.addAttribute("ADDRS", addrList);
//		List<TodoVO> todoList = todoService.selectAll(); 
//		List<TodoVO2> todoList2 = todoService2.selectAll();
//		
		model.addAttribute("LAYOUT","TODO_LIST");
		model.addAttribute("TODOS",todoList);
		model.addAttribute("TODOS2",todoList2);
		
		return "home";
	}
	
	@RequestMapping(value = "/insert", method=RequestMethod.POST)
	public String insert(Principal principal, TodoVO todoVO, Model model, MultipartHttpServletRequest request) throws IOException, Exception  {
		
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		MultipartFile file = request.getFile("file"); 
		
		String imgUploadPath = uploadPath + File.separator + "imgUpload";
		String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
		String fileName = null;
		if(file != null) {
		   fileName =  UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);   
		   todoVO.setGdsImg(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
		   todoVO.setGdsThumbImg(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
		   // String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		   todoVO.setOgName(file.getOriginalFilename());
		}
		todoVO.setT_username(username);
		
		todoService.insert(todoVO);
		
		List<TodoVO> todoList = todoService.findByUsername(username);
		List<TodoVO2> todoList2 = todoService2.findByUsername(username);
		
		model.addAttribute("TODOS",todoList);
		model.addAttribute("TODOS2",todoList2);
		
		model.addAttribute("LAYOUT","TODO_LIST");
		
		return "home";
	}
	
	@RequestMapping(value = "/insert2", method=RequestMethod.POST)
	public String insert2(Principal principal,TodoVO2 todoVO2, Model model, MultipartHttpServletRequest request) throws IOException, Exception  {
		
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		MultipartFile file = request.getFile("file"); 
		
		String imgUploadPath = uploadPath + File.separator + "imgUpload";
		String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
		String fileName = null;
		if(file != null) {
			fileName =  UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);   
			todoVO2.setGdsImg2(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
			todoVO2.setGdsThumbImg2(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
			// String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			todoVO2.setOgName2(file.getOriginalFilename());
		}
		todoVO2.setT_username(username);
		
		todoService2.insert(todoVO2);
		
		List<TodoVO> todoList = todoService.findByUsername(username);
		List<TodoVO2> todoList2 = todoService2.findByUsername(username);
		
		model.addAttribute("TODOS",todoList);
		model.addAttribute("TODOS2",todoList2);
		model.addAttribute("LAYOUT","TODO_LIST");
		model.addAttribute("tabValue","2");
		
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
		
		String imgUploadPath = uploadPath + File.separator + "imgUpload";
		String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
		String fileName = null;
		
		if(file != null) {
			fileName =  UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);   
		} else {
			fileName = uploadPath + File.separator + "images" + File.separator + "none.png";
		}
		
		
		todoVO2.setGdsImg2(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
		todoVO2.setGdsThumbImg2(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
		
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
	@RequestMapping(value = "/insertFrm2", method=RequestMethod.GET)
	public String insertFrm2(Principal principal, @RequestParam(required=false) Long t_seq2, Model model) {
		
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		
		if(t_seq2 != null) {
			TodoVO2 todoVO2 = todoService2.getTodo(t_seq2);
			model.addAttribute("todo2",todoVO2);
		}
		
		model.addAttribute("LAYOUT","TODO_WRITE2");
		
		return "home";
	}
	@RequestMapping(value = "/update",method=RequestMethod.POST)
	public String update(TodoVO todoVO, MultipartFile file, Model model) throws Exception {
		
		String imgUploadPath = uploadPath + File.separator + "imgUpload";
		String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
		String fileName = null;

		if(file != null) {
		   fileName =  UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);   
		} else {
		   fileName = uploadPath + File.separator + "images" + File.separator + "none.png";
		}

		todoVO.setGdsImg(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
		todoVO.setGdsThumbImg(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
		log.debug("수신된 데이터 {}",todoVO);
		todoService.update(todoVO);
		
		return "redirect:/todo";
	}
	@RequestMapping(value = "/update2",method=RequestMethod.POST)
	public String update2(TodoVO2 todoVO2, MultipartFile file, Model model) throws Exception {
		
		String imgUploadPath = uploadPath + File.separator + "imgUpload";
		String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
		String fileName = null;
		
		if(file != null) {
			fileName =  UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);   
		} else {
			fileName = uploadPath + File.separator + "images" + File.separator + "none.png";
		}
		
		todoVO2.setGdsImg2(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
		todoVO2.setGdsThumbImg2(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
		log.debug("수신된 데이터 {}",todoVO2);
		todoService2.update(todoVO2);
		
		return "redirect:/todo";
	}
	@RequestMapping(value = "/comp",method=RequestMethod.GET)
	public String comp(String t_seq, Model model) {
		
		int ret = todoService.todoComp(t_seq);
		return "redirect:/todo";
	}
	



	
	@RequestMapping(value = "/view", method=RequestMethod.GET)
	public String view(Principal principal, @RequestParam(required=false) Long t_seq, Model model) {
		
		log.debug("수신된데이터 {}");
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		
		TodoVO todoVO = todoService.getTodo(t_seq);
		
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
		
		TodoVO2 todoVO2 = todoService2.getTodo(t_seq2);
		
		model.addAttribute("todo2", todoVO2);
		model.addAttribute("LAYOUT","TODO_VIEW2");
		
		return "home";
	}
  }
