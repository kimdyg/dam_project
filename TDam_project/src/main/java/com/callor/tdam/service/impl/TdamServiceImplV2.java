package com.callor.tdam.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.callor.tdam.model.SearchPage;
import com.callor.tdam.model.TdamVO2;
import com.callor.tdam.persistance.TdamDao2;
import com.callor.tdam.service.TdamService2;

@Service
public class TdamServiceImplV2 implements TdamService2{

	@Autowired
	private TdamDao2 todoDao2;
	@Override
	public List<TdamVO2> findByUsername(String username) {
		return todoDao2.findByUsername(username);
	}

	@Override
	public List<TdamVO2> selectAll() {
		return todoDao2.selectAll();
	}

	@Override
	public int insert(TdamVO2 vo) {

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeForm = new SimpleDateFormat("HH:mm:SS");
		vo.setT_sdate(dateForm.format(date));
		vo.setT_stime(timeForm.format(date));
		return todoDao2.insert(vo);
	}

	@Override
	public int update(TdamVO2 vo) {
		return todoDao2.update(vo);
	}


	@Override
	public TdamVO2 findById(Long id) {
		return todoDao2.findById(id);
	}

	@Override
	public int delete(TdamVO2 vo) {
		return todoDao2.delete(vo);
	}

	@Override
	public void create_todo_table2() {
		
	}

	@Override
	public int todoComp(String t_seq2) {

		long seq = 0L;
		try {
			seq = Long.valueOf(t_seq2);
		} catch (Exception e) {
			return -1;
		}
		
		TdamVO2 todoVO2 = todoDao2.findById(seq);
		if(todoVO2 == null) {
			return -1;
		}
		String edate = todoVO2.getT_edate();
		if(edate == null || edate.isEmpty()) {
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
			
			todoVO2.setT_edate(dateTime.format(dateFormat));
			todoVO2.setT_etime(dateTime.format(timeFormat));
		} else {
			todoVO2.setT_edate("");
			todoVO2.setT_etime("");
		}
		todoVO2.setT_complete(!todoVO2.isT_complete());
		int ret = todoDao2.update(todoVO2);
		
		return ret;
	}

	@Override
	public TdamVO2 getTodo(long t_seq2) {
		
		return todoDao2.getTodo(t_seq2);
	}
	public List<TdamVO2> searchAndPage2(SearchPage searchPage) {
		return todoDao2.searchAndPage2(searchPage);
	}

	private static final long LIST_PER_PAGE = 10;
	
	private static final long PAGE_NO_COUNT = 10;
	
	@Override
	public void searchAndPage2(Model model, SearchPage searchPage) {
		
		searchPage.setOffset(0);
		searchPage.setLimit(todoDao2.selectAll().size());
		
		String search = searchPage.getSearch();
		search = search == null ? "" : search;
		searchPage.setSearch(search);
		
		List<TdamVO2> addrList = todoDao2.searchAndPage2(searchPage);

		long totalCount = addrList.size();
		if(totalCount < 1) return;
		
		long finalPageNo = 0;
		
		if(totalCount %LIST_PER_PAGE == 0) {
			finalPageNo = totalCount / LIST_PER_PAGE;
		}else {
			finalPageNo = (totalCount / LIST_PER_PAGE) + 1;
		}
		
		long currentPageNo = searchPage.getCurrentPageNo();
		
		if(currentPageNo > finalPageNo) {
			currentPageNo = finalPageNo;
		}
		if(currentPageNo < 1) {
			currentPageNo = 1;
		}
		
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
		searchPage.setTotalCount(todoDao2.selectAll().size());

		model.addAttribute("PAGE2",searchPage);
		
	}





}
