<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<script
	type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
var isEnd = false;
var page = 0;
var render = function( vo ) {
	var htmls =
		"<li>" +
		"<strong>" + vo.name + "</strong>" +
		"<p>" + vo.content + "</p>" +
		"<strong>" + vo.regDate + "</strong>" +
		"<a href=''>삭제</a>" +
		"</li>"; // js template library -> ejs
		
		$( "#list-guestbook" ).append( htmls );	
		
		
}

var fetchList = function() {
	if( isEnd == true ) {
		return;
	}
	++page;
	$.ajax({
		url: "${pageContext.request.contextPath }/api/guestbook?a=ajax-list&p=" + page,
		type: "get",
		dataType: "json",
		data:"",
		success: function( response ) {
			if( response.result != "success" ) {
				console.error( response.message );
				isEnd = true;
				return;
			}
			
			// redering
			$( response.data ).each( function(index, vo){
				render( vo );
			});
			
			if( response.data.length < 5 ) {
				isEnd = true;
				$( "#btn-fetch" ).prop( "disabled", true );
			}
		},
		error: function( jqXHR, status, e ) {
			console.error( status + ":" + e );
		}
	});
}

$(function(){
	$( "#add-form" ).submit( function( event ) {
		event.preventDefault();
		// ajax insert
	});
	
	$(window).scroll( function(){
		var $window = $(this);
		var scrollTop = $window.scrollTop();
		var windowHeight = $window.height();
		var documentHeight = $( document ).height();
		
		// 스크롤 바가 바닥까지 왔을 때( 20px 덜 왔을 때 )
		if( scrollTop + windowHeight + 10 > documentHeight ) {
			//console.log( "call fetchList" );
			fetchList();
		}
	});
	
	$( "#btn-fetch" ).click( function(){
		fetchList();
	});
	
	// 1번째 리스트 가져오기
	fetchList();
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="/mysite2/add" method="post">
					<input type="text" name="name" placeholder="이름">
					<input type="password" name="pass" placeholder="비밀번호">
					<textarea name="content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">
				
				</ul>
				<button style="margin-top:20px;" id="btn-fetch">가져오기</button>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>