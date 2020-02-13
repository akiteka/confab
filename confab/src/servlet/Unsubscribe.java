package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Users;

@WebServlet("/servlet/Unsubscribe")
public class Unsubscribe extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 退会画面にフォワード
		RequestDispatcher dispatcher =	request.getRequestDispatcher("/unsubscribe.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {

		// セッションパラメータの取得
		HttpSession session = request.getSession();
		Users loginUser = (Users) session.getAttribute("loginUser");

		// 削除処理
		// データベースに接続
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/c:\\data\\user", "sa", "1234");

			// DELETE文を準備
			String sql = "DELETE FROM USERS WHERE NAME=? AND PASS=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
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
				RequestDispatcher dispatcher =	request.getRequestDispatcher("/unsubscribe_result.jsp");
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
