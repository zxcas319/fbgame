package fg.room.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import fg.room.dao.RoomDAO;
import fg.room.service.RoomService;
import fg.create.dao.CreateDAO;
@Service("roomService")

public class RoomServiceImpl implements RoomService{
	@Resource(name="roomDAO")
    private RoomDAO roomDAO;
	
	@Resource(name="createDAO")
    private CreateDAO createDAO;
	
    Logger log = Logger.getLogger(this.getClass());
	@Override
	public void getRoomInfo(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		
		//나랑 같은 레이팅에 user_key 가 ,null이 아닌 방이 존재 하는지 찾기 
		String room_key = roomDAO.findgameRoom(map);
		String user_key=(String) map.get("user_key");
		int rating=((Integer) map.get("rating")).intValue();
		//내 user_key 로 만들어진 room_key 가 있는지 찾기
		String valid=roomDAO.duplicateId(user_key);
		
		if( valid==null || valid.equals("")) {
	
		if (room_key!=null && !room_key.equals(""))
		{
			roomDAO.getUpdateInfo(map);
		}
			
		else 
			roomDAO.getInsertInfo(map);
		}
		
		int a=roomDAO.getHostUser(user_key);
		if(a==1) { 
			String temp_room_key=getRoomKeyInfo(user_key);
			List<Map<String, Object>> list1 = roomDAO.getRoundInfo(temp_room_key);
			list1.get(0).put("rating", rating);
			roomDAO.InsertGameKey(list1);
		}
		
	}
	@Override
	public List<Map<String, Object>> getRoomId() throws Exception {
		// TODO Auto-generated method stub
		return roomDAO.getRoomId();
	}
	@Override
	public int getPlayerOverall(List<Map<String, Object>> list) throws Exception {
		// TODO Auto-generated method stub
		
		String key = "";
		String position = "";
		Object overall = 0;
		int sum=0;
		for(int i = 0;i < list.size();i++) {
			key = (String) list.get(i).get("player_key");
			position = (String) list.get(i).get("position_detail");
			overall = createDAO.getPlayerOverall(position, key).get("average");
			
			sum += Integer.parseInt(overall.toString());
		}
		sum= (int) (sum/list.size());
		return sum;
	}
	@Override
	public Map<String, Object> initInfo(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return roomDAO.initInfo(map);
	}
	@Override
	public void roomCount() throws Exception {
		// TODO Auto-generated method stub
		roomDAO.roomCount();
	}
	@Override
	public List<Map<String, Object>> getRoundInfo(String key) throws Exception {
		// TODO Auto-generated method stub
		return roomDAO.getRoundInfo(key);
	}
	@Override
	public String getRoomKeyInfo(String key) throws Exception {
		// TODO Auto-generated method stub
		return roomDAO.getRoomKeyInfo(key);
	}
	
}