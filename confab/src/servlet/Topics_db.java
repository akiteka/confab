package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;

public class Topics_db{
	//データベース接続に使用する情報
	private final String JDBC_URL="jdbc:h2:tcp://localhost/c:\\data\\user";
	private final String DB_USER = "sa";
	private final String DB_PASS = "1234";

	public List<Mutter> topicAll(){
		List<Mutter>topicList=new ArrayList<>();

		//データベースへ接続
		try{
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS);
			//SELECT文を準備
			String sql = "SELECT DISTINCT TOPIC FROM MUTTER";
			PreparedStatement pStmt = conn.prepareStatement(sql);

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