package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Account;
import model.Users;

public class UsersDAO{




	public Account findByLogin(Users users){
		Account account = null;

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
		try {
			Class.forName("org.postgresql.Driver");
			//Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(dbUri, // "jdbc:postgresql://[場所(Domain)]:[ポート番号]/[DB名]"
            		userName, // ログインロール
            		passWord); // パスワード
			//SELECT文を準備
			String sql = "SELECT NAME,PASS FROM USERS WHERE NAME=? AND PASS=?";
			PreparedStatement pStmt = connection.prepareStatement(sql);
			pStmt.setString(1,users.getName());
			pStmt.setString(2,users.getPass());

			//SELECTを実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			//一致したユーザがいた場合
			//そのユーザを表すAccountインスタンスを作成
			if(rs.next()) {
				String name = rs.getString("NAME");
				String pass = rs.getString("PASS");
				account = new Account(name,pass);
				System.out.println(name);
			}

		} catch (SQLException
				| ClassNotFoundException
				e) {
			e.printStackTrace();
			System.out.println("DB接続エラー");
			return null;
		}
		return account;
	}
}


