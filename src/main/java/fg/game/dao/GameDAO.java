package fg.game.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import fg.common.dao.AbstractDAO;

@Repository("gameDAO")
public class GameDAO extends AbstractDAO {
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getOrderUserPlayerInfo(String order_id) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) selectList("game.getOrderUserPlayerInfo", order_id);
	}

	public String makeGame(String key) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_key", key);
		insert("game.makeGame", param);
		return (String) param.get("game_key");
	}

	public void updateStatus(Map<String, Object> param) {
		// TODO Auto-generated method stub
		int val = (int) update("game.updateHomeStatus", param);
		if (val == 0)
			update("game.updateAwayStatus", param);
	}

	public String getGameKey(String user_key) {
		// TODO Auto-generated method stub
		return (String) selectOne("game.getGameKey", user_key);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Integer> getBasicCoord(String position) {
		// TODO Auto-generated method stub
		return (Map<String, Integer>) selectOne("game.getBasicCoord", position);
	}

	public void saveGamePosition(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		insert("game.saveGamePosition", list);
	}

	@SuppressWarnings("unchecked")
	public void resetGamePosition(String user_key) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = (List<Map<String, Object>>) selectList("game.getPlayerKey", user_key);
		delete("game.resetGamePosition", list);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getGameInfo(String game_key) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) selectList("game.getGameInfo", game_key);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> checkAwayUser(String user_key) {
		// TODO Auto-generated method stub
		return (Map<String, Object>) selectOne("game.checkAwayUser", user_key);
	}

	@SuppressWarnings("unchecked")
	public String intoGame(String user_key) {
		// TODO Auto-generated method stub
		Map<String, Object> map = (Map<String, Object>) selectOne("game.findGame", "");
		if (map != null) {
			String valid = (String) map.get("home_user");
			String game_key = (String) map.get("game_key");

			Map<String, Object> param = new HashMap<String, Object>();
			if (!valid.equals("") && valid != null) {
				param.put("po", "away");
			} else {
				param.put("po", "home");
			}
			param.put("game_key", game_key);
			param.put("user_key", user_key);
			update("game.intoGame", param);
			return (String) map.get("game_key");
		} else
			return null;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getPlayerList(String user_key) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) selectList("game.getPlayerList", user_key);
	}

	public int changePosition(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return (int) update("game.changePosition", param);
	}

	public void removePlayer(String player_key) {
		// TODO Auto-generated method stub
		delete("game.removePlayer", player_key);
	}

	public void insertPlayer(Map<String, Object> param) {
		// TODO Auto-generated method stub
		insert("game.insertPlayer", param);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getMakePercent(String section) {
		// TODO Auto-generated method stub
		return (Map<String, Object>) selectOne("game.getMakePercent", section);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getSection(String gk) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("gk", gk);
		return (List<Map<String, Object>>) selectList("game.getSection", param);
	}

	public void removeGameInfo(String game_key) {
		// TODO Auto-generated method stub
		delete("game.removeGameInfo", game_key);
	}

	public void removeGameLog(String game_key) {
		// TODO Auto-generated method stub
		delete("game.removeGameLog", game_key);
	}

	public void removeGame(String game_key) {
		// TODO Auto-generated method stub
		delete("game.removeGame", game_key);
	}

	public String checkHome(String game_key) {
		// TODO Auto-generated method stub
		return (String) selectOne("game.checkHome", game_key);
	}

	public int checkGameLog(String game_key) {
		// TODO Auto-generated method stub
		return (Integer) selectOne("game.checkGameLog", game_key);
	}

	public void createGameLog(String game_key) {
		// TODO Auto-generated method stub
		insert("game.createGameLog", game_key);
	}

	public void updateGameTime(String game_key) {
		// TODO Auto-generated method stub
		update("game.updateGameTime", game_key);
	}

	public int getGameTime(String game_key) {
		// TODO Auto-generated method stub
		return ((Integer) selectOne("game.getGameTime", game_key)).intValue();
	}
}