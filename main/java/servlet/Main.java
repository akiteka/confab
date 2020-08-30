package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.GetMutterListLogic;
import model.Mutter;
import model.PostMutterLogic;
import model.Users;

//@WebServlet("/servlet/Main")
public class Main extends HttpServlet {
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
		// ログイン済みの場合

		//main.jspで選択しているトピック名をセッションスコープから取得(初期はinitialをlogiin_dbで設定している。)
		HttpSession topicSelectSessionStore = request.getSession();
		Mutter topicSelectSession = (Mutter) topicSelectSessionStore.getAttribute("topicSelectSession");

		//つぶやきリストを取得して、リクエストスコープに保存
		GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
		List<Mutter> mutterList = getMutterListLogic.execute(topicSelectSession);
		request.setAttribute("mutterList", mutterList);

		//トピック一覧を取得して、リクエストスコープに保存
		GetMutterListLogic getTopicsLogic = new GetMutterListLogic();
		List<Mutter> topicsList = getTopicsLogic.execute();
		request.setAttribute("topicsList", topicsList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/main.jsp");
		dispatcher.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// ログインしているか確認するためセッションスコープからユーザー情報を取得
		HttpSession session = request.getSession();
		Users loginUser = (Users) session.getAttribute("loginUser");
		if (loginUser == null) { // ログインしていない場合
			// エラー画面にフォワード
			RequestDispatcher dispatcher =	request.getRequestDispatcher("/error.jsp");
			dispatcher.forward(request, response);
		}else {

		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String text = request.getParameter("text");							//main.jspで入力された書き込みの取得

		String topicPostTmp = request.getParameter("topicPost");		//main.jspで入力されたトピックの取得
		Mutter topicPost = new Mutter(topicPostTmp);

		String topicSelectTmp = request.getParameter("topicSelect");	//main.jspの左枠でクリックされたトピック名の取得


		if(topicSelectTmp!=null) { //main.jspの左枠でトピック名がクリックされたとき

			Mutter topicSelect = new Mutter(topicSelectTmp);

			//クリックされたトピック名をセッションスコープに保存
			HttpSession topicSelectSessionStore = request.getSession();
			topicSelectSessionStore.setAttribute("topicSelectSession", topicSelect);

		}else {

			if(text != null && text.length() == 0) {
				//エラーメッセージをリクエストスコープに保存
				request.setAttribute("errorMsg","※書き込みが入力されていません");

			}else if(text != null && text.length() > 100){
				request.setAttribute("errorMsg","※文字数は400文字以内にしてください。");

			}else if(topicPostTmp != null && topicPostTmp.length() == 0){
				request.setAttribute("errorMsg","※トピックが入力されていません。");
				topicPostTmp ="initial";

			}else if(topicPostTmp != null && topicPostTmp.length() > 20){
				request.setAttribute("errorMsg","※トピックは20文字以内にしてください。");
				topicPostTmp ="initial";

			}else {

				//つぶやきをつぶやきリストに追加
				Mutter mutter = new Mutter(loginUser.getName(),text,topicPost.getTopic());
				PostMutterLogic postMutterLogic = new PostMutterLogic();
				postMutterLogic.execute(mutter);

				//クリックされたトピック名をセッションスコープに保存
				HttpSession topicSelectSessionStore = request.getSession();
				topicSelectSessionStore.setAttribute("topicSelectSession", topicPost);
			}
		}

		//クリックされたトピック名をセッションスコープから取得
		HttpSession topicSelectSessionStore = request.getSession();
		Mutter topicSelectSession = (Mutter) topicSelectSessionStore.getAttribute("topicSelectSession");

		//つぶやきリストを取得して、リクエストスコープに保存
		GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
		List<Mutter> mutterList = getMutterListLogic.execute(topicSelectSession);
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
}
