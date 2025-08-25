package chapter6.controller;

import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import chapter6.logging.InitApplication;

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

	    @Override


}
