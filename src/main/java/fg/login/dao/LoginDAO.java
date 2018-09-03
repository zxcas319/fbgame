package fg.login.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import fg.common.dao.AbstractDAO;
import fg.vo.UserVO;

@Repository("loginDAO")
public class LoginDAO extends AbstractDAO {

	public String getUserKey(Map<String, Object> info) {
		// TODO Auto-generated method stub
		return (String)selectOne("login.getUserKey", info);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> findUserInfo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return (Map<String, Object>) selectOne("login.findUserInfo", param);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> checkId(String id) {
		// TODO Auto-generated method stub
		return (Map<String, Object>) selectOne("login.checkId", id);
	}

	public void signupAction(Map<String, Object> param) {
		// TODO Auto-generated method stub
		insert("login.signupAction", param);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> checkName(String name) {
		// TODO Auto-generated method stub
		return (Map<String, Object>) selectOne("login.checkName", name);
	}

	public UserVO getUserInfo(Map<String, Object> info) {
		// TODO Auto-generated method stub
		return (UserVO) selectOne("login.getUserInfo", info);
	}
}