package com.lgy.board_jdbc_mysql.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.lgy.board_jdbc_mysql.dao.BoardDAO;
import com.lgy.board_jdbc_mysql.dto.BoardDTO;


public class BoardContentService implements BoardService{

	@Override
	public void execute(Model model) {
		Map<String, Object>map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String boardNo = request.getParameter("boardNo");
		
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = dao.contentView(boardNo); 
		model.addAttribute("content_view", dto);
		
		
	}
	
}
