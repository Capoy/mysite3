package com.bit2016.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit2016.mysite.dao.BoardDao;
import com.bit2016.mysite.vo.BoardVo;
import com.bit2016.mysite.vo.UserVo;
import com.bit2016.web.Action;
import com.bit2016.web.util.WebUtil;

public class WriteAction implements Action {

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
		
		String title = request.getParameter( "title" );
		String content = request.getParameter( "content" );
		String gno = request.getParameter( "gno" );
		String ono = request.getParameter( "ono" );
		String d = request.getParameter( "d" );
		
		BoardDao dao = new BoardDao();
		BoardVo vo = new BoardVo();
		
		vo.setTitle(title);
		vo.setContent(content);
		vo.setUserNo( authUser.getNo() );
		
		if( gno != null ) {
			int groupNo = Integer.parseInt( gno );
			int orderNo = Integer.parseInt( ono );
			int depth = Integer.parseInt( d );
			
			// 같은 그룹의 orderNo 보다 큰 글 들의 order_no 1씩 증가
			dao.increaseGroupOrder( groupNo, orderNo );
			
			vo.setGroupNo(groupNo);
			vo.setOrderNo(orderNo+1);
			vo.setDepth(depth+1);
		}
		
		dao.insert(vo);
		
		WebUtil.redirect(
			request, 
			response,
			"/mysite3/board");
	}

}
