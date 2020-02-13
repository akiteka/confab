package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;

public class MutterDAO{
	//データベース接続に使用する情報
	private final String JDBC_URL="jdbc:h2:tcp://localhost/c:\\data\\user";
	private final String DB_USER = "sa";
	private final String DB_PASS = "1234";

	public List<Mutter> findAll(Mutter mutter){
		List<Mutter>mutterList=new ArrayList<>();
		String topic = mutter.getTopic();

		//データベースへ接続
		try{
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS);
			//SELECT文を準備
			String sql = "SELECT ID,NAME,TEXT,TOPIC FROM MUTTER WHERE TOPIC LIKE ? ORDER BY ID DESC  ";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			if(topic.equals("initial")) { //ログイン直後はすべてのトピックの書き込みを表示する。
				pStmt.setString(1,"%");
			}else {
				pStmt.setString(1,topic);
			}


			//SELECTを実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			//一致したユーザがいた場合
			//そのユーザを表すAccountインスタンスを作成
			while(rs.next()) {
				int id =rs.getInt("ID");
				String userName = rs.getString("NAME");
				String text = rs.getString("TEXT");
				String topic_db = rs.getString("TOPIC");
				Mutter add_mutter = new Mutter(id,userName,text,topic_db);
				mutterList.add(add_mutter);

			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return mutterList;
	}

	public boolean create(Mutter mutter) {
		String topic = mutter.getTopic();

		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
			Class.forName("org.h2.Driver");

			//INSERT文を準備
			String sql = "INSERT INTO Mutter(NAME,TEXT,TOPIC) VALUES(?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1,mutter.getUserName());
			pStmt.setString(2,mutter.getText());
			pStmt.setString(3,topic);

			//INSERTを実行
			int result = pStmt.executeUpdate();
			if(result !=1) {
				return false;
			}
		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}