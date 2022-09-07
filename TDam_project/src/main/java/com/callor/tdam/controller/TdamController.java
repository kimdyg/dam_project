package com.callor.tdam.controller;

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

import com.callor.tdam.model.AuthorityVO;
import com.callor.tdam.model.SearchPage;
import com.callor.tdam.model.TdamVO;
import com.callor.tdam.model.TdamVO2;
import com.callor.tdam.service.TdamService;
import com.callor.tdam.service.TdamService2;
import com.callor.tdam.utils.UploadFileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/todo")
public class TdamController {
	
	private final TdamService todoService;
	public TdamController(TdamService todoService) {
		this.todoService = todoService;
	}
	
	@Autowired
	private TdamService2 todoService2;
	
	@Autowired
	private String uploadPath;
	
	@RequestMapping(value = {"/",""},method=RequestMethod.GET)
	public String home(Principal principal, Model model, 
			@RequestParam(name = "pageno",
			required = false, defaultValue = "1") int pageno,
			@RequestParam(name = "pageno2",
			required = false, defaultValue = "1") int pageno2,
			@RequestParam(name = "tabValue",
			required = false, defaultValue = "1") int tabValue,
			SearchPage searchPage, SearchPage searchPage2) {
		
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		SearchPage searchPage1 = SearchPage.builder()
				.t_username("")
				.limit(10)
				.offset(pageno * 10)
				.build();
		SearchPage searchPage_2 = SearchPage.builder()
				.t_username("")
				.limit(10)
				.offset(pageno2 * 10)
				.build();
		
		searchPage1.setCurrentPageNo(pageno);
		searchPage_2.setCurrentPageNo(pageno2);

		todoService.searchAndPage(model,searchPage1);
		todoService2.searchAndPage2(model,searchPage_2);
		
		List<TdamVO> todoList = todoService.searchAndPage(searchPage1);
		List<TdamVO2> todoList2 = todoService2.searchAndPage2(searchPage_2);
		
		model.addAttribute("LAYOUT","TODO_LIST");
		model.addAttribute("TODOS",todoList);
		model.addAttribute("TODOS2",todoList2);
		model.addAttribute("tabValue", tabValue);
		
		return "home";
	}
	
	@RequestMapping(value = "/insert", method=RequestMethod.POST)
	public String insert(Principal principal, TdamVO todoVO, Model model, MultipartHttpServletRequest request) throws IOException, Exception  {
		
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		MultipartFile file = request.getFile("file"); 
		
		if(file.getSize() != 0) {
			String imgUploadPath = uploadPath + File.separator + "imgUpload";
			String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
			String fileName = null;
		   fileName =  UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);   
		   todoVO.setGdsImg(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
		   todoVO.setGdsThumbImg(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
		}
		todoVO.setOgName(file.getOriginalFilename());
		todoVO.setT_username(username);
		
		todoService.insert(todoVO);
		
		List<TdamVO> todoList = todoService.findByUsername(username);
		
		model.addAttribute("TODOS",todoList);
		
		model.addAttribute("LAYOUT","TODO_LIST");
		
		return "redirect:/todo";
	}
	
	@RequestMapping(value = "/insert2", method=RequestMethod.POST)
	public String insert2(Principal principal,TdamVO2 todoVO2, Model model, MultipartHttpServletRequest request) throws IOException, Exception  {
		
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		MultipartFile file = request.getFile("file"); 
		
		if(file.getSize() != 0) {
			String imgUploadPath = uploadPath + File.separator + "imgUpload";
			String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
			String fileName = null;
			fileName =  UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);   
			todoVO2.setGdsImg2(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
			todoVO2.setGdsThumbImg2(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
		}
		todoVO2.setOgName2(file.getOriginalFilename());
		todoVO2.setT_username(username);
		
		todoService2.insert(todoVO2);
		
		List<TdamVO2> todoList2 = todoService2.findByUsername(username);
		
		model.addAttribute("TODOS2",todoList2);
		model.addAttribute("LAYOUT","TODO_LIST");
		
		return "redirect:/todo?tabValue=2";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete(Principal principal, TdamVO todoVO, TdamVO2 todoVO2)throws Exception {
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		
		todoVO.setT_username(username);
		todoService.delete(todoVO);
		
		return "redirect:/todo";
	}
	@RequestMapping(value="/delete2",method=RequestMethod.GET)
	public String delete(Principal principal,TdamVO2 todoVO2, Model model)throws Exception {
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		
		todoVO2.setT_username(username);
		todoService2.delete(todoVO2);

		return "redirect:/todo?tabValue=2";
	}
	
	@RequestMapping(value = "/insertFrm", method=RequestMethod.GET)
	public String insertFrm(Principal principal, @RequestParam(required=false) Long t_seq, Model model) {
		
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		
		if(t_seq != null) {
			TdamVO todoVO = todoService.getTodo(t_seq);
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
			TdamVO2 todoVO2 = todoService2.getTodo(t_seq2);
			model.addAttribute("todo2",todoVO2);
		}
		
		model.addAttribute("LAYOUT","TODO_WRITE2");
		
		return "home";
	}
	@RequestMapping(value = "/update",method=RequestMethod.POST)
	public String update(TdamVO todoVO, MultipartFile file, Model model) throws Exception {

		if(file.getSize() != 0) {
			String imgUploadPath = uploadPath + File.separator + "imgUpload";
			String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
			String fileName = null;
		   fileName =  UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);   
		   todoVO.setGdsImg(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
		   todoVO.setGdsThumbImg(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
		} 

		log.debug("수신된 데이터 {}",todoVO);
		todoService.update(todoVO);
		
		return "redirect:/todo";
	}
	@RequestMapping(value = "/update2",method=RequestMethod.POST)
	public String update2(TdamVO2 todoVO2, MultipartFile file, Model model) throws Exception {
		
		if(file.getSize() != 0) {
			String imgUploadPath = uploadPath + File.separator + "imgUpload";
			String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
			String fileName = null;
			fileName =  UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);   
			todoVO2.setGdsImg2(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
			todoVO2.setGdsThumbImg2(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
		}
		
		log.debug("수신된 데이터 {}",todoVO2);
		todoService2.update(todoVO2);
		
		return "redirect:/todo?tabValue=2";
	}
	@RequestMapping(value = "/comp",method=RequestMethod.GET)
	public String comp(String t_seq, Model model) {
		
		int ret = todoService.todoComp(t_seq);
		return "redirect:/todo";
	}
	



	
	@RequestMapping(value = "/view", method=RequestMethod.GET)
	public String view(Principal principal, @RequestParam(required=false) Long t_seq, Model model, AuthorityVO authVO) {
		
		log.debug("수신된데이터 {}");
		String username = principal.getName();
		if(username == null) {
			return "redirect:/user/login?error=LOGIN_NEED";
		}
		
		TdamVO todoVO = todoService.getTodo(t_seq);
		
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
		
		TdamVO2 todoVO2 = todoService2.getTodo(t_seq2);
		
		model.addAttribute("todo2", todoVO2);
		model.addAttribute("LAYOUT","TODO_VIEW2");
		
		return "home";
	}
	
  }
