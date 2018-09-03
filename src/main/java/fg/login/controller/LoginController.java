package fg.login.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fg.common.utils.Gmailsend;
import fg.login.service.LoginService;
import fg.vo.UserVO;

@Controller
public class LoginController {
    Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "loginService")
	private LoginService loginService;

	@RequestMapping(value = "/login/loginPage.do")
	public ModelAndView loginPage() throws Exception {
		ModelAndView mv = new ModelAndView("/login/loginPage");
		return mv;
	}

	@RequestMapping(value = "/login/signupPage.do")
	public ModelAndView signupPage() throws Exception {
		ModelAndView mv = new ModelAndView("/login/signupPage");
		return mv;
	}

	@RequestMapping(value = "/login/loginAction.do")
	public @ResponseBody Map<String, Object> loginAction(HttpServletRequest req, HttpSession session) throws Exception {
		String id = req.getParameter("send_id");
		String pw = req.getParameter("send_pw");

		Map<String, Object> info = new HashMap<String, Object>();
		info.put("id", id);
		info.put("pw", pw);

		Map<String, Object> result = new HashMap<String, Object>();
		if (id.equals("") || id == null) {
			result.put("error", "Please enter ID.");
			log.debug(" Request login Fail \t:  ID is null");

			return result;
		} else if (pw.equals("") || pw == null) {
			result.put("error", "Please enter PW.");
			log.debug(" Request login Fail \t:  PW is null");

			return result;
		}

		Boolean valid = loginService.checkLoginInfo(info);
		if (valid) {
			UserVO user = loginService.getUserInfo(info);
			session.setAttribute("userSession", user);
			String key = user.getUserKey();
			String created_team = user.getTeamKey();
			result.put("user_key", key);
			result.put("team_key", created_team);

			log.debug(" Request id \t:  " + id);
			log.debug(" Request pw \t:  " + pw);
			log.debug(" Response key \t:  " + key);
			log.debug(" Response team \t:  " + created_team);
		} else {
			result.put("error", "Please check ID or PW.");
			log.debug(" Request login Fail \t:  No Matching Information.");
		}

		return result;
	}

	@RequestMapping(value = "/login/findUserInfo.do")
	public @ResponseBody Map<String, Object> findUserInfo(HttpServletRequest req) throws Exception {
		String email = req.getParameter("send_email");
		String mode = req.getParameter("send_mode");

		Map<String, Object> info = new HashMap<String, Object>();
		info.put("email", email);

		Map<String, Object> result = new HashMap<String, Object>();
		if (mode.equals("pw")) {
			String id = req.getParameter("send_id");
			if (id.equals("") || id == null) {
				result.put("error", "Please enter ID");
				return result;
			} else if (email.equals("") || email == null) {
				result.put("error", "Please enter E-mail");
				return result;
			} else {
				info.put("id", id);
				result = loginService.findUserInfo(info);
				if (result == null) {
					result = new HashMap<String, Object>();
					result.put("error", "No match data");
				}
			}
		} else if (!email.equals("") && email != null) {
			result = loginService.findUserInfo(info);
			if (result == null) {
				result = new HashMap<String, Object>();
				result.put("error", "No match E-mail");
			}
		} else
			result.put("error", "Please enter E-mail");

		return result;
	}

	@RequestMapping(value = "/login/findPage.do")
	public ModelAndView findPage(HttpServletRequest req) throws Exception {
		ModelAndView mv = new ModelAndView("login/findPage");
		String mode = req.getParameter("send_mode");
		mv.addObject("mode", mode);
		return mv;
	}

	@RequestMapping(value = "/login/checkId.do")
	public @ResponseBody Map<String, Object> checkId(HttpServletRequest req) throws Exception {
		String id = req.getParameter("send_id");

		Map<String, Object> result = new HashMap<String, Object>();
		if (id.equals("") || id == null) {
			result.put("error", "Please enter ID.");
			log.debug(" Request login Fail \t:  ID is null");
			return result;
		}

		result = loginService.checkId(id); // 쿼리문 결과

		return result;
	}

	@RequestMapping(value = "/login/checkName.do")
	public @ResponseBody Map<String, Object> checkName(HttpServletRequest req) throws Exception {
		String name = req.getParameter("send_name");

		Map<String, Object> result = new HashMap<String, Object>();
		if (name.equals("") || name == null) {
			result.put("error", "Please enter Name.");
			log.debug(" Request login Fail \t:  Name is null");
			return result;
		}

		result = loginService.checkName(name); // 쿼리문 결과

		return result;
	}

	@RequestMapping(value = "/login/authEmail.do")
	public @ResponseBody String authEmail(HttpServletRequest req) throws Exception {
		String auth_num = "";
		String send_email = req.getParameter("send_email");

		Random generator = new Random();
		int string_rand = 0;
		int num_rand = 0;
		for(int i = 0;i < 4;i++) {
			string_rand = generator.nextInt(26) + 65;
			num_rand = generator.nextInt(10) + 48;
			auth_num += Character.toString((char) string_rand);
			auth_num += Character.toString((char) num_rand);
		}
		log.debug(" Send auth number \t:  " + auth_num + " to " + send_email);
		
		Gmailsend mail = new Gmailsend();
		mail.GmailSet(send_email, "FG game auth number.", auth_num);
		
		return auth_num;
	}

	@RequestMapping(value = "/login/signupAction.do")
	public @ResponseBody void signupAction(HttpServletRequest req) throws Exception {
		String id = req.getParameter("send_id");
		String name = req.getParameter("send_name");
		String pw = req.getParameter("send_pw");
		String send_email = req.getParameter("send_email");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("name", name);
		param.put("pw", pw);
		param.put("email", send_email);

		loginService.signupAction(param);
	}

	@RequestMapping(value = "/logout/logoutAction.do")
	public @ResponseBody void logoutAction(HttpServletRequest req, HttpSession session) throws Exception {
		session.removeAttribute("userSession");
	}
}