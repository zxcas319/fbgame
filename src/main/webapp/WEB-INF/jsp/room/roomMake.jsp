<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/main.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js" /></script>
<title>Web Game FG - Main</title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			// Load Team Class & Money Event
			$.ajax({
		        type : "POST"
		        , url : "/fg/room/initInfo.do"
		        , data : {}
		        , success : function(data) {
		        	console.log(data);
		        	$("#team_class").text(data.grade);
		        	$("#team_money").text(data.money);
		        }
		        , error : function(e) {
		        	console.log(e.result);
				}
		    });
			setInterval(function(){
			$.ajax({
				type : "POST",
				url : "/fg/room/getRoomInfo.do",
				data : {},
				success : function(data) {
					
					$.ajax({
				        type : "POST"
				        , url : "/fg/room/initInfo.do"
				        , data : {}
				        , success : function(data) {
				        	console.log(data);
				        	$("#team_class").text(data.grade);
				        	$("#team_money").text(data.money);
				        }
				        , error : function(e) {
				        	console.log(e.result);
						}
				    });
					
					$.ajax({
						type : "POST",
						url : "/fg/room/getRoomId.do",
						data : {
							
						},
						success : function(data) {
							
							var rtvHtml = "";
							var k = data.length;
							var i = 0;
							var room_capcity = 2;
							console.log(data);
							if(data[0].game_count==1){
							for(var i = 0;i < 6;i = i+2){
								rtvHtml += "<div style='border-radius: 50px; border: solid;'>";
								rtvHtml += "<div style='margin-left: 50px; width: 3px; height: 3px;'";
								rtvHtml += "style='margin-left: 50px;'>";
								rtvHtml += "<img src='${pageContext.request.contextPath}/resources/img/premiere_league.jpg'";
								rtvHtml += "class='pp' style='height: 145px; width: 200px;'>";
								rtvHtml += "</div>";
								rtvHtml += "<div style='margin-left: 50px; width: 3px; height: 3px;'>";
								rtvHtml += "<img src='${pageContext.request.contextPath}/resources/img/pp.png'";
								rtvHtml += "class='pp1' style='height: 145px; width: 200px;'>";
								rtvHtml += "</div>";
								rtvHtml += "<table width=1600 height=150 style='margin-left: 300px;'>";
								rtvHtml += "<tr class='content_tr'>";
									
								if (i >= k){
									rtvHtml += "<td style='text-left: 50px;' width='650'>"+ "empty_player" +"</td>";
								}
								else{
									rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[i].id +"</td>";
								}
								
								if (i+1 >= k){
									rtvHtml += "<td style='text-left: 50px;' width='650'>"+ "empty_player" +"</td>";
								}
								else{
									rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[i+1].id +"</td>";
								}
								rtvHtml += "</tr>";
								rtvHtml += "</table>";
								rtvHtml += "<table width=1600 height=150 style='margin-left: 300px;'>";
								rtvHtml += "<tr class='content_overall'>";
								
								if (i >= k){
									rtvHtml += "<td style='text-left: 50px;' width='650'>"+ "0" +"</td>";
								}
								else{
									rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[i].overall +"</td>";
								}
								
								if (i+1 >= k){
									rtvHtml += "<td style='text-left: 50px;' width='650'>"+ "0" +"</td>";
								}
								else{
									rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[i+1].overall +"</td>";
								}
								
								rtvHtml += "</tr>";
								rtvHtml += "</table>";
								rtvHtml += "</div>";
								rtvHtml += "<p style='margin-top: 50px;'></p>";
								rtvHtml += "</div>";
							}
							
							}//첫번째 방
							if(data[0].game_count>1){
								
								//방1
								rtvHtml += "<div style='border-radius: 50px; border: solid;'>";
								rtvHtml += "<div style='margin-left: 50px; width: 3px; height: 3px;'";
								rtvHtml += "style='margin-left: 50px;'>";
								rtvHtml += "<img src='${pageContext.request.contextPath}/resources/img/premiere_league.jpg'";
								rtvHtml += "class='pp' style='height: 145px; width: 200px;'>";
								rtvHtml += "</div>";
								rtvHtml += "<div style='margin-left: 50px; width: 3px; height: 3px;'>";
								rtvHtml += "<img src='${pageContext.request.contextPath}/resources/img/pp.png'";
								rtvHtml += "class='pp1' style='height: 145px; width: 200px;'>";
								rtvHtml += "</div>";
								rtvHtml += "<table width=1600 height=150 style='margin-left: 300px;'>";
								rtvHtml += "<tr class='content_tr'>";
								
								rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[data[0].user_number].id +"</td>";
								rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[data[1].user_number].id +"</td>";
								
								rtvHtml += "</tr>";
								rtvHtml += "</table>";
								
								rtvHtml += "<table width=1600 height=150 style='margin-left: 300px;'>";
								rtvHtml += "<tr class='content_overall'>";
								rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[data[0].user_number].overall +"</td>";
								rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[data[1].user_number].overall +"</td>";
								
								
								rtvHtml += "</tr>";
								rtvHtml += "</table>";
								rtvHtml += "</div>";
								rtvHtml += "<p style='margin-top: 50px;'></p>";
								rtvHtml += "</div>";
								
								//방2
								rtvHtml += "<div style='border-radius: 50px; border: solid;'>";
								rtvHtml += "<div style='margin-left: 50px; width: 3px; height: 3px;'";
								rtvHtml += "style='margin-left: 50px;'>";
								rtvHtml += "<img src='${pageContext.request.contextPath}/resources/img/premiere_league.jpg'";
								rtvHtml += "class='pp' style='height: 145px; width: 200px;'>";
								rtvHtml += "</div>";
								rtvHtml += "<div style='margin-left: 50px; width: 3px; height: 3px;'>";
								rtvHtml += "<img src='${pageContext.request.contextPath}/resources/img/pp.png'";
								rtvHtml += "class='pp1' style='height: 145px; width: 200px;'>";
								rtvHtml += "</div>";
								rtvHtml += "<table width=1600 height=150 style='margin-left: 300px;'>";
								rtvHtml += "<tr class='content_tr'>";
								
								rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[data[2].user_number].id +"</td>";
								rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[data[3].user_number].id +"</td>";
								
								rtvHtml += "</tr>";
								rtvHtml += "</table>";
								
								rtvHtml += "<table width=1600 height=150 style='margin-left: 300px;'>";
								rtvHtml += "<tr class='content_overall'>";
								rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[data[2].user_number].overall +"</td>";
								rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[data[3].user_number].overall +"</td>";
								
								
								rtvHtml += "</tr>";
								rtvHtml += "</table>";
								rtvHtml += "</div>";
								rtvHtml += "<p style='margin-top: 50px;'></p>";
								rtvHtml += "</div>";
								
								//방3
								rtvHtml += "<div style='border-radius: 50px; border: solid;'>";
								rtvHtml += "<div style='margin-left: 50px; width: 3px; height: 3px;'";
								rtvHtml += "style='margin-left: 50px;'>";
								rtvHtml += "<img src='${pageContext.request.contextPath}/resources/img/premiere_league.jpg'";
								rtvHtml += "class='pp' style='height: 145px; width: 200px;'>";
								rtvHtml += "</div>";
								rtvHtml += "<div style='margin-left: 50px; width: 3px; height: 3px;'>";
								rtvHtml += "<img src='${pageContext.request.contextPath}/resources/img/pp.png'";
								rtvHtml += "class='pp1' style='height: 145px; width: 200px;'>";
								rtvHtml += "</div>";
								rtvHtml += "<table width=1600 height=150 style='margin-left: 300px;'>";
								rtvHtml += "<tr class='content_tr'>";
								
								rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[data[4].user_number].id +"</td>";
								rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[data[5].user_number].id +"</td>";
								
								rtvHtml += "</tr>";
								rtvHtml += "</table>";
								
								rtvHtml += "<table width=1600 height=150 style='margin-left: 300px;'>";
								rtvHtml += "<tr class='content_overall'>";
								rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[data[4].user_number].overall +"</td>";
								rtvHtml += "<td style='text-left: 50px;' width='650'>"+ data[data[5].user_number].overall +"</td>";
								
								
								rtvHtml += "</tr>";
								rtvHtml += "</table>";
								rtvHtml += "</div>";
								rtvHtml += "<p style='margin-top: 50px;'></p>";
								rtvHtml += "</div>";
								
							}
							
							$("#bottom_html").empty();
							$("#bottom_html").append(rtvHtml);
							if(k==6){
								
								
							      
						    //count 증가
								var form = $("#send_form");
							      form.attr('method', 'post');
							      form.attr('action', "/fg/room/roomCount.do");
							      form.submit();
							      
							    var form = $("#send_form");
							      form.attr('method', 'post');
							      form.attr('action', "/fg/game/gamePage.do");
							      form.submit();      
							}
												
							console.log(data);

							

						},
						error : function(e) {
							console.log(e.result);
						}
					});
					
				},
				error : function(e) {
					console.log(e.result);
				}
			});
			}, 1000);
			
			
			
			
			
			
			// Show Left sub menu Event

			// Logout Event
			$("#logoutBt").on("click", function() {
				$.ajax({
					type : "POST",
					url : "/fg/logout/logoutAction.do",
					data : {},
					success : function(data) {
						var form = $("#send_form");
						form.attr('method', 'post');
						form.attr('action', "/fg/login/loginPage.do");
						form.submit();
					},
					error : function(e) {
						console.log(e.result);
					}
				});
			});
			// Main Page 이동 Event
			$(".main_banner").on("click", function() {
				var form = $("#send_form");
				form.attr('method', 'post');
				form.attr('action', "/fg/start/mainPage.do");
				form.submit();
			})
			// 모든 페이지 공통 Event
			// Logout Event
			$("#logoutBt").on("click", function() {
				$.ajax({
					type : "POST",
					url : "/fg/logout/logoutAction.do",
					data : {},
					success : function(data) {
						var form = $("#send_form");
						form.attr('method', 'post');
						form.attr('action', "/fg/login/loginPage.do");
						form.submit();
					},
					error : function(e) {
						console.log(e.result);
					}
				});
			});
		})
	</script>
	<form id="send_form">
	</form>
	<form id="send_form"></form>
	<div class='main_page_html'>
		<input type='button' value='L O G O U T' id='logoutBt'>
		<table width=1600>
			<tr>
				<td class='main_banner' rowspan='2' colspan='3'>main</td>
				<td class='top_txt'>가치</td>
				<td class='top_val' id='team_money'>1,000</td>
			</tr>
			<tr>
				<td class='top_txt'>등급</td>
				<td class='top_val' id='team_class'>S</td>
			</tr>

		</table>
		<p style="margin-top: 50px;"></p>
		<div>
			<p id='bottom_html' style="margin-top: 20px;"></p>
	</div>
</body>
</html>