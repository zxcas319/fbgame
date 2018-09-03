package fg.login.service;

import java.util.Map;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import fg.login.dao.LoginDAO;
import fg.vo.UserVO;

@Service("loginService")
public class LoginServiceImpl implements LoginService{
	@Resource(name="loginDAO")
    private LoginDAO loginDAO;

    Logger log = Logger.getLogger(this.getClass());
    
	public Boolean checkLoginInfo(Map<String, Object> info) {
		String user_key = null;
		
		user_key = loginDAO.getUserKey(info);
		
		if(user_key != null)
			return true;
		else
			return false;
	}

	@Override
	public UserVO getUserInfo(Map<String, Object> info) throws Exception {
		UserVO result = loginDAO.getUserInfo(info);
		return result;
	}
	
	@Override
	public Map<String, Object> findUserInfo(Map<String, Object> info) throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.findUserInfo(info);
	}

	@Override
	public Map<String, Object> checkId(String id) throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.checkId(id);
	}

	@Override
	public void signupAction(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		loginDAO.signupAction(param);
	}

	@Override
	public Map<String, Object> checkName(String name) throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.checkName(name);
	}
}