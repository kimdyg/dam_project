package com.callor.tdam.persistance;

import java.util.List;

import com.callor.tdam.model.SearchPage;
import com.callor.tdam.model.TdamVO2;

public interface TdamDao2 extends  GenericDao<TdamVO2, Long>{
	
	public List<TdamVO2> findByUsername(String username);
	public void create_todo_table2();
	public TdamVO2 getTodo(long t_seq2);
	public List<TdamVO2> searchAndPage2(SearchPage searchPage);


}
