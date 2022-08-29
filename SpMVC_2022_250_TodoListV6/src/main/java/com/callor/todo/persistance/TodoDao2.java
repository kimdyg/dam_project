package com.callor.todo.persistance;

import java.util.List;

import com.callor.todo.model.SearchPage;
import com.callor.todo.model.TodoVO2;

public interface TodoDao2 extends  GenericDao<TodoVO2, Long>{
	
	// username 을 매개변수로 받아
	// username 사용자의 todoList 를 return
	public List<TodoVO2> findByUsername(String username);
	public void create_todo_table2();
	public TodoVO2 getTodo(long t_seq2);
	public List<TodoVO2> searchAndPage2(SearchPage searchPage);


}
