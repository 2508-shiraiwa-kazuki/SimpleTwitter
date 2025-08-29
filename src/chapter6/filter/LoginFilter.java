package chapter6.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chapter6.beans.User;

@WebFilter(urlPatterns = {"/edit", "/setting"})
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		//ServletRequest→「Http」ServletRequsetへ型変換
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();

		User user = (User) session.getAttribute("loginUser");

		//ログインしていない（loginUserがnull)の場合はログイン画面を表示
		if(user != null) {
			chain.doFilter(request, response);
		} else {
			session.setAttribute("errorMessages", "ログインしてください");
			//当初要求されたページではないのでRedirect
			httpResponse.sendRedirect("login");
		}
	}

	@Override
	public void init(FilterConfig config) {
	}

	@Override
	public void destroy() {
	}

}
