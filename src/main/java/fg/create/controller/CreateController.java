package fg.create.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fg.create.service.CreateService;
import fg.vo.UserVO;

@Controller
public class CreateController {
    Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "createService")
	private CreateService createService;
	
	@RequestMapping(value = "/create/createTeam.do")
	public ModelAndView createTeam(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mv = new ModelAndView("create/createTeam");
		return mv;
	}

	@RequestMapping(value = "/create/createPlayer.do")
	public @ResponseBody List<Map<String, Object>> createPlayer(HttpServletRequest req, HttpServletResponse res, HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("userSession");
		String key = user.getUserKey();
		createService.removePlayerList(key);
		int position = 0;
		int rating = 0;
		Map<String, Object> per = createService.makePercent(rating);
		for (int i = 0; i < 23; i++) {
			if (i == 3 || i == 11 || i == 19)
				position++;
			createService.makePlayer(position, rating, key, per);
		}
		List<Map<String, Object>> list = createService.getPlayerList(key);
		list = createService.getPlayerOverall(list);
		return list;
	}

	@RequestMapping(value = "/create/editPlayerName.do")
	public @ResponseBody void editPlayerName(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String player_key = req.getParameter("player_key");
		String name = req.getParameter("name");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("key", player_key);
		param.put("name", name);
		createService.editPlayerName(param);
	}

	@RequestMapping(value = "/create/saveTeam.do")
	public @ResponseBody void saveTeam(HttpServletRequest req, HttpServletResponse res, HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("userSession");
		String key = user.getUserKey();
		String team_name = req.getParameter("team_name");
		String tendency = req.getParameter("tendency");
		String rating = req.getParameter("rating");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("key", key);
		param.put("team_name", team_name);
		param.put("tendency", Integer.parseInt(tendency));
		param.put("rating", Integer.parseInt(rating));

		createService.saveTeam(param);
		user = createService.getUserInfo(key);
		session.removeAttribute("userSession");
		session.setAttribute("userSession", user);
	}
}