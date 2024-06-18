package com.lgy.board_jdbc_mysql.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.lgy.board_jdbc_mysql.dao.BoardDAO;


//게시글 저장
public class BoardWriteService implements BoardService{

	@Override
	public void execute(Model model) {
		Map<String, Object>map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String boardName = request.getParameter("boardName");
		String boardTitle = request.getParameter("boardTitle");
		String boardContent = request.getParameter("boardContent");
		
		BoardDAO dao=new BoardDAO();
		dao.write(boardName, boardTitle, boardContent);
	}
	
}
