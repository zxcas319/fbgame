package fg.common.logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import fg.common.logger.LoggerInterceptor;
import fg.vo.UserVO;

public class LoggerInterceptor extends HandlerInterceptorAdapter {
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("======================================          START         ======================================");
			log.debug(" Request URI \t:  " + request.getRequestURI());
		}
		try {
			UserVO user = (UserVO) request.getSession().getAttribute("userSession");
			if(user == null && !request.getRequestURI().contains("/fg/login/")) {
				response.sendRedirect("/fg/login/loginPage.do");
				return false;
			}
			else if(user != null && request.getRequestURI().contains("/fg/login/")) {
				response.sendRedirect("/fg/start/mainPage.do");
				return false;
			}
			else if(user != null && user.getTeamKey() != null && request.getRequestURI().contains("/fg/create/")) {
				response.sendRedirect("/fg/start/mainPage.do");
				return false;
			}
			else if(user != null && user.getTeamKey() == null && !request.getRequestURI().contains("/fg/create/")) {
				response.sendRedirect("/fg/create/createTeam.do");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("======================================           END          ======================================\n");
		}
	}
}