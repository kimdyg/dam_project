package com.callor.todo.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callor.todo.model.TodoVO2;
import com.callor.todo.persistance.TodoDao2;
import com.callor.todo.service.TodoService2;

@Service
public class TodoServiceImplV2 implements TodoService2{

	@Autowired
	private TodoDao2 todoDao2;
	
	// username 사용자의 todoList Dao  로부터 SELECT 하여
	// 즉시 return
	@Override
	public List<TodoVO2> findByUsername(String username) {
		// TODO Auto-generated method stub
		return todoDao2.findByUsername(username);
	}

	@Override
	public List<TodoVO2> selectAll() {
		// TODO Auto-generated method stub
		return todoDao2.selectAll();
	}

	@Override
	public int insert(TodoVO2 vo) {

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeForm = new SimpleDateFormat("HH:mm:SS");
		vo.setT_sdate(dateForm.format(date));
		vo.setT_stime(timeForm.format(date));
		return todoDao2.insert(vo);
	}

	@Override
	public int update(TodoVO2 vo) {
		// TODO Auto-generated method stub
		return todoDao2.update(vo);
	}


	@Override
	public TodoVO2 findById(Long id) {
		// TODO Auto-generated method stub
		return todoDao2.findById(id);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void create_todo_table2() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int todoComp(String t_seq2) {

		long seq = 0L;
		try {
			seq = Long.valueOf(t_seq2);
		} catch (Exception e) {
			return -1;
		}
		
		TodoVO2 todoVO2 = todoDao2.findById(seq);
		if(todoVO2 == null) {
			return -1;
		}
		String edate = todoVO2.getT_edate();
		if(edate == null || edate.isEmpty()) {
			// java 1.8 이상에서 사용하는 새로운 날짜 시간 클래스
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
			
			todoVO2.setT_edate(dateTime.format(dateFormat));
			todoVO2.setT_etime(dateTime.format(timeFormat));
		} else {
			todoVO2.setT_edate("");
			todoVO2.setT_etime("");
		}
		/*
		 * VO 의 변수가 boolean type 일 경우
		 * set method 는 일반적인 setter method pattern 을 따르는데
		 * get method 는 is변수명() 형태의 pattern 으로 변경된다
		 */
		todoVO2.setT_complete(!todoVO2.isT_complete());
		int ret = todoDao2.update(todoVO2);
		
		return ret;
	}

	@Override
	public TodoVO2 getTodo(long t_seq2) {
		
		return todoDao2.getTodo(t_seq2);
	}


}
