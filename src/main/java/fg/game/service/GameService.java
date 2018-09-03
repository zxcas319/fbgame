package fg.game.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import fg.vo.UserVO;

@Service
public interface GameService {
	List<Map<String, Object>> getOrderUserPlayerInfo(String order_id) throws Exception;
	String makeGame(String key) throws Exception;
	void updateStatus(Map<String, Object> param) throws Exception;
	String getGameKey(String user_key) throws Exception;
	Map<String, Integer> getBasicCoord(String position) throws Exception;
	void saveGamePosition(List<Map<String, Object>> list) throws Exception;
	void resetGamePosition(String user_key) throws Exception;
	List<Map<String, Object>> getGameInfo(String game_key, String user_key) throws Exception;
	Map<String, Object> checkAwayUser(String user_key);
	String intoGame(String user_key) throws Exception;
	List<Map<String, Object>> getPlayerList(String user_key) throws Exception;
	int changePosition(Map<String, Object> param) throws Exception;
	void removePlayer(String player_key) throws Exception;
	void insertPlayer(Map<String, Object> param) throws Exception;
	Map<String, Object> makePercent(String section) throws Exception;
	Map<String, Object> makeAction(Map<String, Object> per) throws Exception;
	int[][] getSection(String gk) throws Exception;
	void removeGameInfo(String game_key) throws Exception;
	void removeGameLog(String game_key) throws Exception;
	void removeGame(String game_key) throws Exception;
	String checkHome(String game_key) throws Exception;
	void createGameLog(String game_key) throws Exception;
	void updateGameTime(String game_key) throws Exception;
	int getGameTime(String game_key) throws Exception;
}