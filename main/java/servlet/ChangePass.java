package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Users;


//@WebServlet("/servlet/ChangePass")
public class ChangePass extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// ログインしているか確認するためセッションスコープからユーザー情報を取得
		HttpSession session = request.getSession();
		Users loginUser = (Users) session.getAttribute("loginUser");
		if (loginUser == null) { // ログインしていない場合
			// エラー画面にフォワード
			RequestDispatcher dispatcher =	request.getRequestDispatcher("/error.jsp");
			dispatcher.forward(request, response);
		}else {
		// パスワード変更画面にフォワード
		RequestDispatcher dispatcher =	request.getRequestDispatcher("/changeUserPass.jsp");
		dispatcher.forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {

		//ユーザ―名変更後に改めてユーザ―名とパスワードをセッションスコープに保存するため
		HttpSession session = request.getSession();
		Users loginUser = (Users) session.getAttribute("loginUser");
		if (loginUser == null) { // ログインしていない場合
			// エラー画面にフォワード
			RequestDispatcher dispatcher =	request.getRequestDispatcher("/error.jsp");
			dispatcher.forward(request, response);
		}else {

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String oldPass = request.getParameter("old");		     //ChangeUserPass.jspで入力された現パスワードの取得
		String newPass = request.getParameter("new");			 //ChangeUserPass.jspで入力された新規パスワードの取得
		String confirmPass = request.getParameter("confirm"); //ChangeUserPass.jspで入力された確認欄の取得

		if(confirmPass.length()==0){
			//エラーメッセージをリクエストスコープに保存
			request.setAttribute("errorMsg","※パスワードを入力してください。");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/changeUserPass.jsp");
			dispatcher.forward(request, response);

		}else if(confirmPass.length()>20){

			request.setAttribute("errorMsg","※パスワードは20文字以内にしてください。");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/changeUserPass.jsp");
			dispatcher.forward(request, response);

		}else if(oldPass.equals(loginUser.getPass()) &&newPass.equals(confirmPass)) {// パスワード変更可能時の処理
			// データベースに接続
			try {
				Class.forName("org.postgresql.Driver");
				//heroku上でデータベース接続に使用する情報を取得する
				Connection connection = null;
				String dbUri = System.getenv("url");
				String userName = System.getenv("user");
				String passWord = System.getenv("Password");


				Class.forName("org.postgresql.Driver");
	            connection = DriverManager.getConnection(dbUri, // "jdbc:postgresql://[場所(Domain)]:[ポート番号]/[DB名]"
	            		userName, // ログインロール
	            		passWord); // パスワード

				//UPDATE文を準備
				String sql = "UPDATE USERS SET PASS=? WHERE NAME=?";
				PreparedStatement pStmt = connection.prepareStatement(sql);
				pStmt.setString(1,confirmPass);
				pStmt.setString(2,loginUser.getName());

				//UPDATE
				pStmt.executeUpdate();

				//新しいID,Passをセッションスコープに保存
				Users user = new Users(loginUser.getName(),newPass);
				HttpSession sessionNew = request.getSession();
				sessionNew.setAttribute("loginUser", user);

				// パスワード変更画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/changeUserPassDone.jsp");
				dispatcher.forward(request, response);

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			request.setAttribute("errorMsg","※入力を確認してください。");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/changeUserPass.jsp");
			dispatcher.forward(request, response);
		}
	}
	}
}
