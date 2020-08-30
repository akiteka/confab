package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;

public class TopicsDB{
	//データベース接続に使用する情報

	public List<Mutter> topicAll(){
		List<Mutter>topicList=new ArrayList<>();


		//データベース接続に使用する情報
		Connection connection = null;
		String dbUri = System.getenv("url");
		String userName = System.getenv("user");
		String passWord = System.getenv("Password");

		//ローカルでデータベース接続に使用する情報を取得する
		if(dbUri==null){
			dbUri="jdbc:postgresql://localhost:5432/confab";
			userName="postgres";
			passWord="r-faced6037";
		}

		//データベースへ接続
		try{
			Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(dbUri, // "jdbc:postgresql://[場所(Domain)]:[ポート番号]/[DB名]"
            		userName, // ログインロール
            		passWord); // パスワード
			//SELECT文を準備
			String sql = "SELECT DISTINCT TOPIC FROM MUTTER";
			PreparedStatement pStmt = connection.prepareStatement(sql);

			//SELECTを実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			while(rs.next()) {
				String topics = rs.getString("TOPIC");
				Mutter add_topics = new Mutter(topics);
				System.out.println(add_topics.getTopic());
				topicList.add(add_topics);
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return topicList;
	}
}