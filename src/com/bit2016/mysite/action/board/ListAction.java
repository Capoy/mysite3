package com.bit2016.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit2016.mysite.dao.BoardDao;
import com.bit2016.mysite.vo.BoardVo;
import com.bit2016.web.Action;
import com.bit2016.web.util.WebUtil;
					
public class ListAction implements Action {
	private static final int LIST_SIZE = 5;
	private static final int PAGE_SIZE = 5;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = WebUtil.checkIntParam( request.getParameter( "p" ), 1 );
		
		BoardDao dao = new BoardDao();
		
		List<BoardVo> list = dao.getList( page, LIST_SIZE );

		int totalCount = dao.getTotalCount(); 

		
		request.setAttribute( "list", list );
		
		request.setAttribute( "totalCount", totalCount );
		request.setAttribute( "currentPage", page );
		request.setAttribute( "listSize", LIST_SIZE );
		
		WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
	}
}
