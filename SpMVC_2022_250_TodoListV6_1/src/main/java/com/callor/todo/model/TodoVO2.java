package com.callor.todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TodoVO2 {

	private long t_seq2;
	private String t_username;
	private String t_sdate;
	private String t_stime;
	private String t_content2;
	private String t_edate;
	private String t_etime;
	
	private String t_title2;
	private String t_place2;
	
	// boolean tpye 의 기본값은 false 라는 것을 확인
	private boolean t_complete;
	private String gdsThumbImg2;
	private String gdsImg2;
}
