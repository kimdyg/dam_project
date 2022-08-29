package com.callor.todo.service;

import java.util.List;

import com.callor.todo.model.TodoVO2;
import com.callor.todo.persistance.TodoDao2;

// Generic 에 선언된 기본 CRUD 메서드와 TodoDao 선언된 
// finByUsername 메서드를 상속받게된다
public interface TodoService2 extends TodoDao2 {
	
	// todo항목을 완료하는 method 가 프로젝트 진행중에 필요하게 되었다
	// interface 의 설계가 변경될텐데
	//		이 인터페이스를 상속받은 모든 클래스에 영향이 미친다

	public int todoComp(String seq);

	public TodoVO2 getTodo(long t_seq2);

	
}
