package com.callor.tdam.service;

import org.springframework.ui.Model;

import com.callor.tdam.model.SearchPage;
import com.callor.tdam.model.TdamVO2;
import com.callor.tdam.persistance.TdamDao2;

public interface TdamService2 extends TdamDao2 {
	

	public int todoComp(String seq);

	public TdamVO2 getTodo(long t_seq2);
	public void searchAndPage2(Model model,SearchPage searchPage);


	
}
