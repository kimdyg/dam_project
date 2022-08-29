package com.callor.todo.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.callor.todo.model.SearchPage;
import com.callor.todo.model.TodoVO;
import com.callor.todo.persistance.TodoDao;
import com.callor.todo.service.TodoService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class TodoServiceImplV1 implements TodoService{

	@Autowired
	private TodoDao todoDao;
	
	// username 사용자의 todoList Dao  로부터 SELECT 하여
	// 즉시 return
	@Override
	public List<TodoVO> findByUsername(String username) {
		// TODO Auto-generated method stub
		return todoDao.findByUsername(username);
	}

	@Override
	public List<TodoVO> selectAll() {
		// TODO Auto-generated method stub
		return todoDao.selectAll();
	}

	@Override
	public int insert(TodoVO vo) {

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeForm = new SimpleDateFormat("HH:mm:SS");
		vo.setT_sdate(dateForm.format(date));
		vo.setT_stime(timeForm.format(date));
		return todoDao.insert(vo);
	}

	@Override
	public int update(TodoVO vo) {
		// TODO Auto-generated method stub
		return todoDao.update(vo);
	}


	@Override
	public TodoVO findById(Long id) {
		// TODO Auto-generated method stub
		return todoDao.findById(id);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void create_todo_table() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int todoComp(String t_seq) {

		long seq = 0L;
		try {
			seq = Long.valueOf(t_seq);
		} catch (Exception e) {
			return -1;
		}
		
		TodoVO todoVO = todoDao.findById(seq);
		if(todoVO == null) {
			return -1;
		}
		String edate = todoVO.getT_edate();
		if(edate == null || edate.isEmpty()) {
			// java 1.8 이상에서 사용하는 새로운 날짜 시간 클래스
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
			
			todoVO.setT_edate(dateTime.format(dateFormat));
			todoVO.setT_etime(dateTime.format(timeFormat));
		} else {
			todoVO.setT_edate("");
			todoVO.setT_etime("");
		}
		/*
		 * VO 의 변수가 boolean type 일 경우
		 * set method 는 일반적인 setter method pattern 을 따르는데
		 * get method 는 is변수명() 형태의 pattern 으로 변경된다
		 */
		todoVO.setT_complete(!todoVO.isT_complete());
		int ret = todoDao.update(todoVO);
		
		return ret;
	}

	@Override
	public TodoVO getTodo(long t_seq) {
		
		return todoDao.getTodo(t_seq);
	}
	@Override
	public List<TodoVO> searchAndPage(SearchPage searchPage) {
		return todoDao.searchAndPage(searchPage);
	}

	// 한 페이지에 보여질 데이터 리스트 개수
	private static final long LIST_PER_PAGE = 10;
	
	// 화면 하단에 페이지 번호 출력 개수
	private static final long PAGE_NO_COUNT = 10;
	
	@Override
	public void searchAndPage(Model model, SearchPage searchPage) {
		
		// pagination을 구현하기 위하여 전체 데이터 가져오기로
		// 개수를 임시로 세팅 
		searchPage.setOffset(0);
		searchPage.setLimit(todoDao.selectAll().size());
		
		// 1. 전체 데이터 개수가 몇개인지를 알아야 pagination 을 구현할수 있다
		// 		화면 하단의 페이지 번호를 자동으로 계산하기 위함이다
		
		// 검색어가 없이 요청이 될경우 SearchPage 객체의 search 값이 null 이되어
		// 데이터가 검색이 되지 않는다
		// 때문에 SearchPage 객체의 search 변수값을 "" 으로 세팅
		String search = searchPage.getSearch();
		search = search == null ? "" : search;
		searchPage.setSearch(search);
		
		// 검색어 조건에 맞는 모든 데이터를 일단 select
		List<TodoVO> addrList = todoDao.searchAndPage(searchPage);

		long totalCount = addrList.size();
		if(totalCount < 1) return;
		
		// 전체 데이터의 총 페이지수
		// 전체 데이터 개수를 한 페이지에 보여질 데이터 개수로 나누기
		// 		만약 전체 데이터가 37 개이고 한페이지에 10개를 보여준다 면
		//  	finalPageNo 는 3이 될 것이다
		long finalPageNo = totalCount / LIST_PER_PAGE;
		
		// 화면 하단의 페이지번호를 클릭했을때 전달되는 값
		long currentPageNo = searchPage.getCurrentPageNo();
		
		// 일반적인 if 문을 사용할때
		if(currentPageNo > finalPageNo) {
			currentPageNo = finalPageNo;
		}
		if(currentPageNo < 1) {
			currentPageNo = 1;
		}
		
		// 선택된 페이지번호에 따라 화면하단 번호 리스트를 유동적으로
		// 보여주기 위한 연산
		long startPageNo = (currentPageNo  / PAGE_NO_COUNT) * PAGE_NO_COUNT;
		startPageNo = startPageNo < 1 ? 1 : startPageNo;
		
		long endPageNo = startPageNo + PAGE_NO_COUNT - 1;
		endPageNo = endPageNo > finalPageNo ? finalPageNo : endPageNo;
		
		searchPage.setStartPageNo(startPageNo);
		searchPage.setEndPageNo(endPageNo);
		searchPage.setLimit(LIST_PER_PAGE);
		searchPage.setPageNoCount(PAGE_NO_COUNT);
		searchPage.setOffset( (currentPageNo - 1) * PAGE_NO_COUNT);
		searchPage.setFinalPageNo(finalPageNo);
		
		// JSP 로 보내기 위해서 model 에 담기
		model.addAttribute("PAGE",searchPage);
		
		
	}

}
