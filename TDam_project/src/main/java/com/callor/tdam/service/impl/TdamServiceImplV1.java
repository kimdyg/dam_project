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
import com.callor.tdam.model.TdamVO;
import com.callor.tdam.persistance.TdamDao;
import com.callor.tdam.service.TdamService;
@Service
public class TdamServiceImplV1 implements TdamService{

	@Autowired
	private TdamDao todoDao;
	
	@Override
	public List<TdamVO> findByUsername(String username) {
		return todoDao.findByUsername(username);
	}

	@Override
	public List<TdamVO> selectAll() {
		return todoDao.selectAll();
	}

	@Override
	public int insert(TdamVO vo) {

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeForm = new SimpleDateFormat("HH:mm:SS");
		vo.setT_sdate(dateForm.format(date));
		vo.setT_stime(timeForm.format(date));
		return todoDao.insert(vo);
	}

	@Override
	public int update(TdamVO vo) {
		return todoDao.update(vo);
	}


	@Override
	public TdamVO findById(Long id) {
		return todoDao.findById(id);
	}

	@Override
	public int delete(TdamVO vo) {
		return todoDao.delete(vo);
	}

	@Override
	public void create_todo_table() {
		
	}

	@Override
	public int todoComp(String t_seq) {

		long seq = 0L;
		try {
			seq = Long.valueOf(t_seq);
		} catch (Exception e) {
			return -1;
		}
		
		TdamVO todoVO = todoDao.findById(seq);
		if(todoVO == null) {
			return -1;
		}
		String edate = todoVO.getT_edate();
		if(edate == null || edate.isEmpty()) {
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
			
			todoVO.setT_edate(dateTime.format(dateFormat));
			todoVO.setT_etime(dateTime.format(timeFormat));
		} else {
			todoVO.setT_edate("");
			todoVO.setT_etime("");
		}
		todoVO.setT_complete(!todoVO.isT_complete());
		int ret = todoDao.update(todoVO);
		
		return ret;
	}

	@Override
	public TdamVO getTodo(long t_seq) {
		
		return todoDao.getTodo(t_seq);
	}
	@Override
	public List<TdamVO> searchAndPage(SearchPage searchPage) {
		return todoDao.searchAndPage(searchPage);
	}

	private static final long LIST_PER_PAGE = 10;
	
	private static final long PAGE_NO_COUNT = 10;
	
	@Override
	public void searchAndPage(Model model, SearchPage searchPage) {
		
		searchPage.setOffset(0);
		searchPage.setLimit(todoDao.selectAll().size());
		
		String search = searchPage.getSearch();
		search = search == null ? "" : search;
		searchPage.setSearch(search);
		
		List<TdamVO> addrList = todoDao.searchAndPage(searchPage);

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
		searchPage.setTotalCount(todoDao.selectAll().size());
		
		model.addAttribute("PAGE",searchPage);
		
		
	}

}
