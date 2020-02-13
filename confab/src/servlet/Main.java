package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.GetMutterListLogic;
import model.Mutter;
import model.PostMutterLogic;
import model.Users;

@WebServlet("/servlet/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//main.jspで選択しているトピック名をセッションスコープから取得(初期はinitialをlogiin_dbで設定している。)
		HttpSession topic_select_session_store = request.getSession();
		Mutter topic_select_session = (Mutter) topic_select_session_store.getAttribute("topic_select_session");

		//つぶやきリストを取得して、リクエストスコープに保存
		GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
		List<Mutter> mutterList = getMutterListLogic.execute(topic_select_session);
		request.setAttribute("mutterList", mutterList);

		//トピック一覧を取得して、リクエストスコープに保存
		GetMutterListLogic getTopicsLogic = new GetMutterListLogic();
		List<Mutter> topicsList = getTopicsLogic.execute();
		request.setAttribute("topicsList", topicsList);

		// ログインしているか確認するためセッションスコープからユーザー情報を取得
		HttpSession session = request.getSession();
		Users loginUser = (Users) session.getAttribute("loginUser");
		if (loginUser == null) { // ログインしていない場合
			// リダイレクト
			response.sendRedirect("/confab/");
		} else { // ログイン済みの場合
			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/main.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String text = request.getParameter("text");							//main.jspで入力された書き込みの取得

		String topic_post_tmp = request.getParameter("topic_post");		//main.jspで入力されたトピックの取得
		Mutter topic_post = new Mutter(topic_post_tmp);

		String topic_select_tmp = request.getParameter("topic_select");	//main.jspの左枠でクリックされたトピック名の取得


		if(topic_select_tmp!=null) { //main.jspの左枠でトピック名がクリックされたとき

			Mutter topic_select = new Mutter(topic_select_tmp);

			//クリックされたトピック名をセッションスコープに保存
			HttpSession topic_select_session_store = request.getSession();
			topic_select_session_store.setAttribute("topic_select_session", topic_select);

		}else {

			if(text != null && text.length() == 0) {
				//エラーメッセージをリクエストスコープに保存
				request.setAttribute("errorMsg","※つぶやきが入力されていません");

			}else if(text != null && text.length() > 100){
				request.setAttribute("errorMsg","文字数が100を超えています。");

			}else if(topic_post_tmp != null && topic_post_tmp.length() == 0){
				request.setAttribute("errorMsg","※トピックが入力されていません。");
				topic_post_tmp ="initial";

			}else {

				//セッションスコープに保存されたユーザー情報を取得
				HttpSession session = request.getSession();
				Users loginUser = (Users) session.getAttribute("loginUser");

				//つぶやきをつぶやきリストに追加
				Mutter mutter = new Mutter(loginUser.getName(),text,topic_post.getTopic());
				PostMutterLogic postMutterLogic = new PostMutterLogic();
				postMutterLogic.execute(mutter);

				//クリックされたトピック名をセッションスコープに保存
				HttpSession topic_select_session_store = request.getSession();
				topic_select_session_store.setAttribute("topic_select_session", topic_post);
			}
		}

		//クリックされたトピック名をセッションスコープから取得
		HttpSession topic_select_session_store = request.getSession();
		Mutter topic_select_session = (Mutter) topic_select_session_store.getAttribute("topic_select_session");

		//つぶやきリストを取得して、リクエストスコープに保存
		GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
		List<Mutter> mutterList = getMutterListLogic.execute(topic_select_session);
		request.setAttribute("mutterList", mutterList);

		//トピック一覧を取得して、リクエストスコープに保存
		GetMutterListLogic getTopicsLogic = new GetMutterListLogic();
		List<Mutter> topicsList = getTopicsLogic.execute();
		request.setAttribute("topicsList", topicsList);

		//メイン画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/main.jsp");
		dispatcher.forward(request,response);
	}
}
