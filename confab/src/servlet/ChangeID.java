package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher; //foward
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Users;
import model.UsersIDlogic;


@WebServlet("/servlet/ChangeID")
public class ChangeID extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// ユーザー名変更画面にフォワード
		RequestDispatcher dispatcher =	request.getRequestDispatcher("/ChangeUserID.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//ユーザ―名変更後に改めてユーザ―名とパスワードをセッションスコープに保存するため
		//及び現ユーザー名を検索するため。
		HttpSession session = request.getSession();
		Users loginUser = (Users) session.getAttribute("loginUser");

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name"); //ChangeUserID.jspで入力された新規ユーザ名の取得
		String pass = loginUser.getPass(); 			//ユーザ―名変更後に改めてログイン状態をセッションスコープに保存するため。



		// データベースに接続
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/c:\\data\\user", "sa", "1234");

			//ユーザー名の被り検索
			Users users = new Users(name);
			UsersIDlogic bo = new UsersIDlogic();
			boolean result = bo.execute(users);

			if(result) {
				//エラーメッセージをリクエストスコープに保存
				request.setAttribute("errorMsg","※既に使用されているIDです。ほかのユーザー名を入力してください。");

				RequestDispatcher dispatcher = request.getRequestDispatcher("/ChangeUserID.jsp");
				dispatcher.forward(request, response);

			}else if(name.length() == 0){
				request.setAttribute("errorMsg","ユーザー名を入力してください。");

				RequestDispatcher dispatcher = request.getRequestDispatcher("/ChangeUserID.jsp");
				dispatcher.forward(request, response);

			}else if(name.length()>100){
				request.setAttribute("errorMsg","※IDは100文字以下にしてください。");

				RequestDispatcher dispatcher = request.getRequestDispatcher("/ChangeUserID.jsp");
				dispatcher.forward(request, response);

			}else{// ユーザー名変更可能時の処理

			//UPDATE文を準備
			String sql = "UPDATE USERS SET NAME=? WHERE NAME=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,name);
			pStmt.setString(2,loginUser.getName());


			//UPDATE
			pStmt.executeUpdate();

			//新しいユーザー名,パスワードを改めてセッションスコープに保存
			Users user = new Users(name,pass);
			HttpSession session_new = request.getSession();
			session_new.setAttribute("loginUser", user);


			RequestDispatcher dispatcher = request.getRequestDispatcher("/ChangeUserIDDone.jsp");
			dispatcher.forward(request, response);
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
