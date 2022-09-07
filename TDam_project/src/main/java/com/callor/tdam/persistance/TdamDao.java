package com.callor.tdam.persistance;

import java.util.List;

import com.callor.tdam.model.SearchPage;
import com.callor.tdam.model.TdamVO;

public interface TdamDao extends  GenericDao<TdamVO, Long>{
	
	public List<TdamVO> findByUsername(String username);
	public void create_todo_table();
	public TdamVO getTodo(long t_seq);
	public List<TdamVO> searchAndPage(SearchPage searchPage);

}
