package fg.room.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fg.common.utils.Gmailsend;
import fg.create.service.CreateService;
import fg.manage.service.ManageService;
import fg.room.service.RoomService;
import fg.vo.UserVO;

@Controller
public class RoomController {
    Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "roomService")
	private RoomService roomService;

	@Resource(name = "manageService")
	private ManageService manageService;
	
	@Resource(name = "createService")
	private CreateService createService;
	
	@RequestMapping(value = "/room/roomMake.do")
	public ModelAndView mainPage(HttpServletRequest req, HttpServletResponse res, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("room/roomMake");
		return mv;
	}
	
	@RequestMapping(value = "/room/initInfo.do")
	public @ResponseBody Map<String, Object> initInfo(HttpServletRequest req, HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("userSession");
		String user_key = user.getUserKey();
		int rating =user.getRating();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("user_key", user_key);
		map.put("rating", rating);
		return roomService.initInfo(map);
	}
	
	@RequestMapping(value = "/room/getRoomInfo.do")
	public @ResponseBody void getRoomInfo(HttpServletRequest req, HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("userSession");
		String user_key = user.getUserKey();
		int rating =user.getRating();
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("user_key", user_key);
		map.put("rating", rating);
		roomService.getRoomInfo(map);
	}
	@RequestMapping(value = "/room/getRoomId.do")
	public @ResponseBody List<Map<String, Object>> getRoomId(HttpServletRequest req, HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("userSession");
		String user_key = user.getUserKey();
		
		String room_key=roomService.getRoomKeyInfo(user_key);
		List<Map<String, Object>> result = roomService.getRoomId();
		String key = "";
		int overall = 0;
		List<Map<String, Object>> temp;
		int s=0;
		
		List<Map<String,Object>> round=roomService.getRoundInfo(room_key);
		for(int i = 0;i < result.size();i++) {
			temp = new ArrayList<Map<String, Object>>();
			key = (String) result.get(i).get("user_key");
			temp = createService.getPlayerList(key);
			overall = roomService.getPlayerOverall(temp);
			result.get(i).put("overall", overall);
			//s = Integer.parseInt((String)(round.get(i).get("testColumn")));
			System.out.println(s);
			result.get(i).put("user_number", ((Integer) (round.get(i).get("testColumn"))).intValue());
		}
		
		//result.add(round);
		return result;
	}
	@RequestMapping(value = "/room/getAllOverall.do")
	public @ResponseBody List<Map<String, Object>> getAllOverall(HttpServletRequest req, HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("userSession");
		String key = user.getUserKey();
		int rating =user.getRating();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("user_key", key);
		map.put("rating", rating);
		List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> list1 = createService.getPlayerList(key);
		list1 = manageService.getPlayerOverall(list1);
		
		//int rating = ((Integer) result.get("rating")).intValue();
		//result.addAll(list1);	
		return list1;
	}
	
	@RequestMapping(value = "/room/roomCount.do")
	public @ResponseBody void roomCount(HttpServletRequest req, HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("userSession");
		String user_key = user.getUserKey();
		int rating =user.getRating();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("user_key", user_key);
		map.put("rating", rating);
		roomService.roomCount();
	}

}
