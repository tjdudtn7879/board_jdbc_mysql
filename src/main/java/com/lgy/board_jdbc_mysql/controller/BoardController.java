package com.lgy.board_jdbc_mysql.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgy.board_jdbc_mysql.service.BoardContentService;
import com.lgy.board_jdbc_mysql.service.BoardListService;
import com.lgy.board_jdbc_mysql.service.BoardService;
import com.lgy.board_jdbc_mysql.service.BoardWriteService;
import com.lgy.board_jdbc_mysql.util.Constant;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {
	BoardService service;
	public JdbcTemplate template;

//	servlet-context.xml 에 있는 template 객체를 저장(this.template)
	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
//		Constant.template 를 dao 에서 사용
		Constant.template = this.template;
	}
	
	@RequestMapping("/list")
	public String list(Model model) {
		log.info("@# list");
		
		service=new BoardListService();
		service.execute(model);
		
		return "list";
	}
	
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model) {
		log.info("@# write");
		
		model.addAttribute("request", request);
		
		service=new BoardWriteService();
		service.execute(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/write_view")
	public String write_view() {
		log.info("@# write_view");
		
		return "write_view";
	}
	
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		log.info("@# content_view");
		
		model.addAttribute("request", request);
		service = new BoardContentService();
		service.execute(model);
		
		return "content_view";
	}
	
	@RequestMapping("/modify")
	public String modify(HttpServletRequest request, Model model) {
		log.info("@# modify");
		
		model.addAttribute("request", request);
//		service = new BoardModifyService();
//		service.execute(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		log.info("@# delete");
		
		model.addAttribute("request", request);
//		service = new BoardDeleteService();
//		service.execute(model);
		
		return "redirect:list";
	}
	
}
















