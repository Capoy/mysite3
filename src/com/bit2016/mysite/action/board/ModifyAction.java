package com.bit2016.mysite.action.board;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit2016.mysite.dao.BoardDao;
import com.bit2016.mysite.vo.BoardVo;
import com.bit2016.mysite.vo.UserVo;
import com.bit2016.web.Action;
import com.bit2016.web.util.WebUtil;

public class ModifyAction implements Action {

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
	
		long no = WebUtil.checkLongParam(request.getParameter("no"), 0L);
		String title = WebUtil.checkNullParam(request.getParameter("title"), "");
		String content = WebUtil.checkNullParam(request.getParameter("content"), "");
		String keyword = WebUtil.checkNullParam( request.getParameter( "kwd" ), "" );

		BoardVo vo = new BoardVo();
		vo.setNo(no);
		vo.setTitle(title);
		vo.setContent(content);
		vo.setUserNo( authUser.getNo() );

		new BoardDao().update(vo);

		WebUtil.redirect(
			request,
			response, 
			"/mysite3/board?a=view&no=" + no + "&kwd=" + URLEncoder.encode( keyword, "UTF-8" ) );
	}
}
