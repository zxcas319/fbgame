package fg.game.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fg.create.service.CreateService;
import fg.game.service.GameService;
import fg.manage.service.ManageService;
import fg.vo.UserVO;

@Controller
public class GameController {
	Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "gameService")
	private GameService gameService;
	@Resource(name = "createService")
	private CreateService createService;
	@Resource(name = "manageService")
	private ManageService manageService;

	@RequestMapping(value = "/game/gamePage.do")
	public ModelAndView gamePage(HttpServletRequest req, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("/game/gamePage");

		UserVO user = (UserVO) session.getAttribute("userSession");
		String id = user.getId();
		String user_key = user.getUserKey();
		mv.addObject("userId", id);

		List<Map<String, Object>> list = createService.getPlayerList(user_key);
		list = manageService.getPlayerOverall(list);

		mv.addObject("player_list", list);

		String game_key = "";
		game_key = gameService.getGameKey(user_key);
		if (game_key == null || game_key.equals(""))
			game_key = gameService.intoGame(user_key);
		if (game_key == null || game_key.equals(""))
			game_key = gameService.makeGame(user_key);

		mv.addObject("game_key", game_key);
		return mv;
	}

	@RequestMapping(value = "/game/getOrderUserPlayerInfo.do")
	public @ResponseBody List<Map<String, Object>> getOrderUserPlayerInfo(HttpServletRequest req) throws Exception {
		String order_id = req.getParameter("order_id");

		List<Map<String, Object>> result = gameService.getOrderUserPlayerInfo(order_id);

		return result;
	}

	@RequestMapping(value = "/game/updateStatus.do")
	public @ResponseBody void updateStatus(HttpServletRequest req, HttpSession session) throws Exception {
		String status = req.getParameter("status");
		String game_key = req.getParameter("game_key");
		Map<String, Object> param = new HashMap<String, Object>();
		UserVO user = (UserVO) session.getAttribute("userSession");
		String user_key = user.getUserKey();
		param.put("user_key", user_key);
		if (status.equals("start"))
			param.put("status", 1);
		else if (status.equals("cancel"))
			param.put("status", 0);
		param.put("game_key", game_key);
		gameService.updateStatus(param);
	}

	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/game/startGame.do")
	public @ResponseBody void startGame(HttpServletRequest req, HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("userSession");
		String user_key = user.getUserKey();
		String game_key = gameService.getGameKey(user_key);
		String check_home = gameService.checkHome(game_key);
		String temp_key = req.getParameter("player_key");
		String temp_position = req.getParameter("select_position");

		String[] key = temp_key.split("/");
		String[] position = temp_position.split("/");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> param;
		Map<String, Integer> coord;
		Map<String, Integer> kickOffCoord = gameService.getBasicCoord("KICKOFF");
		int coord_x;
		int coord_y;
		int coord_x_kick = kickOffCoord.get("coord_x").intValue();
		int coord_y_kick = kickOffCoord.get("coord_y").intValue();
		int temp = -1;
		int priority = -1;
		int idx = 0;
		for (int i = 0; i < key.length; i++) {
			if (!key.equals("") && key != null) {
				param = new HashMap<String, Object>();
				param.put("game_key", game_key);
				param.put("player_key", key[i]);
				param.put("position", position[i]);
				if (!position[i].contains("SUB")) {
					coord = gameService.getBasicCoord(position[i]);
					coord_x = coord.get("coord_x").intValue();
					coord_y = coord.get("coord_y").intValue();
					param.put("coord_x", coord_x);
					param.put("coord_y", coord_y);
					if (check_home.equals(user_key)) {
						temp = coord.get("priority").intValue();
						if (priority == -1 || priority > temp) {
							idx = i;
							priority = temp;
						}
					}
				}
				list.add(param);
			}
		}
		if (check_home.equals(user_key)) {
			list.get(idx).put("coord_x", coord_x_kick);
			list.get(idx).put("coord_y", coord_y_kick);
			list.get(idx).put("own_ball", "own");
		}
		gameService.resetGamePosition(user_key);
		gameService.saveGamePosition(list);
	}

	@RequestMapping(value = "/game/playGame.do")
	public ModelAndView playGame(HttpServletRequest req, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("/game/playGame");
		int[][] section = gameService.getSection("");
		int[][] section_gk = gameService.getSection("gk");
		mv.addObject("section", section);
		mv.addObject("gk", section_gk);
		return mv;
	}

	@RequestMapping(value = "/game/getGameInfo.do")
	public @ResponseBody List<Map<String, Object>> getGameInfo(HttpServletRequest req, HttpSession session)
			throws Exception {
		UserVO user = (UserVO) session.getAttribute("userSession");
		String user_key = user.getUserKey();
		String game_key = gameService.getGameKey(user_key);
		List<Map<String, Object>> list = gameService.getGameInfo(game_key, user_key);

		return list;
	}

	@RequestMapping(value = "/game/checkAwayUser.do")
	public @ResponseBody Map<String, Object> checkAwayUser(HttpServletRequest req, HttpSession session)
			throws Exception {
		UserVO user = (UserVO) session.getAttribute("userSession");
		String user_key = user.getUserKey();
		Map<String, Object> param = gameService.checkAwayUser(user_key);

		return param;
	}

	@RequestMapping(value = "/game/updateGamePosition.do")
	public @ResponseBody void updateGamePosition(HttpServletRequest req, HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("userSession");
		String user_key = user.getUserKey();
		String game_key = gameService.getGameKey(user_key);
		List<Map<String, Object>> list = gameService.getPlayerList(user_key);

		Map<String, Integer> coord;
		int coord_x;
		int coord_y;
		for (int i = 0; i < list.size(); i++) {
			String position = (String) list.get(i).get("position");
			list.get(i).put("game_key", game_key);
			if (!position.contains("SUB")) {
				coord = gameService.getBasicCoord(position);
				coord_x = coord.get("coord_x").intValue();
				coord_y = coord.get("coord_y").intValue();
				list.get(i).put("coord_x", coord_x);
				list.get(i).put("coord_y", coord_y);
			}
		}
		gameService.resetGamePosition(user_key);
		gameService.saveGamePosition(list);
	}

	@RequestMapping(value = "/game/changePosition.do")
	public @ResponseBody void changePosition(HttpServletRequest req, HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("userSession");
		String user_key = user.getUserKey();
		String player_key = req.getParameter("player_key");
		String position = req.getParameter("position");
		Map<String, Object> param = new HashMap<String, Object>();
		if (!position.contains("SUB") && position != null && !position.equals("none")) {
			Map<String, Integer> coord = gameService.getBasicCoord(position);
			int coord_x = coord.get("coord_x").intValue();
			int coord_y = coord.get("coord_y").intValue();
			param.put("coord_x", coord_x);
			param.put("coord_y", coord_y);
		} else {
			param.put("coord_x", null);
			param.put("coord_y", null);
		}
		if (position.equals("none")) {
			gameService.removePlayer(player_key);
		} else {
			param.put("player_key", player_key);
			param.put("position", position);
			int valid = gameService.changePosition(param);
			if (valid == 0) {
				String game_key = gameService.getGameKey(user_key);
				param.put("game_key", game_key);
				gameService.insertPlayer(param);
			}
		}
	}

	@RequestMapping(value = "/game/actionPercent.do")
	public @ResponseBody Map<String, Object> actionPercent(HttpServletRequest req, HttpServletResponse res,
			HttpSession session) throws Exception {
		String section = req.getParameter("section");
		Map<String, Object> per = gameService.makePercent(section);
		return gameService.makeAction(per);
	}

	@RequestMapping(value = "/game/getGameTime.do")
	public @ResponseBody int getGameTime(HttpServletRequest req, HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("userSession");
		String user_key = user.getUserKey();
		String game_key = gameService.getGameKey(user_key);
		int time = gameService.getGameTime(game_key);

		return time;
	}

	@RequestMapping(value = "/game/removeGame.do")
	public @ResponseBody void removeGame(HttpServletRequest req, HttpServletResponse res, HttpSession session)
			throws Exception {
		UserVO user = (UserVO) session.getAttribute("userSession");
		String user_key = user.getUserKey();
		String game_key = gameService.getGameKey(user_key);
		gameService.removeGameInfo(game_key);
		gameService.removeGameLog(game_key);
		gameService.removeGame(game_key);
	}

	@RequestMapping(value = "/game/gameThread.do")
	public void gameThread(HttpServletRequest req, HttpSession sess) throws Exception {
		UserVO user = (UserVO) sess.getAttribute("userSession");
		String id = user.getUserName();
		String user_key = user.getUserKey();
		int[][] section = gameService.getSection("");
		int[][] section_gk = gameService.getSection("gk");
		String game_key = gameService.getGameKey(user_key);
		gameService.createGameLog(game_key);

		String check_home = gameService.checkHome(game_key);
		long one_sec = 100 / 3;
		if (check_home.equals(user_key)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int time = 0;
					while (true) {
						try {
							gameService.updateGameTime(game_key);
							time = gameService.getGameTime(game_key);
							if (time >= 60)
								break;
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						log.debug("thread time : "
								+ ((time / 60 >= 10) ? "" + (int) (time / 60) : "0" + (int) (time / 60)) + ":"
								+ ((time % 60 >= 10) ? "" + (int) (time % 60) : "0" + (int) (time % 60)));
						try {
							Thread.sleep(one_sec);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//gameService.nextGameInfo();
				}
			}).start();
		}
	}
}