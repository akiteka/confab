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


	public List<Mutter> findAll(Mutter mutter){
		List<Mutter>mutterList=new ArrayList<>();
		String topic = mutter.getTopic();

		//heroku上でデータベース接続に使用する情報
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
			//Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(dbUri, // "jdbc:postgresql://[場所(Domain)]:[ポート番号]/[DB名]"
            		userName, // ログインロール
            		passWord); // パスワード
			//SELECT文を準備
			String sql = "SELECT ID,NAME,TEXT,TOPIC FROM MUTTER WHERE TOPIC LIKE ? ORDER BY ID DESC  ";
			PreparedStatement pStmt = connection.prepareStatement(sql);
			if(topic.equals("initial")) { //ログイン直後はすべてのトピックの書き込みを表示する。
				pStmt.setString(1,"%");
			}else {
				pStmt.setString(1,topic);
			}
			//System.out.println(topic);


			//SELECTを実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			//一致したユーザがいた場合
			//そのユーザを表すAccountインスタンスを作成
			while(rs.next()) {
				int id =rs.getInt("ID");
				String usersName = rs.getString("NAME");
				String text = rs.getString("TEXT");
				String topicDB = rs.getString("TOPIC");
				Mutter addMutter = new Mutter(id,usersName,text,topicDB);
				mutterList.add(addMutter);
				//System.out.println(usersName);

			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return mutterList;
	}

	public boolean create(Mutter mutter) {
		String topic = mutter.getTopic();
		//データベース接続に使用する情報
		Connection connection = null;

		String dbUri = System.getenv("url");
		String userName = System.getenv("user");
		String passWord = System.getenv("Password");

		if(dbUri==null){
			dbUri="jdbc:postgresql://localhost:5432/confab";
			userName="postgres";
			passWord="r-faced6037";
		}

		//データベースへ接続
		try{
			Class.forName("org.postgresql.Driver");
			//Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(dbUri, // "jdbc:postgresql://[場所(Domain)]:[ポート番号]/[DB名]"
            		userName, // ログインロール
            		passWord); // パスワード

			//INSERT文を準備
			String sql = "INSERT INTO Mutter(NAME,TEXT,TOPIC) VALUES(?,?,?)";
			PreparedStatement pStmt = connection.prepareStatement(sql);

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