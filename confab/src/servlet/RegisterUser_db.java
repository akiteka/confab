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
import model.UsersIDlogic;

@WebServlet("/servlet/RegisterUser_db")
public class RegisterUser_db extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// セッションスコープに保存された登録ユーザ
		HttpSession session = request.getSession();
		Users registerUser = (Users) session.getAttribute("registerUser");

		// フォワード先
		String forwardPath = null;

		// サーブレットクラスの動作を決定する「action」の値を
		// リクエストパラメータから取得
		String action = request.getParameter("action");

		// 「登録の開始」をリクエストされたときの処理
		if (action == null) {
			// フォワード先を設定
			forwardPath = "/registerForm.jsp";
		}

		// 登録確認画面から「登録実行」をリクエストされたときの処理
		else if (action.equals("done")) {

			Users users = new Users(registerUser.getName());
			UsersIDlogic bo = new UsersIDlogic();
			boolean result = bo.execute(users);

			if(result) {
				request.setAttribute("errorMsg","※既に使用されているIDです。ほかのIDを入力してください。");
				// フォワード先を設定
				forwardPath = "/registerForm.jsp";
			}else{

				// 登録処理の呼び出し
				// データベースに接続
				try {
					Class.forName("org.h2.Driver");
					Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/c:\\data\\user", "sa", "1234");

					// INSERT文を準備
					String sql = "INSERT INTO USERS(NAME,PASS) VALUES(?,?)";
					PreparedStatement pStmt = conn.prepareStatement(sql);
					pStmt.setString(1,registerUser.getName());
					pStmt.setString(2,registerUser.getPass());

					//INSERT文を実行
					pStmt.executeUpdate();

					// 登録後のフォワード先を設定
					forwardPath = "/registerDone.jsp";


				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
				}

				// 不要となったセッションスコープ内のインスタンスを削除
				session.removeAttribute("registerUser");
			}
		}

		// 設定されたフォワード先にフォワード
		RequestDispatcher dispatcher =	request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");		//registerFormで入力されたユーザー名を取得
		String pass = request.getParameter("pass");		//registerFormで入力されたパスワードを取得

		// 登録するユーザーの情報を設定
		Users registerUser = new Users(name, pass);

		// セッションスコープに登録ユーザーを保存
		HttpSession session = request.getSession();
		session.setAttribute("registerUser", registerUser);

		//入力値チェック(入力が0文字)
		if(registerUser.getName().length() == 0 || registerUser.getPass().length() == 0) {

			//エラーメッセージをリクエストスコープに保存
			request.setAttribute("errorMsg","※ID、パスワードを入力してください。");

			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/registerForm.jsp");
			dispatcher.forward(request, response);

		}else if(registerUser.getName().length() > 100 || registerUser.getPass().length() > 100){

			//エラーメッセージをリクエストスコープに保存
			request.setAttribute("errorMsg","※ID、パスワードは100文字以下にしてください。");

			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/registerForm.jsp");
			dispatcher.forward(request, response);

		}else {

			// フォワード
			RequestDispatcher dispatcher =	request.getRequestDispatcher("/registerConfirm.jsp");
			dispatcher.forward(request, response);
		}
	}
}