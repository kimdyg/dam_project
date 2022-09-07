package com.callor.tdam.service;

import org.springframework.ui.Model;

import com.callor.tdam.model.SearchPage;
import com.callor.tdam.model.TdamVO;
import com.callor.tdam.persistance.TdamDao;

public interface TdamService extends TdamDao {
	

	public int todoComp(String seq);

	public TdamVO getTodo(long t_seq);

	public void searchAndPage(Model model,SearchPage searchPage);
	
}
