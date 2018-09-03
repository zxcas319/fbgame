package fg.room.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import fg.common.dao.AbstractDAO;

@Repository("roomDAO")
public class RoomDAO extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public Map<String, Object> getRoomInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return (Map<String, Object>) selectOne("room.getRoomInfo",map);
	}
	public void getInsertInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Map<String, Object> param  = new HashMap<String, Object>();
		String room_key ="";
		
		insert("room.getInsertInfo", map);
		
		
		for(int i = 0;i < 5;i++) {
		
		}
		for(int i = 0;i < 5;i++) {
			map.put("user_index", i+1);
			insert("room.getInsertRoom", map);
		}
	}
	
	public int getHostUser(String key) {
		// TODO Auto-generated method stub
		int a=((Integer)selectOne("room.getHostUser",key)).intValue();
		return a;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> RoundInfo() {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) selectList("room.RoundInfo","");
	}
	
	public void InsertGameKey(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		for(int i=0;i<5;i=i+2) {
			
			insert("room.tempGameKey", list.get(0).get("rating"));
			String temp_game_key=(String)selectOne("room.getTempGameKey","");
			
			
			list.get(i).put("tempGameKey", temp_game_key);
			list.get(i+1).put("tempGameKey", temp_game_key);
			
			int a=((Integer) list.get(i).get("testColumn")).intValue();
			list.get(i).put("testColumn", a);
			int b=((Integer) list.get(i+1).get("testColumn")).intValue();
			list.get(i+1).put("testColumn", b);
			update("room.InsertGameKey",list);
			update("room.deleteTempGameKey",list);
		}
		
	}
	
	
	
	public void getUpdateInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		update("room.getUpdateInfo", map);
	}
	public String findgameRoom(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		return (String)selectOne("room.findgameRoom",map);
	}
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getRoomId() {
		// TODO Auto-generated method stub
		
		return (List<Map<String, Object>>) selectList("room.getRoomId", "");
	}
	@SuppressWarnings("unchecked")
	public String duplicateId(String user_key ) {
		// TODO Auto-generated method stub
		return  (String)selectOne("room.duplicateId", user_key);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> initInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		
		return (Map<String, Object>) selectOne("room.initInfo", map);
	}
	public void roomCount() {
		// TODO Auto-generated method stub
		update("room.roomCount","");
	}
	@SuppressWarnings("unchecked")
	public String getRoomKeyInfo(String key) {
		// TODO Auto-generated method stub
		return (String)selectOne("room.getRoomKeyInfo", key);
	}
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getRoundInfo(String key) {
		// TODO Auto-generated method stub
		
		return (List<Map<String, Object>>) selectList("room.getRoundInfo", key);
	}
	
}