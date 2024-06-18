package com.lgy.board_jdbc_mysql.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.lgy.board_jdbc_mysql.dto.BoardDTO;
import com.lgy.board_jdbc_mysql.util.Constant;

public class BoardDAO {
//	DataSource dataSource;
	JdbcTemplate template=null;
	
	public BoardDAO() {
		/*
		try {
			Context ctx = new InitialContext();
 			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/mysql");
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
//		Constant.template : 컨트롤러에서 설정됨
		template=Constant.template;
	}
	
	public ArrayList<BoardDTO> list() {
//		3.방법 1줄
		return (ArrayList<BoardDTO>) template.query("select boardNo, boardName, boardTitle, boardContent, boardDate, boardHit from tbl_board", new BeanPropertyRowMapper(BoardDTO.class));

		/*
//		2.방법 2줄
		String sql="select boardNo, boardName, boardTitle, boardContent, boardDate, boardHit from tbl_board";
		return (ArrayList<BoardDTO>) template.query(sql, new BeanPropertyRowMapper(BoardDTO.class));
		*/
		/*
		1.방법 4줄
		ArrayList<BoardDTO> dtos=null;
		String sql="select boardNo, boardName, boardTitle, boardContent, boardDate, boardHit from tbl_board";
//		query : jdbc template 쿼리 처리
		dtos = (ArrayList<BoardDTO>) template.query(sql, new BeanPropertyRowMapper(BoardDTO.class));
		
		return dtos;
		*/
		/*
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select boardNo, boardName, boardTitle, boardContent, boardDate, boardHit from tbl_board";
		ArrayList<BoardDTO> dtos=new ArrayList<>();
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int boardNo = rs.getInt("boardNo");
				String boardName = rs.getString("boardName");
				String boardTitle = rs.getString("boardTitle");
				String boardContent = rs.getString("boardContent");
				Timestamp boardDate = rs.getTimestamp("boardDate");
				int boardHit = rs.getInt("boardHit");
				
				BoardDTO dto=new BoardDTO(boardNo, boardName, boardTitle, boardContent, boardDate, boardHit);
				dtos.add(dto);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(rs != null){ rs.close(); }
				if(pstmt != null){ pstmt.close(); }
				if(conn != null){ conn.close(); }
			}catch(Exception se){
				se.printStackTrace();
			}
		}
		
		return dtos;
		*/
	}
	
	
	public void write(final String boardName, final String boardTitle, final String boardContent) {
		
		template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql="insert into tbl_board(boardName, boardTitle, boardContent, boardHit) "
						+ "values(?,?,?,0)";
//				con : 메소드 매개변수 이름 일치
				PreparedStatement pstmt = con.prepareStatement(sql);
//				파라미터 등은 final로 구성
				pstmt.setString(1, boardName);
				pstmt.setString(2, boardTitle);
				pstmt.setString(3, boardContent);
				return pstmt;
			}
		});
		
		/*
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql="insert into tbl_board(boardName, boardTitle, boardContent, boardHit) "
								+ "values(?,?,?,0)";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardName);
			pstmt.setString(2, boardTitle);
			pstmt.setString(3, boardContent);
			pstmt.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(pstmt != null){ pstmt.close(); }
				if(conn != null){ conn.close(); }
			}catch(Exception se){
				se.printStackTrace();
			}
		}
		*/
	}
	
	public BoardDTO contentView(String strID) {
//		upHit(strID);

		String sql="select boardNo, boardName, boardTitle, boardContent, boardDate, boardHit from tbl_board where boardNo="+strID;
		return (BoardDTO)template.queryForObject(sql, new BeanPropertyRowMapper<BoardDTO>(BoardDTO.class));

		
/*
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select boardNo, boardName, boardTitle, boardContent, boardDate, boardHit "
				+ "from tbl_board where boardNo=?";
		BoardDTO dto=null;
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(strID));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int boardNo = rs.getInt("boardNo");
				String boardName = rs.getString("boardName");
				String boardTitle = rs.getString("boardTitle");
				String boardContent = rs.getString("boardContent");
				Timestamp boardDate = rs.getTimestamp("boardDate");
				int boardHit = rs.getInt("boardHit");
				
				dto=new BoardDTO(boardNo, boardName, boardTitle, boardContent, boardDate, boardHit);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(rs != null){ rs.close(); }
				if(pstmt != null){ pstmt.close(); }
				if(conn != null){ conn.close(); }
			}catch(Exception se){
				se.printStackTrace();
			}
		}
		
		return dto;
		*/
	}
	/*
	public void upHit(String boardNo) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql="";
		
		try {
			conn = dataSource.getConnection();
			
			sql="update tbl_board set boardHit=boardHit+1 where boardNo=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(boardNo));
			pstmt.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(pstmt != null){ pstmt.close(); }
				if(conn != null){ conn.close(); }
			}catch(Exception se){
				se.printStackTrace();
			}
		}
	}
	
	public void modify(String boardNo, String boardName, String boardTitle, String boardContent) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql="";
		
		try {
			conn = dataSource.getConnection();
			
			sql="update tbl_board set boardName=?, boardTitle=?, boardContent=? where boardNo=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardName);
			pstmt.setString(2, boardTitle);
			pstmt.setString(3, boardContent);
			
			System.out.println("@# boardNo =>"+boardNo);
			pstmt.setInt(4, Integer.parseInt(boardNo));
			pstmt.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(pstmt != null){ pstmt.close(); }
				if(conn != null){ conn.close(); }
			}catch(Exception se){
				se.printStackTrace();
			}
		}
	}
	
	public void delete(String strID) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql="";
		
		try {
			conn = dataSource.getConnection();
			
			sql="delete from tbl_board where boardNo=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(strID));
			pstmt.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(pstmt != null){ pstmt.close(); }
				if(conn != null){ conn.close(); }
			}catch(Exception se){
				se.printStackTrace();
			}
		}
	}
	*/
}
















