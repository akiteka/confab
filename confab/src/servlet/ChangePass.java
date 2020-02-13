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


@WebServlet("/servlet/ChangePass")
public class ChangePass extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// パスワード変更画面にフォワード
		RequestDispatcher dispatcher =	request.getRequestDispatcher("/ChangeUserPass.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {

		//ユーザ―名変更後に改めてユーザ―名とパスワードをセッションスコープに保存するため
		HttpSession session = request.getSession();
		Users loginUser = (Users) session.getAttribute("loginUser");

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String old_pass = request.getParameter("old");		     //ChangeUserPass.jspで入力された現パスワードの取得
		String new_pass = request.getParameter("new");			 //ChangeUserPass.jspで入力された新規パスワードの取得
		String confirm_pass = request.getParameter("confirm"); //ChangeUserPass.jspで入力された確認欄の取得

		if(old_pass.equals(loginUser.getPass()) &&new_pass.equals(confirm_pass)) {// パスワード変更可能時の処理
			// データベースに接続
			try {
				Class.forName("org.h2.Driver");
				Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/c:\\data\\user", "sa", "1234");

				//UPDATE文を準備
				String sql = "UPDATE USERS SET PASS=? WHERE NAME=?";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setString(1,confirm_pass);
				pStmt.setString(2,loginUser.getName());

				//UPDATE
				pStmt.executeUpdate();

				//新しいID,Passをセッションスコープに保存
				Users user = new Users(loginUser.getName(),new_pass);
				HttpSession session_new = request.getSession();
				session_new.setAttribute("loginUser", user);

				// パスワード変更画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/ChangeUserPassDone.jsp");
				dispatcher.forward(request, response);

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else if(confirm_pass.length()==0){
			//エラーメッセージをリクエストスコープに保存
			request.setAttribute("errorMsg","パスワードを入力してください。");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/ChangeUserPass.jsp");
			dispatcher.forward(request, response);

		}else if(confirm_pass.length()>100){

			request.setAttribute("errorMsg","※パスワードは100文字以下にしてください。");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/ChangeUserPass.jsp");
			dispatcher.forward(request, response);
		}else{
			request.setAttribute("errorMsg","入力を確認してください。");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/ChangeUserPass.jsp");
			dispatcher.forward(request, response);
		}
	}
}
