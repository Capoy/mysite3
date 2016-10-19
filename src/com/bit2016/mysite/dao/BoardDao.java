package com.bit2016.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bit2016.mysite.vo.BoardVo;

public class BoardDao {
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 :" + e);
		}
		return conn;
	}
	
	public int getTotalCount() {
		int totalCount = 0;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			
			String sql = "select count(*) from board";
			rs = stmt.executeQuery(sql);
			if( rs.next() ) {
				totalCount = rs.getInt( 1 );
			}
		} catch (SQLException e) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( stmt != null ) {
					stmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch ( SQLException e ) {
				System.out.println( "error:" + e );
			}  
		}
		
		return totalCount;
	}
	
	public List<BoardVo> getList( Integer page, Integer size ) {
		
		List<BoardVo> list = new ArrayList<BoardVo>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = 
				" select * " +
				"   from ( select no, title, hit, reg_date, depth, name, users_no, rownum as rn" +
				"            from(  select a.no, a.title, a.hit, to_char(a.reg_date, 'yyyy-mm-dd hh:mi:ss') as reg_date, a.depth, b.name, a.users_no" +
				"                     from board a, users b" +
				"                    where a.users_no = b.no" +
// and title like '%kwd%' or content like '%kwd%'
                "                 order by group_no desc, order_no asc ))" +
                "  where (?-1)*?+1 <= rn and rn <= ?*?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt( 1, page );
			pstmt.setInt( 2, size );
			pstmt.setInt( 3, page );
			pstmt.setInt( 4, size );
			
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				long no = rs.getLong( 1 );
				String title = rs.getString( 2 );
				int hit = rs.getInt( 3 );
				String regDate = rs.getString( 4 );
				int depth = rs.getInt( 5 );
				String userName = rs.getString( 6 );
				long userNo = rs.getLong( 7 );
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setDepth(depth);
				vo.setUserName(userName);
				vo.setUserNo(userNo);
				
				list.add( vo );
			}
		} catch (SQLException e) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch ( SQLException e ) {
				System.out.println( "error:" + e );
			}  
		}
		
		return list;
	}
}