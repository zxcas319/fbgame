<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/game.css">
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js" /></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/sockjs-0.3.4.js" /></script>
   <title>Web Game FG - Play Game</title>
</head>
<body>
<script type="text/javascript">
var change_position = "";
$(document).ready(function(){
   startGame();
   $.ajax({
        type : "POST"
        , url : "/fg/game/gameThread.do"
        , data : {}
        , success : function(data) {
        }
        , error : function(e) {
           console.log(e.result);
        }
    });
   $("#ground td").on("click", function(){
      var section = $(this).data("section");
      var gk = $(this).data("gk");
      if(gk != "0")   console.log(gk);
      console.log(section);
      actionPercent(section);
   });
});
var Kick = 0;
function startGame() {
   setInterval(function(){
      $.ajax({
           type : "POST"
           , url : "/fg/game/getGameTime.do"
           , data : {}
           , success : function(data) {
             console.log(data);
             var m = parseInt(data / 60);
             var s = data % 60;
             $(".log_tr td b").text("" + (m >= 10?m:"0"+m) + ":" + (s >= 10?s:"0"+s));
         }
           , error : function(e) {
              console.log(e.result);
           }
       });
      
      $.ajax({
           type : "POST"
           , url : "/fg/game/getGameInfo.do"
           , data : {}
           , success : function(data) {
        	   var rtvHome = "";
        	   var rtvAway = "";
        	   $("#ground td").removeClass("home");
        	   $("#ground td").removeClass("away");
        	   $("#ground td").removeClass("home_ball");
        	   $("#ground td").removeClass("away_ball");
        	   $("#ground td b").text("");
        	   Kick++;
        	   for(var i = 0;i < data.length;i++){
        		   var m = data[i];
        		   if(m.coord_y != null && m.coord_x != null){
        			   var x = 0;
        			   if(Kick < 5){
        				   x = (m.who == "home")?((m.coord_x) + 2):52 - (m.coord_x);
       				   }
                       else
                    	   x = (m.who == "home")?((m.coord_x * 2) + 2):52 - (m.coord_x * 2);
                   	   var y = (m.who == "home")?m.coord_y:38 - m.coord_y;
                   	   var coord = "#ground tr:nth-child(" + y + ") td:nth-child(" + x + ")";
                   	   $(coord).addClass(m.who);
                   	   $(coord + " b").text(m.back_number);
                   	   if(m.own_ball == "own")
                   		   $(coord).addClass(m.who + "_ball");
                 }
                 if(m.who == "home"){
                     rtvHome += "<tr data-key='" + m.player_key + "'>";
                     rtvHome += "<td style='text-align:center;border:1px solid;'>";
                     rtvHome += "" + m.select_position;
                     rtvHome += "</td>";
                     rtvHome += "<td style='text-align:center;border:1px solid;"
                     rtvHome += "'>";
                     rtvHome += "" + m.back_number;
                     rtvHome += "</td>";
                     rtvHome += "<td style='text-align:center;border:1px solid;'>";
                     rtvHome += "" + m.name;
                     rtvHome += "</td>";
                     rtvHome += "</tr>";
                 }
                 else {
                   	 rtvAway += "<tr data-key='" + m.player_key + "'>";
                     rtvAway += "<td style='text-align:center;border:1px solid;'>";
                     rtvAway += "" + m.select_position;
                     rtvAway += "</td>";
                     rtvAway += "<td style='text-align:center;border:1px solid;'>";
                     rtvAway += "" + m.back_number;
                     rtvAway += "</td>";
                     rtvAway += "<td style='text-align:center;border:1px solid;'>";
                     rtvAway += "" + m.name;
                     rtvAway += "</td>";
                     rtvAway += "</tr>";
                 }
              }
              $("#home_player tbody").empty();
              $("#home_player").append(rtvHome);
              $("#away_player tbody").empty();
              $("#away_player").append(rtvAway);
         }
           , error : function(e) {
              console.log(e.result);
           }
       });
   }, 1000);
}

function actionPercent(section){
   $.ajax({
        type : "POST"
        , url : "/fg/game/actionPercent.do"
        , data : {
           section : section
        }
        , success : function(data) {
           console.log(data);
           console.log(data.action);
        }
        , error : function(e) {
           console.log(e.result);
        }
   });
}
</script>
<form id='send_form'>
</form>
<div class='main_page_html'>
   <table class='main_table'>
      <tr>
         <td rowspan='5' colspan='4' class='content_window' style='height: 800px; width: 1600px;'>
            <table class='content_table' style='height: 872px; width: 1600px;'>
               <tr class='log_tr' style='height: 50px; width:1600px;'>
                  <td colspan='3' style='text-align: center; font-size: xx-large;'><b></b></td>
               </tr>
               <tr class='content_tr' style='height: 722px; width: 1600px;'>
               <!-- content start -->
                  <td class='content_full' style='height: 722px; width: 296px;'>
                     <table id='home_player' style='height: 722px;width:296px;'>
                        <colgroup>
                           <col width='15%'/>
                           <col width='15%'/>
                           <col width='70%'/>
                        </colgroup>
                        <thead>
                           <tr>
                              <th>Position</th>
                              <th>Back Number</th>
                              <th>Name</th>
                           </tr>
                        </thead>
                        <tbody></tbody>
                     </table>
                  </td>
                  <td class='content_full' style='height: 722px; width: 1008px;'>
                     <table id='ground' style='height: 722px; width: 1008px;background-size: contain; background-image:url("${pageContext.request.contextPath}/img/ground.jpg");'>
                        <c:forEach begin='0' end='37' step='1' var='j'>
                        <tr style='height:19px;padding:0px;'>
                           <td style='width:19px;height:19px;padding:0px;text-align:center;color: white;font-size: small;'>
                           <c:forEach begin='0' end='50' step='1' var='i'>
                              <td data-x="${ i}" data-y="${ j}" data-section="${section[j][i] }" data-gk="${gk[j][i] }"><b></b></td>
                           </c:forEach>
                           <td style='width:19px;height:19px;padding:0px;text-align:center;color: white;font-size: small;'>
                        </tr>
                        </c:forEach>
                     </table>
                  </td>
                  <td class='content_full' style='height: 722px; width: 296px;'>
                     <table id='away_player' style='height: 722px;width:296px;'>
                        <colgroup>
                           <col width='15%'/>
                           <col width='15%'/>
                           <col width='70%'/>
                        </colgroup>
                        <thead>
                           <tr>
                              <th>Position</th>
                              <th>Back Number</th>
                              <th>Name</th>
                           </tr>
                        </thead>
                        <tbody></tbody>
                     </table>
                  </td>
               <!-- content end -->
               </tr>
               <tr>
                  <td class='notice_window' colspan='4'>notice</td>
               </tr>
            </table>
         </td>
      </tr>
   </table>
</div>
</body>
</html>