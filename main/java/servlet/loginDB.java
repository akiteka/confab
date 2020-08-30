package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher; //foward
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.LoginLogic;
import model.Mutter;
import model.Users;


/**
 * Servlet implementation class login
 */
//@WebServlet("/servlet/login_db")
public class loginDB extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// リダイレクト先
		response.sendRedirect("./index.jsp");
	}


	protected void doPost(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name"); //index.jspで入力されたユーザー名の取得
		String pass = request.getParameter("pass"); //index.jspで入力されたパスワードの取得

		// ログイン処理
		Users user = new Users(name,pass);
		LoginLogic bo = new LoginLogic();
		boolean result = bo.execute(user);


		if (result) {// ログイン成功時の処理

			// ユーザー情報をセッションスコープに保存
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", user);

			//Main.java起動時にnullにならないように、topic_selectの初期条件をセッションスコープに保存
			Mutter topicInitial = new Mutter("initial");
			HttpSession topicSelectSessionStore = request.getSession();
			topicSelectSessionStore.setAttribute("topicSelectSession", topicInitial);

			// ログイン結果画面にフォワード
			RequestDispatcher dispatcher =	request.getRequestDispatcher("/loginResult.jsp");
			dispatcher.forward(request, response);

		}else {

			// ユーザー情報をセッションスコープから破棄
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", null);

			// ログイン結果画面にフォワード
			RequestDispatcher dispatcher =	request.getRequestDispatcher("/loginResult.jsp");
			dispatcher.forward(request, response);
		}
	}
}

