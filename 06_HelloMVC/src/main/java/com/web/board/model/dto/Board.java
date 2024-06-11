package com.web.board.model.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {
	
	private int boardNo;
	private String boardTitle;
//	private Member boardWriter;
	private String boardWriter;
	private String boardContent;
	private String originalFileName;
	private String renamedFileName;
	private Date boardDate;
	private int readCount;
	private List<BoardComment> comments = new ArrayList<BoardComment>();
	private int commentCount;

}
