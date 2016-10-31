package com.bit2016.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bit2016.mysite.vo.UserVo;

public class UserDao {
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
	
	// 인증(로그인)
	public UserVo get(String email, String password) {
		UserVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = 
				" select no, name" + 
				"   from users" +
				"  where email=?" +
				"    and password=?";
			pstmt = conn.prepareStatement( sql );

			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				Long no = rs.getLong( 1 );
				String name = rs.getString( 2 );
				
				vo = new UserVo();
				vo.setNo(no);
				vo.setName(name);
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
			} catch( SQLException e ) {
				System.out.println( "error:" + e );
			}
		}
		
		return vo;
	}
	
	// 이메일 체크
	public UserVo get( String email ) {
		UserVo vo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql =
				" select no, email, name" +
				"   from users" +
				"  where email=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				vo = new UserVo();
				vo.setNo( rs.getLong(1) );
				vo.setEmail( rs.getString( 2 ) );
				vo.setName( rs.getString( 3 ) );
			}
			
		} catch( SQLException ex  ){
			System.out.println( "error:" + ex );
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
			} catch( SQLException ex ) {
				System.out.println( "error:" + ex);
			}
		}
		
		return vo;
	}
	
	// 사용자 가져오기
	public UserVo get(Long no) {
		UserVo vo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = 
				" select no, name, email, gender" + 
				"   from users" +
				"  where no=?";
			pstmt = conn.prepareStatement( sql );

			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();
			if( rs.next() ) {
				vo = new UserVo();
				vo.setNo( rs.getLong( 1 ) );
				vo.setName( rs.getString( 2 ) );
				vo.setEmail( rs.getString( 3 ) );
				vo.setGender( rs.getString( 4 ) );
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
			} catch( SQLException e ) {
				System.out.println( "error:" + e );
			}
		}
		
		return vo;
	}
	
	public void insert( UserVo vo ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql =
				" insert" +
				"   into users" +
				" values( user_seq.nextval, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender() );
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( pstmt != null ) {
					pstmt.close();
				}				
				if( conn != null ) {
					conn.close();
				}
			}catch( SQLException e ) {
				System.out.println( "error:" + e);
			}
		}
	}
	
	public void update( UserVo vo ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update users set name = ?, gender = ?";
			if( "".equals( vo.getPassword() ) == false ){
				sql += ", password=?";
			}
			sql += " where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getGender() );
			if( "".equals( vo.getPassword() ) == false ){
				pstmt.setString( 3, vo.getPassword() );
				pstmt.setLong( 4, vo.getNo() );
			} else {
				pstmt.setLong( 3, vo.getNo() );
			}
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( pstmt != null ) {
					pstmt.close();
				}				
				if( conn != null ) {
					conn.close();
				}
			}catch( SQLException e ) {
				System.out.println( "error:" + e);
			}
		}
	}	
}
