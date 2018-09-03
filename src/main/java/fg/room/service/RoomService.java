package fg.room.service;

import java.util.Map;
import java.util.List;
public interface RoomService {
	void getRoomInfo(Map<String, Object> map) throws Exception;

	List<Map<String, Object>> getRoomId()throws Exception;

	int getPlayerOverall(List<Map<String, Object>> list)throws Exception;

	Map<String, Object> initInfo(Map<String, Object> map)throws Exception;

	void roomCount()throws Exception;

	List<Map<String, Object>> getRoundInfo(String key)throws Exception;

	String getRoomKeyInfo(String key)throws Exception;

}
