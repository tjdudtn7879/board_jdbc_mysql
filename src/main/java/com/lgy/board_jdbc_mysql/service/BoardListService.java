package com.lgy.board_jdbc_mysql.service;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.lgy.board_jdbc_mysql.dao.BoardDAO;
import com.lgy.board_jdbc_mysql.dto.BoardDTO;

public class BoardListService implements BoardService{

	@Override
	public void execute(Model model) {
		BoardDAO dao=new BoardDAO();
		ArrayList<BoardDTO> dtos = dao.list();
		model.addAttribute("list", dtos);
	}

}















