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
public class SearchPage {
	
	private String t_username;
	private String search;
	
	private long totalCount; // 전체 데이터 개수
	
	private long limit; // 한페이지의 개수
	private long offset; // 첫페이지 번호
	
	private long listPerPage; // 한페이지에 보여질 데이터 개수
	private long pageNoCount; // 하단에 보여질 페이지 번호 개수
	
	private long firstPageNo; // 전체데이터의 첫페이지 번호
	private long finalPageNo; // 전체데이터의 마지막 페이지 번호
	
	private long startPageNo; // 보여질 리스트의 시작페이지 번호
	private long endPageNo; // 
	private long currentPageNo;// 현재 보여지고 있는 페이지 번호

}