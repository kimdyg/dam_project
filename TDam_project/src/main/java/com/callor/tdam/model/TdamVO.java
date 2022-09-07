package com.callor.tdam.model;

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
public class TdamVO {

	private long t_seq;
	private String t_username;
	private String t_sdate;
	private String t_stime;
	private String t_content;
	private String t_edate;
	private String t_etime;
	
	private String t_title;
	private String t_place;
	
	// boolean tpye 의 기본값은 false 라는 것을 확인
	private boolean t_complete;
	private String gdsThumbImg;
	private String gdsImg;
	private String ogName;
}
