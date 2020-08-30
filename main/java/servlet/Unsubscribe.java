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

//@WebServlet("/servlet/Unsubscribe")
public class Unsubscribe extends HttpServlet {
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
		// 退会画面にフォワード
		RequestDispatcher dispatcher =	request.getRequestDispatcher("/unsubscribe.jsp");
		dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {

		// セッションパラメータの取得
		HttpSession session = request.getSession();
		Users loginUser = (Users) session.getAttribute("loginUser");
		if (loginUser == null) { // ログインしていない場合
			// エラー画面にフォワード
			RequestDispatcher dispatcher =	request.getRequestDispatcher("/error.jsp");
			dispatcher.forward(request, response);
		}else {
		// 削除処理
		// データベースに接続
		try {
			//heroku上でデータベース接続に使用する情報
			Connection connection = null;
			String dbUri = System.getenv("url");
			String userName = System.getenv("user");
			String passWord = System.getenv("Password");


			Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(dbUri, // "jdbc:postgresql://[場所(Domain)]:[ポート番号]/[DB名]"
            		userName, // ログインロール
            		passWord); // パスワード

			// DELETE文を準備
			String sql = "DELETE FROM USERS WHERE NAME=? AND PASS=?";
			PreparedStatement pStmt = connection.prepareStatement(sql);
			pStmt.setString(1,loginUser.getName());
			pStmt.setString(2,loginUser.getPass());

			//DELETE文を実行
			int result = pStmt.executeUpdate();
			System.out.println(result);

			// DELETE成功時の処理
			if (result==1) {

				//セッションスコープを破棄
				session.invalidate();

				//退会結果画面にフォワード
				RequestDispatcher dispatcher =	request.getRequestDispatcher("/unsubscribeResult.jsp");
				dispatcher.forward(request, response);
			}else {

				// ユーザー情報をセッションスコープに保存
				// HttpSession session = request.getSession();
				//session.setAttribute("loginUser", null);

				// ログイン結果画面にフォワード
				RequestDispatcher dispatcher =
						request.getRequestDispatcher
						("/loginResult.jsp");
				dispatcher.forward(request, response);
			}


		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	}
}
