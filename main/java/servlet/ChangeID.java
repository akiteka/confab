package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher; //foward
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Users;
import model.UsersIDLogic;


//@WebServlet("/servlet/ChangeID")
public class ChangeID extends HttpServlet {
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
		// ユーザー名変更画面にフォワード
		RequestDispatcher dispatcher =	request.getRequestDispatcher("/changeUserID.jsp");
		dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//ユーザ―名変更後に改めてユーザ―名とパスワードをセッションスコープに保存するため及び現ユーザー名を検索するため。
		HttpSession session = request.getSession();
		Users loginUser = (Users) session.getAttribute("loginUser");

		if (loginUser == null) { // ログインしていない場合
			// エラー画面にフォワード
			RequestDispatcher dispatcher =	request.getRequestDispatcher("/error.jsp");
			dispatcher.forward(request, response);
		}else {

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name"); //ChangeUserID.jspで入力された新規ユーザ名の取得
		String pass = loginUser.getPass(); 			//ユーザ―名変更後に改めてログイン状態をセッションスコープに保存するため。



		// データベースに接続
		try {
			//heroku上でデータベース接続に使用する情報を取得する
			Connection connection = null;
			String dbUri = System.getenv("url");
			String userName = System.getenv("user");
			String passWord = System.getenv("Password");

			Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(dbUri, // "jdbc:postgresql://[場所(Domain)]:[ポート番号]/[DB名]"
            		userName, // ログインロール
            		passWord); // パスワード

			//ユーザー名の被り検索
			Users users = new Users(name);
			UsersIDLogic bo = new UsersIDLogic();
			boolean result = bo.execute(users);

			if(result) {
				//エラーメッセージをリクエストスコープに保存
				request.setAttribute("errorMsg","※既に使用されているIDです。ほかのユーザー名を入力してください。");

				RequestDispatcher dispatcher = request.getRequestDispatcher("/changeUserID.jsp");
				dispatcher.forward(request, response);

			}else if(name.length() == 0){
				request.setAttribute("errorMsg","※ユーザー名を入力してください。");

				RequestDispatcher dispatcher = request.getRequestDispatcher("/changeUserID.jsp");
				dispatcher.forward(request, response);

			}else if(name.length()>20){
				request.setAttribute("errorMsg","※ユーザー名は20文字以内にしてください。");

				RequestDispatcher dispatcher = request.getRequestDispatcher("/changeUserID.jsp");
				dispatcher.forward(request, response);

			}else{// ユーザー名変更可能時の処理

			//UPDATE文を準備
			String sql = "UPDATE USERS SET NAME=? WHERE NAME=?";
			PreparedStatement pStmt =connection.prepareStatement(sql);
			pStmt.setString(1,name);
			pStmt.setString(2,loginUser.getName());


			//UPDATE
			pStmt.executeUpdate();

			//新しいユーザー名,パスワードを改めてセッションスコープに保存
			Users user = new Users(name,pass);
			HttpSession sessionNew = request.getSession();
			sessionNew.setAttribute("loginUser", user);


			RequestDispatcher dispatcher = request.getRequestDispatcher("/changeUserIDDone.jsp");
			dispatcher.forward(request, response);
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	}
}
