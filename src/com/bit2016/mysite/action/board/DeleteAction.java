package com.bit2016.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit2016.mysite.dao.BoardDao;
import com.bit2016.mysite.vo.UserVo;
import com.bit2016.web.Action;
import com.bit2016.web.util.WebUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// is auth?
		HttpSession session = request.getSession();
		if( session == null ) {
			WebUtil.redirect(request, response, "/mysite3/board" );
			return;
		}
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ) {
			WebUtil.redirect(request, response, "/mysite3/board" );
			return;
		}	
		
		long no = WebUtil.checkLongParam( request.getParameter( "no" ), 0L );
		int page = WebUtil.checkIntParam( request.getParameter( "p" ), 1 ); 
		long userNo = authUser.getNo();
		
		new BoardDao().delete( no, userNo );
		
		WebUtil.redirect(
			request, 
			response, 
			"/mysite3/board?a=list&p=" + page );
	}
}