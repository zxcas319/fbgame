package fg.game.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import fg.game.dao.GameDAO;
import fg.vo.UserVO;

@Service("gameService")
public class GameServiceImpl implements GameService {
	@Resource(name = "gameDAO")
	private GameDAO gameDAO;

	Logger log = Logger.getLogger(this.getClass());

	@Override
	public List<Map<String, Object>> getOrderUserPlayerInfo(String order_id) throws Exception {
		// TODO Auto-generated method stub
		return gameDAO.getOrderUserPlayerInfo(order_id);
	}

	@Override
	public String makeGame(String key) throws Exception {
		// TODO Auto-generated method stub
		return gameDAO.makeGame(key);
	}

	@Override
	public void updateStatus(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		gameDAO.updateStatus(param);
	}

	@Override
	public String getGameKey(String user_key) throws Exception {
		// TODO Auto-generated method stub
		return gameDAO.getGameKey(user_key);
	}

	@Override
	public Map<String, Integer> getBasicCoord(String position) throws Exception {
		// TODO Auto-generated method stub
		return gameDAO.getBasicCoord(position);
	}

	@Override
	public void saveGamePosition(List<Map<String, Object>> list) throws Exception {
		// TODO Auto-generated method stub
		gameDAO.saveGamePosition(list);
	}

	@Override
	public void resetGamePosition(String user_key) throws Exception {
		// TODO Auto-generated method stub
		gameDAO.resetGamePosition(user_key);
	}

	@Override
	public List<Map<String, Object>> getGameInfo(String game_key, String user_key) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> rtvList = gameDAO.getGameInfo(game_key);

		String val = "";
		for (int i = 0; i < rtvList.size(); i++) {
			val = (String) rtvList.get(i).get("user_key");
			if (val.equals(user_key))
				rtvList.get(i).put("who", "home");
			else
				rtvList.get(i).put("who", "away");
		}

		return rtvList;
	}

	@Override
	public Map<String, Object> checkAwayUser(String user_key) {
		// TODO Auto-generated method stub home_user, away_user, b.id home_id, c.id
		// away_id, home_status, away_status
		Map<String, Object> param = gameDAO.checkAwayUser(user_key);
		Map<String, Object> result = new HashMap<String, Object>();

		String valid = (String) param.get("home_user");
		int status = -1;
		String id = "";
		if (valid.equals(user_key)) {
			status = ((Integer) param.get("away_status")).intValue();
			id = (String) param.get("away_id");
		} else {
			status = ((Integer) param.get("home_status")).intValue();
			id = (String) param.get("home_id");
		}
		result.put("id", id);
		result.put("status", status);

		return result;
	}

	@Override
	public List<Map<String, Object>> getPlayerList(String user_key) throws Exception {
		// TODO Auto-generated method stub
		return gameDAO.getPlayerList(user_key);
	}

	@Override
	public String intoGame(String user_key) throws Exception {
		// TODO Auto-generated method stub
		return gameDAO.intoGame(user_key);
	}

	@Override
	public int changePosition(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return gameDAO.changePosition(param);
	}

	@Override
	public void removePlayer(String player_key) throws Exception {
		// TODO Auto-generated method stub
		gameDAO.removePlayer(player_key);
	}

	@Override
	public void insertPlayer(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		gameDAO.insertPlayer(param);
	}

	@Override
	public Map<String, Object> makePercent(String section) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> per = gameDAO.getMakePercent(section);

		return per;
	}

	@Override
	public Map<String, Object> makeAction(Map<String, Object> per) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		Random generator = new Random();

		int[] act = new int[3];
		act[0] = (Integer) per.get("shoot");
		act[1] = (Integer) per.get("pass") + act[0];
		act[2] = (Integer) per.get("dribble") + act[1];
		int rand = generator.nextInt(act[2]) + 1;

		if (rand <= act[0]) {
			map.put("action", "S");
		} else if (rand <= act[1]) {
			map.put("action", "P");
		} else if (rand <= act[2]) {
			map.put("action", "D");
		}

		return map;
	}

	@Override
	public int[][] getSection(String gk) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> divide = gameDAO.getSection(gk);
		int min_x, max_x, min_y, max_y, div;
		int[][] section = new int[38][51];
		for (int k = 0; k < divide.size(); k++) {
			min_x = ((Integer) divide.get(k).get("min_x")).intValue();
			min_y = ((Integer) divide.get(k).get("min_y")).intValue();
			max_x = ((Integer) divide.get(k).get("max_x")).intValue();
			max_y = ((Integer) divide.get(k).get("max_y")).intValue();
			div = ((Integer) divide.get(k).get("section")).intValue();
			for (int j = min_y; j <= max_y; j++) {
				for (int i = min_x; i <= max_x; i++) {
					section[j][i] = div;
				}
			}
		}
		return section;
	}

	@Override
	public void removeGameInfo(String game_key) throws Exception {
		// TODO Auto-generated method stub
		gameDAO.removeGameInfo(game_key);
	}

	@Override
	public void removeGameLog(String game_key) throws Exception {
		// TODO Auto-generated method stub
		gameDAO.removeGameLog(game_key);
	}

	@Override
	public void removeGame(String game_key) throws Exception {
		// TODO Auto-generated method stub
		gameDAO.removeGame(game_key);
	}

	@Override
	public String checkHome(String game_key) throws Exception {
		// TODO Auto-generated method stub
		return gameDAO.checkHome(game_key);
	}

	@Override
	public void createGameLog(String game_key) throws Exception {
		// TODO Auto-generated method stub
		int valid = gameDAO.checkGameLog(game_key);
		if (valid == 0)
			gameDAO.createGameLog(game_key);
	}

	@Override
	public void updateGameTime(String game_key) throws Exception {
		// TODO Auto-generated method stub
		gameDAO.updateGameTime(game_key);
	}

	@Override
	public int getGameTime(String game_key) throws Exception {
		return gameDAO.getGameTime(game_key);
	}
}