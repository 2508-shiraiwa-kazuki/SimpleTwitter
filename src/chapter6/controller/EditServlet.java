package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/edit" })
public class EditServlet extends HttpServlet {

	 /**
	    * ロガーインスタンスの生成
	    */
	    Logger log = Logger.getLogger("twitter");

	    /**
	    * デフォルトコンストラクタ
	    * アプリケーションの初期化を実施する。
	    */
	    public EditServlet() {
	        InitApplication application = InitApplication.getInstance();
	        application.init();

	    }

	    //つぶやきの取得
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

		  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
	        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

		  HttpSession session = request.getSession();
		  List<String> errorMessages = new ArrayList<String>();

		  //if(  ){
		  //	trueの処理
		  //} else {
		  //	falseの処理（エラーメッセージ）

		  //URLに数値以外が入力されたときのエラーメッセージ
		  String id = request.getParameter("id");
		  Message message = new Message();
		  if((!StringUtils.isBlank(id)) && (id.matches("^[0-9]+$"))) {
			  int messageId = Integer.parseInt(request.getParameter("id"));
		      message = new MessageService().select(messageId);
		  }

	      //messageDao(つぶやき編集)でif(messages.isEmpty()) → return null としている
	      if(message != null) {
	    	  request.setAttribute("message", message);
	    	  request.getRequestDispatcher("edit.jsp").forward(request, response);
	      } else {
	      	  errorMessages.add("不正なパラメータが入力されました");
	      	  session.setAttribute("errorMessages", errorMessages);
	      	  response.sendRedirect("./");
	      	  return;

	      	  //1つめのif文でfalseだった場合messageはnullなので、2つめのif文のelseと重複する→両方判定できている
	      }
   	    }

	    //つぶやきの編集
	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	    	log.info(new Object(){}.getClass().getEnclosingClass().getName() +
	    			" : " + new Object(){}.getClass().getEnclosingMethod().getName());

	    	HttpSession session = request.getSession();
	    	List<String> errorMessages = new ArrayList<String>();

	    	String text = request.getParameter("text");
	    	int messageId = Integer.parseInt(request.getParameter("id"));

	    	Message message = new Message();

	    	if(!isValid(text, errorMessages)) {
	    		session.setAttribute("errorMessages",  errorMessages);
	    		session.setAttribute("message", message);
	    		response.sendRedirect("edit.jsp");
	    		return;
	    	}

	    	message.setId(messageId);
	    	message.setText(text);

	    	new MessageService().update(message);
	    	response.sendRedirect("./");

	    }

	    private boolean isValid(String text, List<String> errorMessages) {

	    	log.info(new Object(){}.getClass().getEnclosingClass().getName() +
	    			" : " + new Object(){}.getClass().getEnclosingMethod().getName());

	    	if(StringUtils.isBlank(text)) {
	    	    errorMessages.add("メッセージを入力してください");
	        } else if (140 < text.length()) {
	            errorMessages.add("140文字以下で入力してください");
	        }

	    	//その他のエラーが発生した場合
	    	if (errorMessages.size() != 0) {
	            return false;
	        }
	        return true;
	    }
}
